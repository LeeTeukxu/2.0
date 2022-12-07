package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.TechSupportMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.IClientInfoService;
import com.zhide.dtsystem.services.define.ITechSupportService;
import com.zhide.dtsystem.services.define.ITechSupportUserService;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: TechSupportServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月18日 9:33
 **/
@Service
public class TechSupportServiceImpl implements ITechSupportService {
    LoginUserInfo loginUserInfo;
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    techSupportMainRepository mainRep;
    @Autowired
    techSupportSubRepository subRep;
    @Autowired
    techSupportUserRepository userRep;
    @Autowired
    ITechSupportUserService techUserService;
    @Autowired
    techSupportAttachmentRepository techAttRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    ItbDictDataService dictService;
    @Autowired
    techSupportChangeRecordRepository changeRep;
    @Autowired
    IClientInfoService clientService;
    @Autowired
    TechSupportMapper mainMapper;
    @Autowired
    casesSubRepository casesSubRep;
    Map<String,String> ClientNames=new HashMap<>();
    @Override
    @Transactional(rollbackFor = Exception.class)
    public techSupportMain SaveAll(Map<String, Object> Data) throws  Exception {
        loginUserInfo = CompanyContext.get();
        techSupportMain main = SaveMain(Data);
        String CasesID = main.getCasesId();
        String Action = Data.get("Action").toString();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(CasesID, atts);
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
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = mainMapper.getData(params);
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
    public pageObject getFiles(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = mainMapper.getFiles(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    private techSupportMain SaveMain(Map<String, Object> Data) throws Exception {
        techSupportMain main = new techSupportMain();
        String CasesID = "";
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            CasesID = Data.get("casesId").toString();
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<techSupportMain> finds = mainRep.findById(ID);
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

            main.setDocSn(feeItemMapper.getFlowCode("ZLWJ"));
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
        String TechMan=main.getTechMan();
        if(StringUtils.isEmpty(TechMan)==false){
            List<String> MMS=new ArrayList<>();
            String[] mans=TechMan.split(",");
            for(int i=0;i<mans.length;i++){
                String Man=mans[i];
                String Manager=loginUserMapper.getManager(Man);
                if(MMS.contains(Manager)==false)MMS.add(Manager);
            }
            main.setTechManager(StringUtils.join(MMS,","));
        } //else throw new Exception("必须指定技术挖掘人员!");
        mainRep.save(main);
        /*插入商务人员*/
        techUserService.AddOne(main.getCasesId(), main.getCreateMan());
        techUserService.AddAll(CasesID,main.getCreateManager());

        if(main.getTechMan()!=null) {
            techUserService.AddAll(CasesID, main.getTechMan());
            techUserService.AddAll(CasesID, main.getTechManager());
        }
        return main;
    }
    private  void SaveAttachment(String CasesID, List<String> IDS) {
        //techAttRep.deleteAllByCasesId(CasesID);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<techSupportAttachment> findAtts=techAttRep.findFirstByCasesIdAndAttId(CasesID,ID);
            if(findAtts.isPresent()==false) {
                techSupportAttachment att = new techSupportAttachment();
                att.setCasesId(CasesID);
                att.setAttId(ID);
                att.setCreateTime(new Date());
                techAttRep.save(att);
            }
            //att.setDocSn(feeItemMapper.getFlowCode("JDWJ"));
        }
    }
    private void SaveAJ(techSupportMain main, String CasesID, List<techSupportSub> items) {
        for (int i = 0; i < items.size(); i++) {
            techSupportSub row = items.get(i);
            int Id = row.getId();
            Optional<techSupportSub> nowOnes = subRep.findById(Id);
            if (nowOnes.isPresent()) {
                techSupportSub nowOne = nowOnes.get();
                nowOne.setMemo(row.getMemo());
                nowOne.setClientRequiredDate(row.getClientRequiredDate());
                nowOne.setIsComplete(false);
                subRep.save(nowOne);
            }
        }
    }
    private void CreateChangeRecord(List<String> fields, techSupportMain obj) {
        LoginUserInfo Info = CompanyContext.get();
        int UserID = Integer.parseInt(Info.getUserId());
        String formText = obj.getFormText();
        try {
            ClientNames= clientService.getAllByIDAndName();
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
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("Tech")).findFirst();
        String xFormText = tf.get().getAllText();
        List<Map<String, Object>> Configs = JSON.parseObject(xFormText, new TypeReference<List<Map<String, Object>>>() {});
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
                if(label.equals("客户名称")){
                    String nowName=ClientNames.get(nowValue);
                    String preName=ClientNames.get(preValue);
                    nowValue=nowName;
                    preValue=preName;
                }
                techSupportChangeRecord record = new techSupportChangeRecord();
                record.setCasesId(obj.getCasesId());
                record.setUserId(UserID);
                record.setMode(Mode);
                String ChangeText = label + "由:【" + preValue + "】，被修改为:【" + nowValue + "】";
                if (Mode == "新增") ChangeText = "新增了:" + label + ",值为:【" + nowValue + "】";
                record.setChangeText(ChangeText);
                record.setCreateTime(new Date());
                changeRep.save(record);
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
    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageSize * pageIndex - 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String StateText = request.getParameter("State");
        if (StringUtils.isEmpty(StateText) == false) {
            params.put("State", Integer.parseInt(StateText));
        }
        String Mode= request.getParameter("Mode");
        if(StringUtils.isEmpty(Mode))Mode="Get";
        params.put("Mode",Mode);

        String CasesID=request.getParameter("CasesID");
        params.put("CasesID",CasesID);
        String RefID= request.getParameter("RefID");
        if(StringUtils.isEmpty(RefID))RefID="";
        if(Mode.equals("UnSelect"))RefID="";
        params.put("RefID",RefID);
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean Commit(int ID, String Result, String ResultText) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (ID < 1) throw new Exception("参数异常");
        int UserID = Integer.parseInt(Info.getUserId());
        String ManagerID = loginUserMapper.getManager(Info.getUserId());
        Optional<techSupportMain> findOne = mainRep.findById(ID);
        if (findOne.isPresent()) {
            techSupportMain main = findOne.get();
            if (main.getState() != 2) throw new Exception("业务状态不正确。");
            if (main.getClientId() == null) throw new Exception("客户信息不能为空，请完善信息后再进行提交操作!");
            if (Result.equals("同意接单")) {
                main.setState(4);
                main.setAuditMan(UserID);
                main.setAuditTime(new Date());
                main.setAuditText(ResultText);
                main.setAuditManager(ManagerID);

                String TechMan=main.getTechMan();
                if(StringUtils.isEmpty(TechMan)==false){
                    List<String> MMS=new ArrayList<>();
                    String[] mans=TechMan.split(",");
                    for(int i=0;i<mans.length;i++){
                        String Man=mans[i];
                        String Manager=loginUserMapper.getManager(Man);
                        if(MMS.contains(Manager)==false)MMS.add(Manager);
                    }
                    main.setTechManager(StringUtils.join(MMS,","));
                } else {
                    main.setTechMan(Info.getUserId());
                    String Manager=loginUserMapper.getManager(Info.getUserId());
                    main.setTechManName(Manager);
                }
                techUserService.AddAll(main.getCasesId(), main.getTechMan());
                techUserService.AddAll(main.getCasesId(), main.getTechManager());
            } else {
                main.setState(3);
                main.setAuditMan(null);
                main.setAuditManager(null);
            }
            mainRep.save(main);
        } else {
            throw new Exception("当前审核的业务对象已不存在!");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "删除技术交底文件")
    public void DeleteTechFile(String CasesID, String AttID) throws Exception {
        Optional<tbAttachment> findAtts=attRep.findAllByGuid(AttID);
        if(findAtts.isPresent()){
            attRep.delete(findAtts.get());
        }
        Optional<techSupportAttachment> findTechs=techAttRep.findFirstByCasesIdAndAttId(CasesID,AttID);
        if(findTechs.isPresent()){
            techAttRep.delete(findTechs.get());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void CheckFile(String SubID, String AttID) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        Optional<techSupportAttachment> findAtts=techAttRep.findFirstByAttId(AttID);
        Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
        Optional<casesSub> findSubs=casesSubRep.findFirstBySubId(SubID);
        if(findAtts.isPresent() && findFiles.isPresent() && findSubs.isPresent()){
            techSupportAttachment tAtt= findAtts.get();
            tbAttachment file=findFiles.get();
            casesSub sub= findSubs.get();
            if(tAtt.getRefId().equals(SubID)){
                Boolean aa=tAtt.getAccount();
                if(aa==null)aa=false;
                if(aa==true) throw new Exception("已结算的记录不能再次引用!");

                techAttRep.save(tAtt);

                String techFile=sub.getTechFiles();
                List<String>TechArray=new ArrayList<>();
                if(StringUtils.isEmpty(techFile)==false){
                    TechArray= Arrays.stream(techFile.split(",")).collect(Collectors.toList());
                }
                if(TechArray.contains(tAtt.getAttId())==false)TechArray.add(tAtt.getAttId());
                sub.setTechFiles(StringUtils.join(TechArray,","));
                casesSubRep.save(sub);
                AddChangeRecord(tAtt.getCasesId(),
                        sub.getSubNo()+"的技术交底文件文件:【"+file.getName()+"】被"+Info.getUserName()+
                                "选择","选择交底文件",Info);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void UnCheckFile(String SubID, String AttID) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        Optional<techSupportAttachment> findAtts=techAttRep.findFirstByAttId(AttID);
        Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
        Optional<casesSub> findSubs=casesSubRep.findFirstBySubId(SubID);
        if(findAtts.isPresent() && findFiles.isPresent() && findSubs.isPresent()){
            techSupportAttachment tAtt= findAtts.get();
            tbAttachment file=findFiles.get();
            casesSub sub= findSubs.get();
            if(tAtt.getRefId().equals(SubID)){
                Boolean aa=tAtt.getAccount();
                if(aa==null)aa=false;
                if(aa==true) throw new Exception("已结算的记录不能取消!");
                tAtt.setRefId(null);
                tAtt.setTechManName(null);
                tAtt.setRefMan(null);
                tAtt.setRefTime(null);
                tAtt.setClientName(null);
                tAtt.setClientId(null);

                techAttRep.save(tAtt);

                String techFile=sub.getTechFiles();
                if(StringUtils.isEmpty(techFile)==false){
                    List<String>TechArray= Arrays.stream(techFile.split(",")).collect(Collectors.toList());
                    TechArray.remove(tAtt.getAttId());
                    sub.setTechFiles(StringUtils.join(TechArray,","));
                    casesSubRep.save(sub);
                }
                AddChangeRecord(tAtt.getCasesId(), sub.getSubNo()+"的技术交底文件文件:【"+file.getName()+"】被"+Info.getUserName()+ "取消选择","取消选择",Info);
            }
        }
    }
    private void AddChangeRecord(String CasesID,String ChangeText,String Mode,LoginUserInfo Info){
        techSupportChangeRecord newRecord=new techSupportChangeRecord();
        newRecord.setCasesId(CasesID);
        newRecord.setCreateTime(new Date());
        newRecord.setChangeText(ChangeText);
        newRecord.setMode(Mode);
        newRecord.setUserId(Info.getUserIdValue());
        changeRep.save(newRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @MethodWatch(name = "删除了专利挖申请",des = "CasesID为{CasesID}")
    public void DeleteSupport(String CasesID) throws Exception{
        LoginUserInfo Info=CompanyContext.get();

        Optional<techSupportMain> findMains=mainRep.findFirstByCasesId(CasesID);
        if(findMains.isPresent()){
            techSupportMain main=findMains.get();
            Integer State=main.getState();
            if(State==4) throw new Exception("已接单业务不允许删除!");
            int UserID=main.getCreateMan();
            String RoleName=Info.getRoleName();
            if(RoleName.equals("系统管理员")==false&&RoleName.indexOf("流程")==-1 && RoleName.indexOf("财务")==-1){
                if(Info.getUserIdValue().equals(UserID)==false){
                    throw new Exception("只有发起人员才能删除当前专利挖掘记录!");
                }
            }

        } else throw new Exception("删除的信息已不存在!");
        List<techSupportAttachment> files=techAttRep.findAllByCasesId(CasesID);
        List<techSupportAttachment> findFiles=files.stream().filter(f->Strings.isEmpty(f.getRefId())==false).collect(Collectors.toList());
        if(findFiles.size()>0) throw new Exception("有"+Integer.toString(findFiles.size())+"个文件被引用，无法删除!");

        userRep.deleteAllByCasesId(CasesID);
        changeRep.deleteAllByCasesId(CasesID);
        subRep.deleteAllByCasesId(CasesID);
        techAttRep.deleteAllByCasesId(CasesID);
        mainRep.deleteAllByCasesId(CasesID);
    }
}
