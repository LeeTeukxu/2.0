package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.LoginUserUtils;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.events.caseStateChangedEvent;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.UserPermission;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CaseNewServiceImpl implements ICaseNewService {
    @Autowired
    casesMainRepository casesRep;
    @Autowired
    casesNewAttachmentRepository casesAttRep;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    ItbDictDataService dictService;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesMemoRepository memoRep;

    LoginUserInfo loginUserInfo;
    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;

    @Autowired
    productItemTypeRepository productRep;

    @Autowired
    casesSubFilesRepository subFileRep;
    @Autowired
    StringRedisTemplate redisRep;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    casesUserRepository casesUserRep;
    @Autowired
    ICasesSubUserService casesSubService;
    @Autowired
    casesSubUserRepository casesSubUserRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    arrivalUseDetailRepository arrivalRep;
    @Autowired
    tbArrivalRegistrationRepository arrRep;
    @Autowired
    arrivalUseDetailRepository detailRep;
    @Autowired
    IClientInfoService clientService;
    Map<String, String> ClientNames = new HashMap<>();
    @Autowired
    UserPermission userPermission;
    @Autowired
    LoginUserUtils loginUserUtils;
    @Autowired
    ApplicationEventPublisher publisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public casesMain SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        casesMain main = SaveMain(Data);
        String CasesID = main.getCasesId();
        String Action = Data.get("Action").toString();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(CasesID, atts);
        }
        if (Data.containsKey("AJ")) {
            String AJText = Data.get("AJ").toString();
            List<casesSub> items = JSON.parseArray(AJText, casesSub.class);
            if (items.size() > 0) SaveAJ(Action, main, CasesID, items);
            else {
                if (Action.equals("Commit")) throw new Exception("??????????????????????????????????????????!");
            }
        } else {
            if (Action.equals("Commit")) throw new Exception("??????????????????????????????????????????!");
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

    private String GetAJNumbers(List<casesSub> subs) {
        List<String> Res = new ArrayList<>();
        List<String> Names = subs.stream().map(f -> f.getyName()).distinct().collect(Collectors.toList());
        for (int i = 0; i < Names.size(); i++) {
            String Name = Names.get(i);
            Integer Num = subs.stream().filter(f -> f.getyName().equals(Name)).mapToInt(f -> f.getNum()).sum();
            Res.add(Integer.toString(Num) + Name);
        }
        return StringUtils.join(Res, ",");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean Commit(int ID, String Result, String ResultText) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (ID < 1) throw new Exception("????????????");
        int UserID = Integer.parseInt(Info.getUserId());
        String ManagerID = loginUserMapper.getManager(Info.getUserId());
        Optional<casesMain> findOne = casesRep.findById(ID);
        if (findOne.isPresent()) {
            casesMain main = findOne.get();
            if (main.getState() != 2) throw new Exception("????????????????????????");
            if (main.getClientId() == null) throw new Exception("??????????????????????????????????????????????????????????????????!");
            if (Result.equals("????????????")) {
                main.setState(4);
                main.setSignTime(new Date());
            } else {
                main.setState(3);
                main.setAuditMan(null);
                main.setAuditManager(null);
            }

            List<casesSub> childs = subRep.findAllByCasesId(main.getCasesId());
            if (Result.equals("????????????") == true) {
                if (childs.size() == 0) throw new Exception("??????????????????????????????????????????!");
            }
            main.setAuditMan(UserID);
            main.setAuditTime(new Date());
            main.setAuditText(ResultText);
            main.setAuditManager(ManagerID);
            if (main.isAllMoneySet() == false) {
                Double X = childs.stream().mapToDouble(f -> f.getGuanMoney() + f.getDaiMoney()).sum();
                main.setAllMoney(X);
            }
            /*????????????????????????*/
            main.setNums(GetAJNumbers(childs));

            casesRep.save(main);

            if (Result.equals("????????????")) {
                /*???????????????*/
                casesUserService.AddOne(main.getCasesId(), UserID);
            }
            for (int i = 0; i < childs.size(); i++) {
                casesSub sub = childs.get(i);
                String subId = sub.getSubId();
                if (Result.equals("????????????")) {
                    Integer requiredDays = sub.getRequiredDays();
                    if (requiredDays == null) {
                        String yId = sub.getYid();
                        Optional<productItemType> findTypes = productRep.findFirstByFid(yId);
                        if (findTypes.isPresent()) {
                            requiredDays = findTypes.get().getMaxDays();
                        }
                    }
                    if (requiredDays == null) {
                        throw new Exception(sub.getYName() + "????????????,????????????!");
                    }
                    sub.setClientRequiredDate(DateUtils.addDays(new Date(), requiredDays));
                    sub.setCanUse(true);
                    List<casesSubFiles> files = subFileRep.getAllBySubIdAndType(subId, "Zl");
                    if (files.size() > 0) {
                        String zlFiles = StringUtils.join(files.stream()
                                        .map(f -> f.getAttId()).collect(Collectors.toList()),
                                ',');
                        sub.setZlFiles(zlFiles);
                    }
                    List<casesSubFiles> files1 = subFileRep.getAllBySubIdAndType(subId, "Tech");
                    if (files1.size() > 0) {
                        String techFiles = StringUtils.join(files1.stream().map(f -> f.getAttId()).collect(Collectors
                                .toList()), ',');
                        sub.setTechFiles(techFiles);
                    }
                    sub.setProcessState(50);
                } else {
                    sub.setCanUse(false);
                }
                Double Guan = sub.getGuanMoney();
                Double Dai = sub.getDaiMoney();
                Integer Num = sub.getNum();
                Double Total = Dai * Num + Guan;
                sub.setTotalMoney(Total);
                subRep.save(sub);
            }
            if (Result.equals("????????????")) {
                int ClientID = main.getClientId();
                Optional<tbClient> findClients = clientRep.findById(ClientID);
                if (findClients.isPresent()) {
                    tbClient client = findClients.get();
                    client.setCootype(1);
                    clientRep.save(client);
                } else throw new Exception(Integer.toString(ClientID) + "????????????????????????!");
            }

        } else {
            throw new Exception("???????????????????????????????????????!");
        }
        return true;
    }

    @CacheEvict(value = {"getAllCasesMain", "getAllCasesSub"}, keyGenerator = "CompanyKeyGenerator")
    @Override
    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "????????????????????????")
    public boolean Remove(String CasesID) throws Exception {
        LoginUserInfo log = CompanyContext.get();

        Optional<casesMain> findMains = casesRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            casesMain oneMain = findMains.get();
            Integer State = oneMain.getState();
            if (State >= 4) throw new Exception("??????????????????????????????!");
        } else throw new Exception("???????????????????????????!");

        List<arrivalUseDetail> findArrs = arrivalRep.findAllByCasesIdAndState(CasesID,2);
        if (findArrs.size() > 0) {
            throw new Exception("??????????????????????????????????????????????????????!");
        }
        List<casesChangeRecord> changeRecords = casesChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
        casesChangeRep.deleteAll(changeRecords);
        casesAttRep.deleteAllByCasesId(CasesID);
        memoRep.removeAllByCasesid(CasesID);
        List<casesSub> casesSubs = subRep.findAllByCasesId(CasesID);
        for (int i = 0; i < casesSubs.size(); i++) {
            casesSub sub = casesSubs.get(i);
            String SubNo = sub.getSubNo();
            if (StringUtils.isEmpty(SubNo) == false) {
                String Type = "ZL";
                String YearMonth = SubNo.substring(2, SubNo.length() - 4);
                Integer Num = Integer.parseInt(SubNo.substring(SubNo.length() - 4));
                feeItemMapper.deleteFlowCode(Type, Num, YearMonth);
            }
        }
        subRep.deleteAllByCasesId(CasesID);
        casesAttRep.deleteAllByCasesId(CasesID);
        Optional<casesMain> findOnes = casesRep.findFirstByCasesId(CasesID);
        if (findOnes.isPresent()) {
            casesRep.delete(findOnes.get());
        }
        subFileRep.deleteAllByCasesId(CasesID);
        casesUserRep.deleteAllByCasesId(CasesID);
        casesSubUserRep.deleteAllByCasesId(CasesID);
        return false;
    }

    @Override
    public boolean createAJRecord(ajCreateInfo info) {
        int num = info.getNum();
        LoginUserInfo loginInfo = CompanyContext.get();
        for (int i = 0; i < num; i++) {
            casesSub sub = new casesSub();
            sub.setSubId(UUID.randomUUID().toString());
            sub.setCasesId(info.getCasesId());
            sub.setYid(info.getyId());
            Optional<productItemType> findOnes = productRep.findFirstByFid(info.getyId());
            if (findOnes.isPresent()) {
                sub.setYName(findOnes.get().getName());
            }
            sub.setGuanMoney(info.getGuanMoney());
            sub.setDaiMoney(info.getDaiMoney());
            sub.setNum(1);
            sub.setcLevel(info.getcLevel());
            sub.setTotalMoney(info.getDaiMoney() * 1 + info.getGuanMoney());
            sub.setSubNo(feeItemMapper.getFlowCode("ZL"));
            //sub.setClientRequiredDate(info.getClientRequiredDate());
            sub.setRequiredDays(info.getRequiredDays());
            sub.setCreateMan(loginInfo.getUserIdValue());
            sub.setCreateTime(new Date());
            sub.setHasTech(info.getHasTech());
            sub.setSupportMan(info.getSupportMan());
            subRep.save(sub);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Complete(String CasesID, int State) throws Exception {
        LoginUserInfo log = CompanyContext.get();
        Optional<casesMain> findOnes = casesRep.findFirstByCasesId(CasesID);
        if (findOnes.isPresent()) {
            casesMain main = findOnes.get();
            main.setState(State);
            List<casesSub> subs = subRep.findAllByCasesId(CasesID);
            for (int i = 0; i < subs.size(); i++) {
                casesSub sub = subs.get(i);
                if (State == 8) {
                    sub.setIsComplete(true);
                } else sub.setIsComplete(false);
                subRep.save(sub);
            }
            if (State == 8) {
                main.setCompleteMan(log.getUserIdValue());
                main.setCompleteTime(new Date());
            } else {
                main.setCompleteMan(null);
                main.setCompleteTime(null);
            }
            casesRep.save(main);
            casesUserService.AddOne(main.getCasesId(), log.getUserIdValue());
        }
        redisRep.delete("getAllCasesMain::" + log.getCompanyId());
        redisRep.delete("getAllCasesSub::" + log.getCompanyId());
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveSelectMoney(SelectMoneyInfo Info) throws Exception {
        LoginUserInfo LogInfo = CompanyContext.get();
        Integer ArrID = Info.getArrID();
        Optional<tbArrivalRegistration> findArrs = arrRep.findById(ArrID);
        if (findArrs.isPresent()) {
            Double SelectTotal = Info.getDai() + Info.getGuan();
            tbArrivalRegistration main = findArrs.get();
            Double PayAmount = Double.parseDouble(main.getPaymentAmount());
            if (PayAmount == 0) throw new Exception("???????????????0???????????????????????????!");

            Optional<casesMain> findMains = casesRep.findFirstByCasesId(Info.getCasesID());
            if (findMains.isPresent()) {
                List<casesSub> Subs = subRep.findAllByCasesId(Info.getCasesID());
                if (Subs.size() == 0) throw new Exception("???????????????????????????!");
                Double CasesMoney = Subs.stream().mapToDouble(f -> f.getDaiMoney() + f.getGuanMoney()).sum();
                if (CasesMoney == 0) throw new Exception("???????????????????????????0");
                if (SelectTotal > CasesMoney)
                    throw new Exception("????????????(" + Double.toString(SelectTotal) + ")????????????????????????(" + Double.toString(CasesMoney) + ")");

            } else throw new Exception("???????????????????????????!");
            List<arrivalUseDetail> savedDatas = detailRep.findAllByArrId(ArrID);
            Double savedMoney =
                    savedDatas.stream().filter(f -> f.getState() != 1).mapToDouble(f -> f.getDai() + f.getGuan()).sum();
            Double NowTotal = savedMoney + Info.getDai() + Info.getGuan();
            if (NowTotal > PayAmount) throw new Exception("??????????????????????????????????????????!");

            arrivalUseDetail detail = new arrivalUseDetail();
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
            detail.setMemo("???????????????????????????????????????");
            detailRep.save(detail);

            main.setClaimant(LogInfo.getUserIdValue());
            main.setClaimStatus(2);
            main.setClaimDate(new Date());
            main.setReviewerStatus(0);
            main.setKhName(Info.getClientName());
            main.setCustomerId(Integer.toString(Info.getClientID()));
            arrRep.save(main);
        } else throw new Exception("?????????????????????????????????!");
    }

    private casesMain SaveMain(Map<String, Object> Data) throws Exception {
        casesMain main = new casesMain();
        String CasesID = "";
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("????????????!");
        if (Data.containsKey("id")) {
            CasesID = Data.get("casesId").toString();
            ID = Integer.parseInt(Data.get("id").toString());
        }
        if (Data.containsKey("casesId")) {
            CasesID = Data.get("casesId").toString();
        }
        Data.remove("state");
        Optional<casesMain> finds = casesRep.findById(ID);
        if (finds.isPresent() == false) {
            finds = casesRep.findFirstByCasesId(CasesID);
        }
        if (finds.isPresent()) {
            main = finds.get();
            main.setPreFormText(main.getFormText());
            int state = main.getState();
            if (state > 4) throw new Exception("????????????????????????????????????!");
            if (Action.equals("Save")) {
                main.setState(1);
            } else main.setState(2);

        } else {
            main.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
            main.setCreateTime(new Date());
            main.setCasesId(UUID.randomUUID().toString());
            if (Action.equals("Save")) main.setState(1);
            else main.setState(2);

            main.setDocSn(feeItemMapper.getFlowCode("YWJD"));
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
        if (Action.equals("Commit")) {
            if (Data.containsKey("AuditMan")) {
                Integer XMan = Integer.parseInt(Data.get("AuditMan").toString());
                main.setAuditMan(XMan);
            }

            main.setCreateTime(new Date());//????????????????????????????????????????????????
        }
        casesRep.save(main);

        /*??????????????????*/
        casesUserService.AddOne(main.getCasesId(), main.getCreateMan());

        if (Action.equals("Commit")) {
            SendToAuditMan(main);
        }
        return main;
    }

    private void SaveAttachment(String CasesID, List<String> IDS) {
        casesAttRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            casesNewAttachment att = new casesNewAttachment();
            att.setCasesId(CasesID);
            att.setAttId(ID);
            casesAttRep.save(att);
        }
    }

    private void SaveAJ(String Action, casesMain main, String CasesID, List<casesSub> items) throws Exception {
        for (int i = 0; i < items.size(); i++) {
            casesSub row = items.get(i);
            int Id = row.getId();
            Optional<casesSub> nowOnes = subRep.findById(Id);
            if (nowOnes.isPresent()) {
                casesSub nowOne = nowOnes.get();
                nowOne.setMemo(row.getMemo());
                nowOne.setShenqingName(row.getShenqingName());
                nowOne.setGuanMoney(row.getGuanMoney());
                nowOne.setDaiMoney(row.getDaiMoney());
                nowOne.setClientRequiredDate(row.getClientRequiredDate());
                nowOne.setRelNo(row.getRelNo());
                nowOne.setRelId(row.getRelId());
                nowOne.setcLevel(row.getcLevel());
                nowOne.setClientLinkMan(row.getClientLinkMan());
                nowOne.setClientLinkMail(row.getClientLinkMail());
                nowOne.setClientLinkPhone(row.getClientLinkPhone());
                nowOne.setSupportMan(row.getSupportMan());
                nowOne.setHasTech(row.getHasTech());

                Integer HasTech = row.getHasTech();
                if (HasTech == null) throw new Exception(nowOne.getSubNo() + "??????????????????????????????!");

                if (Action.equals("Commit")) {
                    String TechFiles = StringUtils.trim(nowOne.getTechFiles());
                    if (StringUtils.isEmpty(TechFiles)) {
                        if (HasTech == 1) {
                            throw new Exception(nowOne.getSubNo() + "???????????????????????????????????????!");
                        }
                    }
                }
                nowOne.setIsComplete(false);
                subRep.save(nowOne);
            }
        }
        if (main.isAllMoneySet() == false) {
            List<casesSub> subs = subRep.findAllByCasesId(CasesID);
            Double AllMoney = subs.stream().mapToDouble(f -> f.getDaiMoney() + f.getGuanMoney()).sum();
            main.setAllMoney(AllMoney);
            casesRep.save(main);
        }
    }

    private void CreateChangeRecord(List<String> fields, casesMain obj) {
        LoginUserInfo Info = CompanyContext.get();
        int UserID = Integer.parseInt(Info.getUserId());
        String formText = obj.getFormText();
        try {
            ClientNames = clientService.getAllByIDAndName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String preFormText = obj.getPreFormText();
        Map<String, Object> pre = new HashMap<>();
        Map<String, Object> now = JSON.parseObject(formText, new TypeReference<Map<String, Object>>() {
        });
        if (ObjectUtils.isEmpty(preFormText) == false) {
            pre = JSON.parseObject(preFormText, new TypeReference<Map<String, Object>>() {
            });
        }

        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        String xFormText = tf.get().getAllText();
        List<Map<String, Object>> Configs = JSON.parseObject(xFormText, new TypeReference<List<Map<String, Object>>>() {
        });
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String Mode = "??????";
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
            } else Mode = "??????";
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
                if (label.equals("????????????")) {
                    String nowName = ClientNames.get(nowValue);
                    String preName = ClientNames.get(preValue);
                    nowValue = nowName;
                    preValue = preName;
                }
                casesChangeRecord record = new casesChangeRecord();
                record.setCasesId(obj.getCasesId());
                record.setUserId(UserID);
                record.setMode(Mode);
                String ChangeText = label + "???:???" + preValue + "??????????????????:???" + nowValue + "???";
                if (Mode == "??????") ChangeText = "?????????:" + label + ",??????:???" + nowValue + "???";
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

    private void SendToAuditMan(casesMain main) {
        List<String> roles = userPermission.findAllByFunName("AuditCases");
        List<LoginUserInfo> users = loginUserUtils.findAllByRoleNames(roles);
        List<String> accounts = users.stream().map(f -> f.getAccount()).distinct().collect(Collectors.toList());
        caseStateChangedEvent event = new caseStateChangedEvent(this);
        event.setId(main.getCasesId());
        event.setType("Main");
        event.setName("??????????????????");
        event.setUsers(accounts);
        event.setMessage(main.getDocSn() + "?????????????????????????????????!");
        event.setTimes(new Date());
        publisher.publishEvent(event);
    }

    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "???????????????????????????")
    public void ReAudit(String CasesID) throws Exception {
        Optional<casesMain> findMains = casesRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            casesMain main = findMains.get();
            main.setState(3);
            main.setAuditMan(null);
            main.setAuditManager(null);
            main.setAuditTime(null);
            main.setAuditText(null);
            casesRep.save(main);
        } else throw new Exception("???????????????????????????????????????????????????!");
        List<casesSub> subs = subRep.findAllByCasesId(CasesID);
        for (casesSub sub : subs) {
            Integer TechMan = sub.getTechMan();
            if (TechMan == null) TechMan = 0;
            if (TechMan > 0) {
                throw new Exception("????????????:???" + sub.getSubNo() + "????????????????????????????????????????????????????????????!");
            }
            sub.setCanUse(false);
        }
        subRep.saveAll(subs);
    }

    @Override
    public void CaseMainIDChange(Integer Transfer, Integer Resignation) throws Exception {
        loginUserInfo = CompanyContext.get();

        List<casesMain> listCasesMan = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        casesRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            casesMain casesMain = new casesMain();
            casesMain = f;

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

            //????????????
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    casesMain.setCreateMan(Transfer);
                    CreateManRecord = "??????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }
            //??????????????????
            if (f.getCreateManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCreateManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CreateManagerRecord = iar.getRecord();
                casesMain.setCreateManager(iar.getId());
            }

            //??????????????????
            if (f.getAuditMan() != null) {
                if (f.getAuditMan() == Resignation) {
                    casesMain.setAuditMan(Transfer);
                    AuditManRecord = "????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }
            //????????????????????????
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                casesMain.setAuditManager(iar.getId());
            }

            //??????????????????
            if (f.getTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManRecord = iar.getRecord();
                casesMain.setTechMan(iar.getId());
            }

            //??????????????????
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                casesMain.setTechManager(iar.getId());
            }

            //????????????
            if (f.getCwMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManRecord = iar.getRecord();
                casesMain.setCwMan(iar.getId());
            }

            //??????????????????
            if (f.getCwManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManagerRecord = iar.getRecord();
                casesMain.setCwManager(iar.getId());
            }

            //??????????????????
            if (f.getCompleteMan() != null) {
                if (f.getCompleteMan() == Resignation) {
                    casesMain.setCompleteMan(Transfer);
                    CompleteManRecord = "????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            listCasesMan.add(casesMain);
            Record =
                    CreateManRecord + CreateManagerRecord + AuditManRecord + AuditManagerRecord + TechManRecord + TechManagerRecord + CWManRecord + CWManagerRecord + CompleteManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseMainChange");
                crecord.setChangeText(Record);
                crecord.setUserId(loginUserInfo.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });
    }

    private IdAndRecord GetCreateManager(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String createManager = f.getCreateManager();
        String result = "";
        String[] createManagers = createManager.split(",");
        for (String strCreateManager : createManagers) {
            if (strCreateManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("??????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strCreateManager + ",";
        }
        result = result.substring(0, result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("??????????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strAuditManager + ",";
        }
        result = result.substring(0, result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechMan(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techMan = f.getTechMan();
        String result = "";
        String[] techMans = techMan.split(",");
        for (String strTechMan : techMans) {
            if (strTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strTechMan + ",";
        }
        result = result.substring(0, result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManager : techManagers) {
            if (strTechManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strTechManager + ",";
        }
        result = result.substring(0, result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwMan(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwMan = f.getCwMan();
        String result = "";
        String[] cwMans = cwMan.split(",");
        for (String strCwMan : cwMans) {
            if (strCwMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("??????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strCwMan + ",";
        }
        result = result.substring(0, result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwManager(casesMain f, Integer Transfer, Integer Resignation, String loginResignation,
            String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwManager = f.getCwManager();
        String result = "";
        String[] cwManagers = cwManager.split(",");
        for (String strCwManager : cwManagers) {
            if (strCwManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            } else result += strCwManager + ",";
        }
        result = result.substring(0, result.length() - 1);
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
