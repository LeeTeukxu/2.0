package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.MiddleFileMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.tbInvoiceApplicationMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbInvoiceApplicationRepository;
import com.zhide.dtsystem.repositorys.tbInvoiceParameterRepository;
import com.zhide.dtsystem.services.define.ItbInvoiceApplicationService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class tbInvoiceApplicationImpl implements ItbInvoiceApplicationService {
    @Autowired
    tbInvoiceApplicationMapper invoiceApplicationMapper;

    @Autowired
    tbInvoiceApplicationRepository invoiceApplicationRepository;

    @Autowired
    tbInvoiceParameterRepository invoiceParameterRepository;

    @Autowired
    MiddleFileMapper middleFileMapper;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @Autowired
    SysLoginUserMapper loginUserMapper;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = invoiceApplicationMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> IDS = datas.stream().map(f -> f.get("InvoiceApplicationID").toString()).collect(toList());
            List<String> middleFiles = middleFileMapper.getAllByType("发票申请", IDS);
            List<String> DZFPMiddleFiles = middleFileMapper.getAllByType("电子发票", IDS);
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, middleFiles, DZFPMiddleFiles);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (StringUtils.isEmpty(sortField)) sortField = "AddTime";
        String state= request.getParameter("State");
        if(StringUtils.isEmpty(state))state="0";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if(state.equals("0")==false)params.put("State",state);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        return params;
    }

    private Map<String, Object> eachSingleRow(Map<String, Object> row, List<String> middleFiles,
            List<String> DZFPMiddleFiles) {
        row.remove(" TotalNum");
        String InvoiceApplicationID = row.get("InvoiceApplicationID").toString();
        row.put("MIDDLEFILE", middleFiles.contains(InvoiceApplicationID) ? 1 : 0);
        row.put("DZFPMIDDLEFILE", DZFPMiddleFiles.contains(InvoiceApplicationID) ? 1 : 0);
        return row;
    }

    @Override
    public Page<tbInvoiceParameter> getParameter(int dtId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("fid").descending());
        Page<tbInvoiceParameter> recordPage = invoiceParameterRepository.findAllBydtId(dtId, pageable);
        return recordPage;
    }

    @Override
    public tbInvoiceParameter saveParameter(tbInvoiceParameter data, String Name) throws Exception {
        if (data == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        tbInvoiceParameter result = null;

        if (Name.equals("InvoiceType")) {
            data.setPid(1);
            data.setDtId(1);
        }
        if (Name.equals("Bank")) {
            data.setPid(2);
            data.setDtId(2);
        }
        if (Name.equals("ProjectName")) {
            data.setPid(3);
            data.setDtId(3);
        }
        if (Name.equals("TickCompany")) {
            data.setPid(4);
            data.setDtId(4);
        }
        data.setSn("0");
        tbInvoiceParameter invoiceParameterData = getDictDataMaxFID();
        if (invoiceParameterData == null) {
            data.setFid(1);
        } else {
            data.setFid(invoiceParameterData.getFid() + 1);
        }
        result = invoiceParameterRepository.save(data);
        return result;
    }

    public tbInvoiceParameter getDictDataMaxFID() {
        return invoiceApplicationMapper.getDictDataMaxFID();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbInvoiceApplication save(tbInvoiceApplication invoiceApplication, String Text, String CommitType) throws Exception {
        if (invoiceApplication == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        tbInvoiceApplication result = null;
        if (invoiceApplication.getInvoiceApplicationId() == null) {
            invoiceApplication.setApplicant(Integer.parseInt(loginUserInfo.getUserId()));
            invoiceApplication.setDateOfApplication(new Date());
            invoiceApplication.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            invoiceApplication.setAddTime(new Date());
            invoiceApplication.setState(1);
            result = invoiceApplicationRepository.save(invoiceApplication);
            if (Text != "") {
                CreateChangeRecord(CommitType, Text, result);
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(tbInvoiceApplication invoiceApplication, String Text, String CommitType) throws Exception {
        if (invoiceApplication == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (invoiceApplication.getInvoiceApplicationId() != null) {
            invoiceApplicationRepository.save(invoiceApplication);
            if (Text != "") {
                CreateChangeRecord(CommitType, Text, invoiceApplication);
            }
        }
        return 1;
    }

    public int UPDATE(tbInvoiceApplication invoiceApplication) {
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(List<Integer> ids) throws Exception {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            Optional<tbInvoiceApplication> findOnes=invoiceApplicationRepository.findById(id);
            if(findOnes.isPresent()) {
                int State=findOnes.get().getState();
                if(State!=1 && State!=3) throw new Exception("只有待提交和审核驳回状态的单据才可以删除!");
                invoiceApplicationRepository.deleteById(id);
                finanChangeRecordRepository.deleteAllByPidAndModuleName(id, "InvoiceApplication");
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeParameter(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            invoiceParameterRepository.deleteById(id);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateExpressInfo(tbInvoiceApplication invoiceApplication) throws Exception {
        if (invoiceApplication == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (invoiceApplication.getInvoiceApplicationId() != null) {
            Optional<tbInvoiceApplication> findOnes=
                    invoiceApplicationRepository.findById(invoiceApplication.getInvoiceApplicationId());
            if(findOnes.isPresent()){
                tbInvoiceApplication one= findOnes.get();
                one.setState(4);
                one.setAuditResult(1);
                if(one.getAuditMan()==null){
                    one.setAuditMan(loginUserInfo.getUserIdValue());
                    one.setAuditTime(new Date());
                }
                one.setAuditText(invoiceApplication.getAuditText());
                one.setReceiptNumber(invoiceApplication.getReceiptNumber());
                one.setExpressNumber(invoiceApplication.getExpressNumber());
                one.setCourierCompany(invoiceApplication.getCourierCompany());
                one.setReceiveTime(invoiceApplication.getReceiveTime());
                if(StringUtils.isEmpty(invoiceApplication.getExpressNumber())==false){
                    one.setIsSend(1);
                } else one.setIsSend(0);
                invoiceApplicationRepository.save(one);
                result=1;
            }
        }
        return result;
    }

    public int UPDATEExpressInfo(tbInvoiceApplication invoiceApplication) {
        if (invoiceApplication.getCourierCompany() != "" && invoiceApplication.getExpressNumber() != "") {
            invoiceApplication.setIsSend(1);
        } else {
        }
        int result = invoiceApplicationRepository.UPDATEExpressInfo(invoiceApplication.getReceiptNumber(),
                invoiceApplication.getCourierCompany(),
                invoiceApplication.getExpressNumber(),
                invoiceApplication.getIsSend(),
                invoiceApplication.getInvoiceApplicationId());
        return result;
    }

    private FinanChangeRecord CreateChangeRecord(String Mode, String Text, tbInvoiceApplication invoiceApplication) {
        LoginUserInfo info = CompanyContext.get();
        FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
        finanChangeRecord.setPid(invoiceApplication.getInvoiceApplicationId());
        finanChangeRecord.setChangeText(Text);
        finanChangeRecord.setMode(Mode);
        finanChangeRecord.setModuleName("InvoiceApplication");
        finanChangeRecord.setUserId(Integer.parseInt(info.getUserId()));
        finanChangeRecord.setCreateTime(new Date());
        return finanChangeRecordRepository.save(finanChangeRecord);
    }

    @Override
    public void InvoiceApplicationIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();

        List<tbInvoiceApplication> listInvoiceApplication = new ArrayList<>();
        List<FinanChangeRecord> listFinanChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        invoiceApplicationRepository.findAll().stream().forEach(f -> {
            FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
            tbInvoiceApplication invoiceApplication = new tbInvoiceApplication();
            invoiceApplication = f;

            String Record = "";
            String ApplicantRecord = "";
            String UserIDRecord = "";

            //申请人
            if (f.getApplicant() != null) {
                if (f.getApplicant() == Resignation) {
                    invoiceApplication.setApplicant(Transfer);
                    ApplicantRecord = "申请人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            if (f.getUserId() != null) {
                if (f.getUserId() == Resignation) {
                    invoiceApplication.setUserId(Transfer);
                    UserIDRecord = "创建人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listInvoiceApplication.add(invoiceApplication);

            Record = ApplicantRecord + UserIDRecord;
            if (!Record.equals("")) {
                finanChangeRecord.setPid(f.getInvoiceApplicationId());
                finanChangeRecord.setMode("Add");
                finanChangeRecord.setModuleName("InvoiceApplication");
                finanChangeRecord.setChangeText(Record);
                finanChangeRecord.setUserId(Info.getUserIdValue());
                finanChangeRecord.setCreateTime(new Date());
                listFinanChangeRecord.add(finanChangeRecord);
            }
        });

        if (listFinanChangeRecord.size() > 0) {
            finanChangeRecordRepository.saveAll(listFinanChangeRecord);
        }
        if (listInvoiceApplication.size() > 0) {
            invoiceApplicationRepository.saveAll(listInvoiceApplication);
        }
    }

    @Override
    public List<Map<String, Object>> getInvoiceTotal() {
        LoginUserInfo Info=CompanyContext.get();
        List<Map<String,Object>> OO= invoiceApplicationMapper.getInvoiceTotal(Info.getDepIdValue(),Info.getUserIdValue(),Info.getRoleName());
        return OO;
    }
}
