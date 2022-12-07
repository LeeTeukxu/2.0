package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesUserService;
import com.zhide.dtsystem.services.define.ITradeCaseService;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeCaseServiceImpl implements ITradeCaseService {
    @Autowired
    tradeCasesRepository tradeCasesRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    ItbDictDataService dictService;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    tbDictDataRepository dictRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesMemoRepository memoRep;
    @Autowired
    casesCommitFileRepository commitFileRep;
    @Autowired
    casesSubFilesRepository subFileRep;
    @Autowired
    tbTradeCaseFollowRecordRepository tradeCaseFollowRecordRepository;

    LoginUserInfo loginUserInfo;

    @Override
    public @Transactional(rollbackFor = Exception.class)
    tradeCases SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        tradeCases main = SaveMain(Data);
        String CasesID = main.getCasesid();
        String Action = Data.get("Action").toString();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(CasesID, atts);
        }
        if (Data.containsKey("Yw")) {
            String YwText = Data.get("Yw").toString();
            List<casesYwItems> items = JSON.parseArray(YwText, casesYwItems.class);
            if(items.size() > 0) {
                if (main.getClientId() != null) {
                    int ClientID = main.getClientId();
                    SaveYw(CasesID, ClientID, items);
                }else throw new Exception("必须选择客户才能提交!");
            }else {
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
                if (fields.size() > 0) CreateChangeRecord(fields, main);
            }
        }
        return main;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Commit(int ID, String Result, String ResultText) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (ID < 1) throw new Exception("参数异常");
        int UserID = Integer.parseInt(Info.getUserId());
        String ManagerID = loginUserMapper.getManager(Info.getUserId());
        Optional<tradeCases> findOne = tradeCasesRep.findById(ID);
        if (findOne.isPresent()) {
            tradeCases main = findOne.get();
            if (main.getState() != 2) throw new Exception("业务状态不正确。");
            if (main.getClientId() == null) throw new Exception("客户信息不能为空，请完善信息后再进行提交操作!");
            if (Result.equals("同意交单")) {
                main.setState(4);

            } else {
                main.setState(3);
                main.setAuditMan(null);
                main.setAuditManager(null);
            }
            List<casesYwItems> childs = casesYwRep.findAllByCasesId(main.getCasesid());
            if (Result.equals("同意交单") == true) {
                if (childs.size() == 0) throw new Exception("必须录入交单明细才能提交审核!");
            }
            main.setAuditMan(UserID);
            main.setAuditTime(new Date());
            main.setAuditText(ResultText);
            main.setAuditManager(ManagerID);
            if (main.isAllMoneySet() == false){
                Double X = childs.stream().mapToDouble(f -> f.getGuan() + f.getDai()).sum();
                main.setAllMoney(X);
            }
            /*重新生成业务数量*/
            main.setNums(GetAJNumbers(childs));

            tradeCasesRep.save(main);

            //保存审批人到附表
            casesUserService.AddOne(main.getCasesid(), UserID);
            for (int i=0;i<childs.size();i++){
                casesYwItems items = childs.get(i);
                String subId = items.getSubId();
                if (Result.equals("同意交单")){
                    items.setCanUse(true);
                    List<casesSubFiles> files1 = subFileRep.getAllBySubIdAndType(subId, "Tech");
                    if (files1.size() > 0) {
                        String techFiles = StringUtils.join(files1.stream().map(f -> f.getAttId()).collect(Collectors
                                .toList()), ',');
                        items.setTechFiles(techFiles);
                    }
                    items.setProcessState(5);
                }else items.setCanUse(false);
                casesYwRep.save(items);
            }

            Optional<casesCommitFile> findFiles=commitFileRep.findById(ID);
//            if(findFiles.isPresent()){
//                casesCommitFile findOnes=findFiles.get();
//                findOnes.setShenBaoMan(loginUserInfo.getUserIdValue());
//                findOnes.setShenBaoTime(new Date());
//                findOnes.setShenBaoResult(Result);
//                findOnes.setShenBaoText(ResultText);
//                if(Result.equals("同意申报")) findOnes.setState(4); else findOnes.setState(3);
//                commitFileRep.save(findOnes);
//            }
        } else {
            throw new Exception("操作业务已不存在!");
        }
        return true;
    }

    private String GetAJNumbers(List<casesYwItems> subs) {
        List<String> Res = new ArrayList<>();
        List<String> Names = subs.stream().map(f -> f.getYName()).distinct().collect(Collectors.toList());
        for (int i = 0; i < Names.size(); i++) {
            String Name = Names.get(i);
            Integer Num = subs.stream().filter(f -> f.getYName().equals(Name)).mapToInt(f -> f.getNum()).sum();
            Res.add(Integer.toString(Num) + Name);
        }
        return StringUtils.join(Res, ",");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Remove(String CasesID) throws Exception {
        List<casesChangeRecord> changeRecords = casesChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
        casesChangeRep.deleteAll(changeRecords);

        casesAttRep.deleteAllByCasesId(CasesID);

        casesYwRep.deleteAllByCasesId(CasesID);

        memoRep.removeAllByCasesid(CasesID);
        Optional<tradeCases> findOnes = tradeCasesRep.findFirstByCasesid(CasesID);
        if (findOnes.isPresent()) {
            tradeCasesRep.delete(findOnes.get());
        }
        return false;
    }
    @Transactional(rollbackFor = Exception.class)
    private tradeCases SaveMain(Map<String, Object> Data) throws Exception {
        tradeCases main = new tradeCases();
        String CasesID = "";
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            CasesID = Data.get("casesid").toString();
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<tradeCases> finds = tradeCasesRep.findById(ID);
        if (finds.isPresent()) {
            main = finds.get();
            main.setPreFormText(main.getFormText());
            int state = main.getState();
            if (state > 4) throw new Exception("业务状态异常，操作被中止!");
            if (Action.equals("Save")) {
                main.setState(1);
            } else main.setState(2);

        } else {
            main.setDocSn(feeItemMapper.getFlowCode("SZJD"));
            main.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
            main.setCreateTime(new Date());
            main.setCasesid(UUID.randomUUID().toString());
            if (Action.equals("Save")) main.setState(1);
            else main.setState(2);
        }
        if (StringUtils.isEmpty(CasesID) == false) main.setCasesid(CasesID);
        for (String Key : Data.keySet()) {
            Field target = fieldObject.findByName(main, Key);
            if (target != null) {
                Object value = Data.get(Key);
                fieldObject.setValue(main, target, value);
            }
        }

        String myManager = loginUserMapper.getManager(loginUserInfo.getUserId());
        main.setCreateManager(myManager);

        if(Action.equals("Commit")){
            if(Data.containsKey("AuditMan")){
                Integer XMan=Integer.parseInt(Data.get("AuditMan").toString());
                main.setAuditMan(XMan);
            }
        }
        tradeCasesRep.save(main);
        /*插入商务人员*/
        casesUserService.AddOne(main.getCasesid(), main.getCreateMan());
        return main;
    }

    private void SaveAttachment(String CasesID, List<String> IDS) {
        casesAttRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            casesAttachment att = new casesAttachment();
            att.setCasesId(CasesID);
            att.setAttId(ID);
            casesAttRep.save(att);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void SaveYw(String CasesID, int ClientID, List<casesYwItems> items) {
        casesYwRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < items.size(); i++) {
            casesYwItems obj = items.get(i);
            for (int j = 0; j<obj.getNum(); j++) {
                obj.setClientId(ClientID);
                obj.setCasesId(CasesID);
                obj.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
                obj.setCreateTime(new Date());
                casesYwRep.save(obj);
            }
        }
    }

    private void CreateChangeRecord(List<String> fields, tradeCases obj) {
        LoginUserInfo Info = CompanyContext.get();
        int UserID = Integer.parseInt(Info.getUserId());
        String formText = obj.getFormText();
        String preFormText = obj.getPreFormText();
        Map<String, Object> pre = new HashMap<>();
        Map<String, Object> now = JSON.parseObject(formText, new TypeReference<Map<String, Object>>() {
        });
        if (ObjectUtils.isEmpty(preFormText) == false) {
            pre = JSON.parseObject(preFormText, new TypeReference<Map<String, Object>>() {
            });
        }

        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("TradeCase"))
                .findFirst();
        String xFormText = tf.get().getAllText();
        List<Map<String, Object>> Configs = JSON.parseObject(xFormText, new TypeReference<List<Map<String, Object>>>() {
        });
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String Mode = "修改";
            String preValue = "";
            String nowValue = "";
            String label = "";
            String type = "";
            String url = "";
            if (now.containsKey(field)) {
                nowValue = now.get(field).toString();
            }
            if (pre.containsKey(field)) {
                preValue = pre.get(field).toString();
            } else Mode = "新增";
            Optional<Map<String, Object>> findConfig = Configs.stream()
                    .filter(f -> f.get("name").toString().equals(field)).findFirst();
            if (findConfig.isPresent()) {
                Map<String, Object> Config = findConfig.get();
                label = Config.get("label").toString();
                type = Config.get("type").toString();
                if (Config.containsKey("url")) {
                    url = Config.get("url").toString();
                }
            }
            if (nowValue.equals(preValue) == false) {
                nowValue = translateValue(type, url, nowValue);
                preValue = translateValue(type, url, preValue);
                casesChangeRecord record = new casesChangeRecord();
                record.setCasesId(obj.getCasesid());
                record.setUserId(UserID);
                record.setMode(Mode);
                String ChangeText = label + "由:【" + preValue + "】，被修改为:【" + nowValue + "】";
                if (Mode == "新增") ChangeText = "新增了:" + label + ",值为:【" + nowValue + "】";
                record.setChangeText(ChangeText);
                record.setCreateTime(new Date());
                casesChangeRep.save(record);
            }
        }
    }

    private String translateValue(String type, String url, String value) {
        if (type.equals("text") == true || type.equals("textearea") == true || type.equals("date")) {
            return value;
        } else if (type.equals("select")) {
            String[] ss = url.split("\\?");
            Integer dtId = Integer.parseInt(ss[1].split("=")[1]);
            List<ComboboxItem> items = dictService.getByDtId(dtId);
            Optional<ComboboxItem> findItems = items.stream().filter(f -> f.getId().equals(value)).findFirst();
            if (findItems.isPresent()) {
                return findItems.get().getText();
            } else return value;
        } else return value;
    }

    @Autowired
    productItemTypeRepository productRep;

    /*
     *更新业务单数量
     * */
    private String getYwNums(String CasesID) {
        List<String> res = new ArrayList<>();
        List<casesYwItems> items = casesYwRep.findAllByCasesId(CasesID);
        for (int i = 0; i < items.size(); i++) {
            casesYwItems item = items.get(i);
            String num = Integer.toString(item.getNum());
            String Id = item.getYName();
            Optional<productItemType> findOnes = productRep.findFirstByFid(Id);
            if (findOnes.isPresent()) {
                String name = findOnes.get().getName();
                String r = num + name;
                res.add(r);
            }
        }
        return Strings.join(res, ';');
    }

    @Override
    public boolean createYwRecord(ywCreateInfo info) {
        int num = info.getNum();
        LoginUserInfo loginInfo = CompanyContext.get();
        for (int i = 0; i < num; i++) {
            casesYwItems sub = new casesYwItems();
            sub.setSubId(UUID.randomUUID().toString());
            sub.setCasesId(info.getCasesId());
            sub.setYid(info.getyId());
            Optional<productItemType> findOnes = productRep.findFirstByFid(info.getyId());
            if (findOnes.isPresent()) {
                sub.setYName(findOnes.get().getName());
            }
            sub.setGuan(info.getGuan());
            sub.setDai(info.getDai());
            sub.setNum(1);
            sub.setSignDate(info.getSignTime());
            sub.setYType(info.getYtype());
            sub.setSubNo(feeItemMapper.getFlowCode("SZ"));
            sub.setClientRequiredDate(info.getClientRequiredDate());
            sub.setPrice(info.getPrice());
            sub.setTotal(info.getDai() * 1 + info.getGuan());
            sub.setCreateMan(loginInfo.getUserIdValue());
            sub.setCreateTime(new Date());
            casesYwRep.save(sub);
        }
        return true;
    }

    @Override
    public void TradeCaseUserIDChange(Integer Transfer, Integer Resignation) throws Exception {
        loginUserInfo = CompanyContext.get();
        List<tradeCases> listTradeCase = new ArrayList<>();
        List<tbTradeCaseFollowRecord> listTradeCaseFollowRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        tradeCasesRep.findAll().stream().forEach(f -> {
            tbTradeCaseFollowRecord tradeCaseFollowRecord = new tbTradeCaseFollowRecord();
            tradeCases tradeCases = new tradeCases();
            tradeCases = f;

            String Record = "";
            String CreateManRecord = "";
            String CreateManagerRecord = "";
            String AuditManRecord = "";
            String AuditManagerRecord = "";
            String TechManManagerRecord = "";
            String TechManagerRecord = "";

            //商务人员
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    tradeCases.setCreateMan(Transfer);
                    CreateManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //商务人员经理
            if (f.getCreateManager() != null) {
                String createManager = f.getCreateManager();
                String result = "";
                String[] createManagers = createManager.split(",");
                for (String strCreateManager : createManagers) {
                    if (strCreateManager.equals(Resignation.toString())) {
                        result = Transfer + ",";
                        if (CreateManagerRecord.equals("")) {
                            CreateManagerRecord = "商务人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；";
                        }
                    }else result += strCreateManager + ",";
                }
                result = result.substring(0,result.length() - 1);
                tradeCases.setCreateManager(result);
            }

            //审核人员
            if (f.getAuditMan() != null) {
                if (f.getAuditMan() == Resignation) {
                    tradeCases.setAuditMan(Transfer);
                    AuditManRecord = "审核人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //审核人员经理
            if (f.getAuditManager() != null) {
                String auditManager = f.getAuditManager();
                String result = "";
                String[] auditManagers = auditManager.split(",");
                for (String strAuditManager : auditManagers) {
                    if (strAuditManager.equals(Resignation.toString())) {
                        if (AuditManagerRecord.equals("")) {
                            AuditManagerRecord = "审核人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；";
                        }
                        result += Transfer + ",";
                    }else result += strAuditManager + ",";
                }
                result = result.substring(0,result.length() - 1);
                tradeCases.setAuditManager(result);
            }

            //申报人员
            if (f.getTechMan() != null) {
                String techMan = f.getTechMan();
                String result = "";
                String[] techMans = techMan.split(",");
                for (String strTechMan : techMans) {
                    if (strTechMan.equals(Resignation.toString())) {
                        result += Transfer + ",";
                        if (TechManManagerRecord.equals("")) {
                            TechManManagerRecord = "申报人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                        }
                    }else result += strTechMan + ",";
                }
                result = result.substring(0,result.length() - 1);
                tradeCases.setTechMan(result);
            }
            //申报人员经理
            if(f.getTechManager() != null) {
                String techManager = f.getTechManager();
                String result = "";
                String[] techManagers = techManager.split(",");
                for (String strTechManager : techManagers) {
                    if (strTechManager.equals(Resignation.toString())) {
                        result += Transfer + ",";
                        if (TechManagerRecord.equals("")) {
                            TechManagerRecord = "申报人员经理由：" + loginResignation + "变更为：" + loginTransfer ;
                        }
                    }else result += strTechManager + ",";
                }
                result = result.substring(0,result.length() - 1);
                tradeCases.setTechManager(result);
            }
            listTradeCase.add(tradeCases);
            Record = CreateManRecord + CreateManagerRecord + AuditManRecord + AuditManagerRecord + TechManManagerRecord + TechManagerRecord;
            if (!Record.equals("")) {
                tradeCaseFollowRecord.setRecord(Record);
                tradeCaseFollowRecord.setTradeCaseId(f.getId());
                tradeCaseFollowRecord.setCreateMan(loginUserInfo.getUserIdValue());
                tradeCaseFollowRecord.setCreateTime(new Date());
                listTradeCaseFollowRecord.add(tradeCaseFollowRecord);
            }
        });
        if (listTradeCaseFollowRecord.size() > 0) {
            tradeCaseFollowRecordRepository.saveAll(listTradeCaseFollowRecord);
        }
        if (listTradeCase.size() > 0) {
            tradeCasesRep.saveAll(listTradeCase);
        }
    }


}
