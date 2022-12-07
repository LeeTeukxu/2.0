package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICaseService;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class CaseServiceImpl implements ICaseService {
    @Autowired
    casesRepository casesRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAjDetailRepository casesAjRep;
    @Autowired
    tbFormDesignRepository formDesignRep;

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
    LoginUserInfo loginUserInfo;
    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;

    @Override
    public @Transactional(rollbackOn = Exception.class)
    cases SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        cases main = SaveMain(Data);
        String CasesID = main.getCasesid();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(CasesID, atts);
        }
        if (Data.containsKey("AJ")) {
            String AJText = Data.get("AJ").toString();
            List<casesAjDetail> items = JSON.parseArray(AJText, casesAjDetail.class);
            if (items.size() > 0) SaveAJ(CasesID, items);
        }
        if (Data.containsKey("watchFields")) {
            Object OX = Data.get("watchFields");
            if (ObjectUtils.isEmpty(OX) == false) {
                String watchText = OX.toString();
                List<String> fields = JSON.parseArray(watchText, String.class);
                if (fields.size() > 0) CreateChangeRecord(fields, main);
            }
        }
        arrivalRegistrationRepository.RenLinUpdateClientCooType(1, main.getClientId());
        return main;
    }

    @Override
    public boolean Commit(int ID, String Result, String ResultText) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (Info.getRoleName().equals("流程人员") == false) {
            throw new Exception("无操作权限!");
        }
        if (ID < 1) throw new Exception("参数异常");
        int UserID = Integer.parseInt(Info.getUserId());
        Optional<cases> findOne = casesRep.findById(ID);
        if (findOne.isPresent()) {
            cases main = findOne.get();
            if (main.getState() != 2) throw new Exception("业务状态不正确。");
            if (Result.equals("同意交单")) {
                main.setState(4);
                main.setNums(getYwNums(main.getCasesid()));

            } else main.setState(3);
            main.setAuditMan(UserID);
            main.setAuditTime(new Date());
            main.setAuditText(ResultText);
            main.setAuditManager(loginUserMapper.getManager(Info.getUserId()));
            casesRep.save(main);
        } else {
            throw new Exception("操作业务已不存在!");
        }
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean Remove(String CasesID) throws Exception {
        List<casesChangeRecord> changeRecords = casesChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
        casesChangeRep.deleteAll(changeRecords);

        casesAttRep.deleteAllByCasesId(CasesID);

        casesYwRep.deleteAllByCasesId(CasesID);

        memoRep.removeAllByCasesid(CasesID);

        casesAjRep.deleteAllByCasesId(CasesID);
        casesAttRep.deleteAllByCasesId(CasesID);
        Optional<cases> findOnes = casesRep.findFirstByCasesid(CasesID);
        if (findOnes.isPresent()) {
            casesRep.delete(findOnes.get());
        }
        return false;
    }

    private cases SaveMain(Map<String, Object> Data) throws Exception {
        cases main = new cases();
        String CasesID = "";
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            CasesID = Data.get("casesId").toString();
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<cases> finds = casesRep.findById(ID);
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
        if (main.getState() == 2) {
            main.setDocSn(feeItemMapper.getFlowCode("YWJD"));
        }
        String myManager = loginUserMapper.getManager(loginUserInfo.getUserId());
        main.setCreateManager(myManager);
        casesRep.save(main);
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

    private void SaveYw(String CasesID, int ClientID, List<casesYwItems> items) {
        casesYwRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < items.size(); i++) {
            casesYwItems obj = items.get(i);
            obj.setClientId(ClientID);
            obj.setCasesId(CasesID);
            obj.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
            obj.setCreateTime(new Date());
            casesYwRep.save(obj);
        }
    }

    private void SaveAJ(String CasesID, List<casesAjDetail> items) {
        //casesAjRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < items.size(); i++) {
            casesAjDetail obj = items.get(i);
            String ajId = obj.getAjid();
            if (StringUtils.isEmpty(ajId) == true) {
                obj.setCasesId(CasesID);
                ajId = UUID.randomUUID().toString();
                obj.setAjid(ajId);
                obj.setAjCode(feeItemMapper.getFlowCode("ZL"));
                obj.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
                obj.setCreateTime(new Date());
                casesAjRep.save(obj);
            } else {
                Optional<casesAjDetail> finds = casesAjRep.findFirstByAjid(ajId);
                if (finds.isPresent()) {
                    casesAjDetail findOne = finds.get();
                    findOne.setAjMemo(obj.getAjMemo());
                    findOne.setAjCode(obj.getAjCode());
                    findOne.setAjLinkMan(obj.getAjLinkMan());
                    findOne.setAjLinkPhone(obj.getAjLinkPhone());
                    findOne.setAjName(obj.getAjName());
                    findOne.setAjWriteMan(obj.getAjWriteMan());
                    findOne.setAjType(obj.getAjType());
                    findOne.setAttIds(obj.getAttIds());
                    findOne.setClientId(obj.getClientId());
                    casesAjRep.save(findOne);
                }
            }
            SaveAJAttachments(CasesID, ajId, obj.getAttIds());
        }
    }

    @Autowired
    casesAjAttachmentRepository ajAttRep;

    private void SaveAJAttachments(String CasesID, String AJID, String AttIDS) {
        if (StringUtils.isEmpty(AttIDS)) AttIDS = "";
        ajAttRep.deleteAllByAjid(AJID);
        String[] Atts = AttIDS.split(",");
        for (int i = 0; i < Atts.length; i++) {
            String Att = Atts[i];
            if (StringUtils.isEmpty(Att) == false) {
                casesAjAttachment obj = new casesAjAttachment();
                obj.setAjid(AJID);
                obj.setCasesId(CasesID);
                obj.setAttId(Att);
                ajAttRep.save(obj);
            }
        }
    }

    private void CreateChangeRecord(List<String> fields, cases obj) {
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

        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
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
}
