package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.tbArrivalMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.arrivalUseDetailRepository;
import com.zhide.dtsystem.repositorys.tbArrivalRegistrationRepository;
import com.zhide.dtsystem.services.define.IArrivalUseDetailService;
import com.zhide.dtsystem.services.define.ItbArrivalService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class tbArrivalServiceImpl implements ItbArrivalService {

    @Autowired
    tbArrivalMapper arrivalMapper;

    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;
    @Autowired
    IArrivalUseDetailService useDetailService;
    @Autowired
    arrivalUseDetailRepository detailRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = arrivalMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    @Override
    public pageObject getWorkData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = arrivalMapper.getWorkData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
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
        if (sortField.isEmpty()) sortField = "AddTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbArrivalRegistration Save(@Param(value = "Data") tbArrivalRegistration ArrivalRegistration, String Text,
            String CommitType,String Sub) throws Exception {
        if (ArrivalRegistration == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date nowTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        tbArrivalRegistration result = null;
        if (ArrivalRegistration.getArrivalRegistrationId() == null) {
            ArrivalRegistration.setDocumentNumber(sdt.append(dt.format(nowTime)).toString());
            ArrivalRegistration.setSignMan(Integer.parseInt(loginUserInfo.getUserId()));
            ArrivalRegistration.setAddTime(new Date());
            ArrivalRegistration.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            ArrivalRegistration.setClaimStatus(1);
            ArrivalRegistration.setReviewerStatus(0);
            result = arrivalRegistrationRepository.save(ArrivalRegistration);
            if (Text != "") {
                CreateChangeRecord(CommitType, Text, result);
            }
            if(StringUtils.isEmpty(Sub)==false){
                List<arrivalUseDetail> Ds=JSON.parseArray(Sub,arrivalUseDetail.class);
                useDetailService.SaveSub(Ds);
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(@Param(value = "Data") tbArrivalRegistration ArrivalRegistration, String Text,
            String CommitType) throws Exception {
        if (ArrivalRegistration == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (ArrivalRegistration.getArrivalRegistrationId() != null) {
            result = UPDATE(ArrivalRegistration);
            if (Text != "") {
                CreateChangeRecord(CommitType, Text, ArrivalRegistration);
            }
        }
        return result;
    }

    public int UPDATE(tbArrivalRegistration ArrivalRegistration) {
//        int result = arrivalRegistrationRepository.Update(ArrivalRegistration.getDocumentNumber(),
//                ArrivalRegistration.getDateOfPayment(),
//                ArrivalRegistration.getPaymentMethod(),
//                ArrivalRegistration.getPaymentAccount(),
//                ArrivalRegistration.getPaymentAmount(),
//                ArrivalRegistration.getPayer(),
//                ArrivalRegistration.getReturnBank(),
//                ArrivalRegistration.getDescription(),
//                ArrivalRegistration.getAttIDS(),
//                ArrivalRegistration.getArrivalRegistrationId());
        int result=0;
        int ArrID=ArrivalRegistration.getArrivalRegistrationId();
        Optional<tbArrivalRegistration> findOnes=arrivalRegistrationRepository.findById(ArrID);
        if(findOnes.isPresent()){
            tbArrivalRegistration  One=findOnes.get();
            One.setDateOfPayment(ArrivalRegistration.getDateOfPayment());
            One.setPaymentMethod(ArrivalRegistration.getPaymentMethod());
            One.setPaymentAccount(ArrivalRegistration.getPaymentAccount());
            One.setPaymentAmount(ArrivalRegistration.getPaymentAmount());
            One.setPayer(ArrivalRegistration.getPayer());
            One.setReturnBank(ArrivalRegistration.getReturnBank());
            One.setDescription(ArrivalRegistration.getDescription());
            One.setAttIDS(ArrivalRegistration.getAttIDS());
            arrivalRegistrationRepository.save(One);
            result=1;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbArrivalRegistration SaveRenLin(@Param(value = "Data") tbArrivalRegistration arrival, String Text,
            String CommitType,String Sub) throws Exception {
        if (arrival == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (arrival.getArrivalRegistrationId() != null) {
            int claimStatus=arrival.getClaimStatus();
            if(claimStatus==4){
                result = updateRenLin(arrival, loginUserInfo.getUserIdValue(), new Date(),4);
            } else result = updateRenLin(arrival, loginUserInfo.getUserIdValue(), new Date(),1);
            if (result > 0) {
                //RenLinUpdateClientCooType(arrival);
                //已拒绝重新认领的话,则重置复核状态和复核备注
                if (arrival.getReviewerStatus() == 1) {
                    arrivalRegistrationRepository.UpdateReviewerStatus(arrival.getArrivalRegistrationId());
                }
                if (Text != "") {
                    CreateChangeRecord(CommitType, Text, arrival);
                }
            }
            if(StringUtils.isEmpty(Sub)==false){
                List<arrivalUseDetail> Ds=JSON.parseArray(Sub,arrivalUseDetail.class);
                useDetailService.SaveSub(Ds);
            }
        }
        return arrival;
    }
    @Transactional(rollbackFor = Exception.class)
    public tbArrivalRegistration CommitRenLin(@Param(value = "Data") tbArrivalRegistration arrival, String Text,
            String CommitType,String Sub) throws Exception{
        if (arrival == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (arrival.getArrivalRegistrationId() != null) {
            result = updateRenLin(arrival, loginUserInfo.getUserIdValue(), new Date(),2);
            if (result > 0) {
                //RenLinUpdateClientCooType(arrival);
                //已拒绝重新认领的话,则重置复核状态和复核备注
                if (arrival.getReviewerStatus() == 1) {
                    arrivalRegistrationRepository.UpdateReviewerStatus(arrival.getArrivalRegistrationId());
                }
                if (Text != "") {
                    CreateChangeRecord(CommitType, Text, arrival);
                }
            }
            if(StringUtils.isEmpty(Sub)==false){
                List<arrivalUseDetail> Ds=JSON.parseArray(Sub,arrivalUseDetail.class);
                if(Ds.size()>0)useDetailService.SaveSub(Ds); else throw new RuntimeException("领用记录为空，审核操作被中止!");
            } else throw new Exception("Sub is Empty:领用记录为空，审核操作被中止!");
        }
        return arrival;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateRenLin(tbArrivalRegistration ArrivalRegistration, int Claimant, Date ClaimDate,int ClaimStatus) {
        Optional<tbArrivalRegistration> findOnes=arrivalRegistrationRepository.findById(ArrivalRegistration.getArrivalRegistrationId());
        if(findOnes.isPresent()){
            tbArrivalRegistration One=findOnes.get();
            One.setCustomerId(ArrivalRegistration.getCustomerId());
            One.setAgencyFee(ArrivalRegistration.getAgencyFee());
            One.setOfficalFee(ArrivalRegistration.getOfficalFee());
            One.setRemark(ArrivalRegistration.getRemark());
            One.setClaimant(Claimant);
            One.setClaimStatus(ClaimStatus);
            One.setReviewerStatus(0);
            One.setClaimDate(ClaimDate);
            arrivalRegistrationRepository.save(One);
            return 1;
        } else return 0;
    }

    public int RenLinUpdateClientCooType(tbArrivalRegistration ArrivalRegistration) {
        int result = arrivalRegistrationRepository.RenLinUpdateClientCooType(1,
                Integer.parseInt(ArrivalRegistration.getCustomerId()));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbArrivalRegistration SaveFuHe(@Param(value = "Data") tbArrivalRegistration arrival, String Text,
            String CommitType) throws Exception {

        if (arrival == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (arrival.getArrivalRegistrationId() != null) {
            result = updateFuHe(arrival, Integer.parseInt(loginUserInfo.getUserId()), new Date());
        }
        if (Text != "") {
            CreateChangeRecord(CommitType, Text, arrival);
        }
        return arrival;
    }

    public int updateFuHe(tbArrivalRegistration ArrivalRegistration, int Reviewer, Date ReviewerDate) {
        int result = arrivalRegistrationRepository.updateFuHe(Reviewer, ReviewerDate,
                ArrivalRegistration.getNote(),
                ArrivalRegistration.getReviewerStatus(),
                ArrivalRegistration.getArrivalRegistrationId());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<Integer> ids) throws  Exception{
        LoginUserInfo Info=CompanyContext.get();
        int  UserID=Info.getUserIdValue();
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            tbArrivalRegistration findOne= arrivalRegistrationRepository.getOne(id);
            if(findOne!=null) {
                int SignMan=findOne.getSignMan();
                if(SignMan!=UserID){
                    throw new Exception(findOne.getDocumentNumber()+"只能由登记人删除!");
                }
                int state= findOne.getClaimStatus();
                if(state!=1 && state!=3){
                    throw new Exception("当前状态的单据不允许删除!");
                }
                List<arrivalUseDetail> ds= detailRep.findAllByArrIdAndState(id,2);
                if(ds.size()>0) throw  new Exception(findOne.getDocumentNumber()+"还存在领用记录，不允许删除!");
                arrivalRegistrationRepository.deleteById(id);
                finanChangeRecordRepository.deleteAllByPidAndModuleName(id, "Arrival");
                detailRep.deleteAllByArrId(id);
            } else throw  new Exception("欲删除的记录不存在，请刷新后再进行操作!");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAudit(Integer ID) throws Exception {
        Optional<tbArrivalRegistration> findOnes=arrivalRegistrationRepository.findById(ID);
        if(findOnes.isPresent()){
            tbArrivalRegistration One=findOnes.get();
            if(One.getClaimStatus()==2 && One.getReviewerStatus()==2){
                One.setReviewerStatus(0);
                One.setReviewer(null);
                One.setReviewerDate(null);
                arrivalRegistrationRepository.save(One);

                List<arrivalUseDetail> ds=detailRep.findAllByArrId(ID);
                for(arrivalUseDetail detail:ds){
                    detail.setState(0);
                    detail.setAuditTime(null);
                    detail.setAuditMan(null);
                    detailRep.save(detail);
                }

            } else throw  new Exception("业务只有在审核通过的状态下才能取消审核!");
        }
    }

    private FinanChangeRecord CreateChangeRecord(String Mode, String Text, tbArrivalRegistration arrivalRegistration) {
        LoginUserInfo info = CompanyContext.get();
        FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
        finanChangeRecord.setPid(arrivalRegistration.getArrivalRegistrationId());
        finanChangeRecord.setChangeText(Text);
        finanChangeRecord.setMode(Mode);
        finanChangeRecord.setModuleName("Arrival");
        finanChangeRecord.setUserId(Integer.parseInt(info.getUserId()));
        finanChangeRecord.setCreateTime(new Date());
        return finanChangeRecordRepository.save(finanChangeRecord);
    }

    @Override
    public void FinanceIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<tbArrivalRegistration> listArrivalRegistration = new ArrayList<>();
        List<FinanChangeRecord> listFinanChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        arrivalRegistrationRepository.findAll().stream().forEach(f -> {
            FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
            tbArrivalRegistration arrivalRegistration = new tbArrivalRegistration();
            arrivalRegistration = f;

            String Record = "";
            String SignManRecord = "";
            String ClaimantRecord = "";
            String ReviewerRecord = "";

            //登记人
            if (f.getSignMan() != null) {
                if (f.getSignMan() == Resignation) {
                    arrivalRegistration.setSignMan(Transfer);
                    SignManRecord = "登记人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //认领人
            if (f.getClaimant() != null) {
                if (f.getClaimant() == Resignation) {
                    arrivalRegistration.setClaimant(Transfer);
                    ClaimantRecord = "认领人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //复核人
            if (f.getReviewer() != null) {
                if (f.getReviewer() == Resignation) {
                    arrivalRegistration.setReviewer(Transfer);
                    ReviewerRecord = "复核人由：" + loginResignation + "变更为：" + loginTransfer;
                }
            }
            listArrivalRegistration.add(arrivalRegistration);

            Record = SignManRecord + ClaimantRecord + ReviewerRecord;
            if (!Record.equals("")) {
                finanChangeRecord.setPid(f.getArrivalRegistrationId());
                finanChangeRecord.setMode("Add");
                finanChangeRecord.setModuleName("Arrival");
                finanChangeRecord.setChangeText(Record);
                finanChangeRecord.setUserId(Info.getUserIdValue());
                finanChangeRecord.setCreateTime(new Date());
                listFinanChangeRecord.add(finanChangeRecord);
            }
        });
        if (listFinanChangeRecord.size() > 0) {
            finanChangeRecordRepository.saveAll(listFinanChangeRecord);
        }
        if (listArrivalRegistration.size() > 0) {
            arrivalRegistrationRepository.saveAll(listArrivalRegistration);
        }
    }
}
