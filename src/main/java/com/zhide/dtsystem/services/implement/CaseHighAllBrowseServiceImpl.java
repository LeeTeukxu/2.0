package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CaseHighAllBrowseMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICaseHighAllBrowseService;
import com.zhide.dtsystem.services.define.ICaseHighUserService;
import com.zhide.dtsystem.services.define.IChangeRecordService;
import com.zhide.dtsystem.services.simpleMemoCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import com.zhide.dtsystem.viewModel.HighBillObject;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CaseHighAllBrowseServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月22日 17:43
 **/
@Service
public class CaseHighAllBrowseServiceImpl implements ICaseHighAllBrowseService {
    LoginUserInfo loginUserInfo;
    @Autowired
    tbClientLinkersRepository linkerRep;
    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    ICaseHighUserService highUserService;
    @Autowired
    caseHighAttachmentRepository highAttRep;
    @Autowired
    caseHighSubFilesRepository highSubAttRep;
    @Autowired
    caseHighSubRepository caseSubRep;
    @Autowired
    IChangeRecordService changeRecordService;
    @Autowired
    StringRedisTemplate redisRep;
    @Autowired
    CaseHighAllBrowseMapper caseHighMapper;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesChangeRecordRepository caseChangeRep;
    @Autowired
    caseHighUserRepository highUserRep;
    @Autowired
    caseHighSubUserRepository highSubUserRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = caseHighMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> CIDS = datas.stream().map(f -> f.get("CasesID").toString()).collect(toList());
            List<simpleMemo> memos = new ArrayList<>();
            datas.stream()
                    .filter(f -> ObjectUtils.isEmpty(f.get("ProcessText")) == false)
                    .map(f -> f.get("ProcessText").toString())
                    .forEach(f -> memos.addAll(JSON.parseArray(f, simpleMemo.class).stream().collect(toList())));
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> row = datas.get(i);
                String CasesID = row.get("CasesID").toString();
                List<simpleMemo> memo = memos.stream().filter(f -> f.getPid().equals(CasesID)).collect(toList());
                Map<String, Object> newRow = eachSingleRow(row, memo);
                PP.add(newRow);
            }
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public caseHighMain SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        caseHighMain main = SaveMain(Data);
        String CasesID = main.getCasesId();
        String Action = Data.get("Action").toString();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(CasesID, atts);
        }
        if (Data.containsKey("AJ")) {
            String AJText = Data.get("AJ").toString();
            List<caseHighSub> items = JSON.parseArray(AJText, caseHighSub.class);
            if (items.size() > 0) SaveAJ(CasesID, items, Action.equals("Commit") ? true : false);
            else {
                if (Action.equals("Commit")) throw new Exception("必须录入交单明细才能提交审核!");
            }
        } else {
            if (Action.equals("Commit")) throw new Exception("必须录入交单明细才能提交审核!");
        }
        if (Data.containsKey("watchFields")) {
            Object OX = Data.get("watchFields");
            if (ObjectUtils.isEmpty(OX) == false) {
                String watchText = OX.toString();
                List<String> fields = JSON.parseArray(watchText, String.class);
                if (fields.size() > 0) changeRecordService.CreateAndSave(fields, "PayCaseHigh", main);
            }
        }
        if (Action.equals("Commit")) {
            List<caseHighSub> Subs = caseSubRep.findAllByCasesId(CasesID);
            Double Total = Subs.stream().flatMapToDouble(f -> DoubleStream.of(f.getContainSjf() == 1 ?
                    f.getNum() * f.getDaiMoney
                    () + f.getSjfMoney() : f.getNum() * f.getDaiMoney())).sum();
            main.setAllMoney(Total);

            if(Data.containsKey("AuditMan")){
                Integer XMan=Integer.parseInt(Data.get("AuditMan").toString());
                main.setAuditMan(XMan);
            }
            highMainRep.save(main);
        }
        return main;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value = {"getAllCasesMain", "getAllCasesSub"}, keyGenerator = "CompanyKeyGenerator")
    public caseHighMain Commit(int ID, String Result, String ResultText) throws Exception {
        caseHighMain main;
        LoginUserInfo Info = CompanyContext.get();
        if (ID < 1) throw new Exception("参数异常");
        int UserID = Integer.parseInt(Info.getUserId());
        Optional<caseHighMain> findOne = highMainRep.findById(ID);
        if (findOne.isPresent()) {
            main = findOne.get();
            if (main.getState() != 2) throw new Exception("业务状态不正确。");
            if (main.getClientId() == null) throw new Exception("客户信息不能为空，请完善信息后再进行提交操作!");
            if (Result.equals("同意交单")) {
                main.setState(4);
            } else {
                main.setState(3);
                main.setAuditMan(null);
                main.setAuditManager(null);
            }

            List<caseHighSub> childs = caseSubRep.findAllByCasesId(main.getCasesId());
            if (Result.equals("同意交单") == true) {
                if (childs.size() == 0) throw new Exception("必须录入交单明细才能提交审核!");
            }
            main.setAuditMan(UserID);
            main.setAuditTime(new Date());
            main.setAuditText(ResultText);
            main.setAuditManager(loginUserMapper.getManager(Info.getUserId()));
            Double X = childs.stream().mapToDouble(f -> f.getDaiMoney() + (f.getContainSjf() == 1 ? f.getSjfMoney() :
                    0)).sum();
            main.setAllMoney(X);
            /*重新生成业务数量*/
            main.setNums(GetAJNumbers(childs));

            highMainRep.save(main);
            /*加入审核人*/
            highUserService.AddOne(main.getCasesId(), UserID);
            for (int i = 0; i < childs.size(); i++) {
                caseHighSub sub = childs.get(i);
                String subId = sub.getSubId();
                if (Result.equals("同意交单")) {
                    sub.setCanUse(true);
                    sub.setProcessState(50);
                } else {
                    sub.setCanUse(false);
                }
                Double sjfMoney = sub.getSjfMoney();
                if (sjfMoney == null) sjfMoney = 0.0;
                Double Dai = sub.getDaiMoney();
                Integer Num = sub.getNum();
                Double Total = Dai * Num + sjfMoney;
                sub.setTotalMoney(Total);
                caseSubRep.save(sub);
            }
            redisRep.delete("getAllCasesMain::" + Info.getCompanyId());
            redisRep.delete("getAllCasesSub::" + Info.getCompanyId());

        } else {
            throw new Exception("当前审核的业务对象已不存在!");
        }
        return main;
    }

    private String GetAJNumbers(List<caseHighSub> subs) {
        List<String> Res = new ArrayList<>();
        List<String> Names = subs.stream().map(f -> f.getYName()).distinct().collect(Collectors.toList());
        for (int i = 0; i < Names.size(); i++) {
            String Name = Names.get(i);
            Integer Num = subs.stream().filter(f -> f.getYName().equals(Name)).mapToInt(f -> f.getNum()).sum();
            Res.add(Integer.toString(Num) + Name);
        }
        return StringUtils.join(Res, ",");
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");

        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String StateText = request.getParameter("State");
        if (StringUtils.isEmpty(StateText) == false) {
            params.put("State", StateText);
        }
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

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<simpleMemo> memos) {
        row.remove("_TotalNum");
        List<String> words = new ArrayList<>();
        for (int i = 0; i < memos.size(); i++) {
            simpleMemo simple = memos.get(i);
            words.add(simpleMemoCreator.createSingle(i + 1, simple));
        }
        row.put("EDITMEMO", words.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            row.put("MEMO", String.join("<br/><br/>", words));
        } else {
            row.put("MEMO", "");
        }
        return row;
    }

    private caseHighMain SaveMain(Map<String, Object> Data) throws Exception {
        caseHighMain main = new caseHighMain();
        String CasesID = "";
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            CasesID = Data.get("casesId").toString();
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<caseHighMain> finds = highMainRep.findById(ID);
        if (finds.isPresent()) {
            main = finds.get();
            main.setPreFormText(main.getFormText());
            int state = main.getState();
            if (state > 4) throw new Exception("业务状态异常，操作被中止!");
            if (Action.equals("Save")) {
                main.setState(1);
            } else main.setState(2);

        } else {
            main.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
            main.setCreateTime(new Date());
            main.setCasesId(UUID.randomUUID().toString());
            if (Action.equals("Save")) main.setState(1);
            else main.setState(2);

            main.setDocSn(feeItemMapper.getFlowCode("GQJD"));
            String myManager = loginUserMapper.getManager(loginUserInfo.getUserId());
            main.setCreateManager(myManager);
        }
        if (StringUtils.isEmpty(CasesID) == false) main.setCasesId(CasesID);
        for (String Key : Data.keySet()) {
            Field target = fieldObject.findByName(main, Key);
            if (target != null) {
                Object value = Data.get(Key);
                fieldObject.setValue(main, target, value);
            }
        }
        highMainRep.save(main);

        /*插入商务人员*/
        highUserService.AddOne(main.getCasesId(), main.getCreateMan());
        return main;
    }

    private void SaveAttachment(String CasesID, List<String> IDS) {
        highAttRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            caseHighAttachment att = new caseHighAttachment();
            att.setCasesId(CasesID);
            att.setAttId(ID);
            highAttRep.save(att);
        }
    }

    private void SaveAJ(String CasesID, List<caseHighSub> items, boolean isCommit) {
        Double XTotal = 0.0;
        for (int i = 0; i < items.size(); i++) {
            caseHighSub row = items.get(i);
            int Id = row.getId();
            Optional<caseHighSub> nowOnes = caseSubRep.findById(Id);
            if (nowOnes.isPresent()) {
                caseHighSub nowOne = nowOnes.get();
                nowOne.setMemo(row.getMemo());
                nowOne.setSwsName(row.getSwsName());
                nowOne.setSbYear(row.getSbYear());
                nowOne.setArea(row.getArea());
                nowOne.setContainSjf(row.getContainSjf());
                nowOne.setSjfMoney(row.getSjfMoney());
                nowOne.setDaiMoney(row.getDaiMoney());
                nowOne.setClientRequiredDate(row.getClientRequiredDate());
                nowOne.setClientLinkMan(row.getClientLinkMan());
                nowOne.setClientLinkMail(row.getClientLinkMail());
                nowOne.setClientLinkPhone(row.getClientLinkPhone());
                nowOne.setRptType(row.getRptType());
                nowOne.setIsComplete(false);
                if (isCommit == false) nowOne.setCanUse(false);
                else {
                    nowOne.setCanUse(false);
                }
                Integer ContainSJF = row.getContainSjf();
                Double Total = 0.0;
                if (ContainSJF == 1) Total = row.getDaiMoney() * row.getNum() + row.getSjfMoney();
                else Total = row.getDaiMoney() * row.getNum();
                nowOne.setTotalMoney(Total);
                XTotal += Total;
                caseSubRep.save(nowOne);
            }
        }
        Optional<caseHighMain> findMains = highMainRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            caseHighMain main = findMains.get();
            main.setAllMoney(XTotal);
            highMainRep.save(main);
        }
    }

    public boolean ChangeAllMoney(String CasesID, Double changeMoney) {
        Optional<caseHighMain> findMains = highMainRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            List<caseHighSub> Subs = caseSubRep.findAllByCasesId(CasesID);
            Double Total = Subs.stream().flatMapToDouble(f -> DoubleStream.of(f.getContainSjf() == 1 ?
                    f.getNum() * f.getDaiMoney
                    () + f.getSjfMoney() : f.getNum() * f.getDaiMoney())).sum();
            caseHighMain main = findMains.get();
            main.setAllMoney(Total);
            main.setChangeMoney(changeMoney);
            main.setAllMoneySet(true);
            highMainRep.save(main);
            return true;
        }
        return false;
    }

    @Override
    public boolean SaveMainMemo(String CasesID, String Data) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<simpleMemo> postMemos = JSON.parseArray(Data, simpleMemo.class);
        Optional<caseHighMain> findOnes = highMainRep.findFirstByCasesId(CasesID);
        if (findOnes.isPresent()) {
            caseHighMain main = findOnes.get();
            String X = main.getProcessText();
            List<simpleMemo> saveMemos = JSON.parseArray(X, simpleMemo.class);
            if (saveMemos == null) saveMemos = new ArrayList<>();
            for (int i = 0; i < postMemos.size(); i++) {
                simpleMemo simple = postMemos.get(i);
                String ID = simple.getId();
                Optional<simpleMemo> cOnes = saveMemos.stream().filter(f -> f.getId().equals(ID)).findFirst();
                if (cOnes.isPresent()) {
                    simpleMemo One = cOnes.get();
                    One.setMemo(simple.getMemo());
                    One.setUpdateMan(Info.getUserIdValue());
                    One.setUpdateManName(Info.getUserName());
                    One.setUpdateTime(new Date());
                } else saveMemos.add(simple);
            }
            main.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            highMainRep.save(main);
        } else throw new Exception("更新的数据不存在!");
        return true;
    }

    @Override
    public boolean SaveSubMemo(String SubID, String Data) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<simpleMemo> postMemos = JSON.parseArray(Data, simpleMemo.class);
        Optional<caseHighSub> findOnes = caseSubRep.findBySubId(SubID);
        if (findOnes.isPresent()) {
            caseHighSub main = findOnes.get();
            String X = main.getProcessText();
            List<simpleMemo> saveMemos = JSON.parseArray(X, simpleMemo.class);
            if (saveMemos == null) saveMemos = new ArrayList<>();
            for (int i = 0; i < postMemos.size(); i++) {
                simpleMemo simple = postMemos.get(i);
                String ID = simple.getId();
                Optional<simpleMemo> cOnes = saveMemos.stream().filter(f -> f.getId().equals(ID)).findFirst();
                if (cOnes.isPresent()) {
                    simpleMemo One = cOnes.get();
                    One.setMemo(simple.getMemo());
                    One.setUpdateMan(Info.getUserIdValue());
                    One.setUpdateManName(Info.getUserName());
                    One.setUpdateTime(new Date());
                } else saveMemos.add(simple);
            }
            main.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            caseSubRep.save(main);
        } else throw new Exception("更新的数据不存在!");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean RemoveAll(String CasesID) throws Exception {
        LoginUserInfo log = CompanyContext.get();
        List<casesChangeRecord> changeRecords = caseChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
        caseChangeRep.deleteAll(changeRecords);
        highAttRep.deleteAllByCasesId(CasesID);

        List<caseHighSub> casesSubs = caseSubRep.findAllByCasesId(CasesID);
        for (int i = 0; i < casesSubs.size(); i++) {
            caseHighSub sub = casesSubs.get(i);
            String SubNo = sub.getSubNo();
            if (StringUtils.isEmpty(SubNo) == false) {
                String Type = "GQ";
                String YearMonth = SubNo.substring(2, SubNo.length() - 4);
                Integer Num = Integer.parseInt(SubNo.substring(SubNo.length() - 4));
                feeItemMapper.deleteFlowCode(Type, Num, YearMonth);
            }
        }

        caseSubRep.deleteAllByCasesId(CasesID);
        highSubAttRep.deleteAllByCasesId(CasesID);

        highUserRep.deleteAllByCasesId(CasesID);
        highSubUserRep.deleteAllByCasesId(CasesID);
        Optional<caseHighMain> findOnes = highMainRep.findFirstByCasesId(CasesID);
        if (findOnes.isPresent()) {
            highMainRep.delete(findOnes.get());
        }
        redisRep.delete("getAllCaseHighMain::" + log.getCompanyId());
        redisRep.delete("getAllCaseHighSub::" + log.getCompanyId());
        return true;
    }

    @Autowired
    tbArrivalRegistrationRepository arrRep;
    @Autowired
    arrivalUseDetailRepository detailRep;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveSelectMoney(SelectMoneyInfo Info) throws Exception {
        LoginUserInfo LogInfo=CompanyContext.get();
        Integer ArrID=Info.getArrID();
        Optional<tbArrivalRegistration> findArrs=arrRep.findById(ArrID);
        if(findArrs.isPresent()){
            Double  SelectTotal=Info.getDai()+Info.getGuan();
            tbArrivalRegistration main=findArrs.get();
            Double PayAmount=Double.parseDouble(main.getPaymentAmount());
            if(PayAmount==0) throw new Exception("到帐金额为0的业务不能进行认领!");

            Optional<caseHighMain> findMains=highMainRep.findFirstByCasesId(Info.getCasesID());
            if(findMains.isPresent()){
                List<caseHighSub> Subs=caseSubRep.findAllByCasesId(Info.getCasesID());
                if(Subs.size()==0) throw new Exception("至少要一条业务明细!");
                Double CasesMoney=Subs.stream().mapToDouble(f->f.getDaiMoney()+f.getSjfMoney()).sum();
                if(CasesMoney==0) throw new Exception("业务交单金额不能为0");
                if(SelectTotal>CasesMoney) throw new Exception("领用金额("+Double.toString(SelectTotal)+")不能大于交单金额("+Double.toString(CasesMoney)+ ")");

            } else throw  new Exception("交单业务对象不存在!");
            List<arrivalUseDetail> savedDatas=detailRep.findAllByArrId(ArrID);
            Double savedMoney=
                    savedDatas.stream().filter(f->f.getState()!=1).mapToDouble(f->f.getDai()+f.getGuan()).sum();
            Double NowTotal=savedMoney+Info.getDai()+Info.getGuan();
            if(NowTotal>PayAmount) throw new Exception("累计领用金额已超过了到帐金额!");

            arrivalUseDetail detail=new arrivalUseDetail();
            detail.setState(0);
            detail.setMoneyType(1);
            detail.setArrId(ArrID);
            detail.setCanUse(true);
            detail.setCasesId(Info.getCasesID());
            detail.setDai(Info.getDai());
            detail.setGuan(Info.getGuan());
            detail.setTotal(SelectTotal);
            detail.setClientName(Info.getClientName());
            detail.setClientId(Info.getClientID());
            detail.setCreateMan(LogInfo.getUserIdValue());
            detail.setCreateTime(new Date());
            detail.setMemo("通过项目专利交单模块进行领用。");
            detailRep.save(detail);

            main.setClaimant(LogInfo.getUserIdValue());
            main.setClaimStatus(2);
            main.setClaimDate(new Date());
            main.setReviewerStatus(0);
            main.setKhName(Info.getClientName());
            main.setCustomerId(Integer.toString(Info.getClientID()));
            arrRep.save(main);
        } else throw new Exception("操作的回款单对象不存在!");
    }
    String NONE="无相关设置";
    @Override
    public HighBillObject getBillObject(String CasesID) throws Exception {
        HighBillObject billObject = caseHighMapper.getBillObject(CasesID);
        List<tbClientLinkers> linkers = linkerRep.findAllByClientID(billObject.getCLIENTID());
        if (linkers.size() > 0) {
            tbClientLinkers linker=linkers.get(0);
            billObject.setADDRESS(linker.getAddress());
            billObject.setLINKMAN(linker.getLinkMan());
            billObject.setLINKPHONE(linker.getLinkPhone());
            billObject.setMOBILE(linker.getMobile());
        } else {
            billObject.setADDRESS(NONE);
            billObject.setLINKMAN(NONE);
            billObject.setLINKPHONE(NONE);
            billObject.setMOBILE(NONE);
        }
        return billObject;
    }

    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "项目交单反审核操作")
    public void ReAudit(String CasesID) throws  Exception{
        Optional<caseHighMain>findMains=highMainRep.findFirstByCasesId(CasesID);
        if(findMains.isPresent()){
            caseHighMain main= findMains.get();
            main.setState(3);
            main.setAuditMan(null);
            main.setAuditManager(null);
            main.setAuditTime(null);
            main.setAuditText(null);
            highMainRep.save(main);
        } else throw new Exception("当前取消审核的专利交单业务已不存在!");
        List<caseHighSub> subs=caseSubRep.findAllByCasesId(CasesID);
        for(caseHighSub sub:subs){
            Integer TechMan=sub.getTechMan();
            if(TechMan==null)TechMan=0;
            if(TechMan>0){
                throw new Exception("交单明细:【"+sub.getSubNo()+"】已被技术人员领取，无法进行取消审核操作!");
            }
            sub.setCanUse(false);
        }
        caseSubRep.saveAll(subs);
    }

    @Override
    public void CaseHighMainIDChange(Integer Transfer, Integer Resignation) throws Exception {
        loginUserInfo = CompanyContext.get();
        List<caseHighMain> listCaseHighMan = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        highMainRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            caseHighMain caseHighMain = new caseHighMain();
            caseHighMain = f;

            String Record = "";
            String CreateManRecord = "";
            String CreateManagerRecord = "";
            String AuditManRecord = "";
            String AuditManagerRecord = "";
            String TechManRecord = "";
            String TechManagerRecord = "";
            String CWManRecord = "";
            String CWManagerRecord = "";
            String CompleteManRecord = "";

            //商务人员
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    caseHighMain.setCreateMan(Transfer);
                    CreateManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //商务人员经理
            if (f.getCreateManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCreateManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CreateManagerRecord = iar.getRecord();
                caseHighMain.setCreateManager(iar.getId());
            }

            //流程审核人员
            if (f.getAuditMan() != null) {
                if (f.getAuditMan() == Resignation) {
                    caseHighMain.setAuditMan(Transfer);
                    AuditManRecord = "流程审核人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //流程审核人员经理
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                caseHighMain.setAuditManager(iar.getId());
            }

            //接单技术人员
            if (f.getTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManRecord = iar.getRecord();
                caseHighMain.setTechMan(iar.getId());
            }

            //接单技术经理
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                caseHighMain.setTechManager(iar.getId());
            }

            //财务人员
            if (f.getCwMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManRecord = iar.getRecord();
                caseHighMain.setCwMan(iar.getId());
            }

            //财务人员经理
            if (f.getCwManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManagerRecord = iar.getRecord();
                caseHighMain.setCwManager(iar.getId());
            }

            //完结操作人员
            if (f.getCompleteMan() != null) {
                if (f.getCompleteMan() == Resignation) {
                    caseHighMain.setCompleteMan(Transfer);
                    CompleteManRecord = "完结操作人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listCaseHighMan.add(caseHighMain);
            Record = CreateManRecord + CreateManagerRecord + AuditManRecord + AuditManagerRecord + TechManRecord + TechManagerRecord + CWManRecord + CWManagerRecord + CompleteManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseHighManChange");
                crecord.setChangeText(Record);
                crecord.setUserId(loginUserInfo.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });
        if (listCasesChangeRecord.size() > 0) {
            caseChangeRep.saveAll(listCasesChangeRecord);
        }
        if (listCaseHighMan.size() > 0) {
            highMainRep.saveAll(listCaseHighMan);
        }
    }

    private IdAndRecord GetCreateManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String createManager = f.getCreateManager();
        String result = "";
        String[] createManagers = createManager.split(",");
        for (String strCreateManager : createManagers) {
            if (strCreateManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strCreateManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("流程审核人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechMan(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techMan = f.getTechMan();
        String result = "";
        String[] techMans = techMan.split(",");
        for (String strTechMan : techMans) {
            if (strTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("接单技术人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManager : techManagers) {
            if (strTechManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("接单技术经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwMan(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwMan = f.getCwMan();
        String result = "";
        String[] cwMans = cwMan.split(",");
        for (String strCwMan : cwMans) {
            if (strCwMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("财务人员由：" + loginResignation + "便跟为：" + loginTransfer + "；");
                }
            }else result += strCwMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwManager = f.getCwManager();
        String result = "";
        String[] cwManagers = cwManager.split(",");
        for (String strCwManager : cwManagers) {
            if (strCwManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("财务人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strCwManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    public class IdAndRecord {
        private String Id;
        private String Record;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getRecord() {
            return Record;
        }

        public void setRecord(String record) {
            Record = record;
        }
    }
}
