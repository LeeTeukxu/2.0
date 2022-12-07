package com.zhide.dtsystem.controllers.techSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.TechSupportMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ITechSupportService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: techSupportAllController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月17日 10:51
 **/
@RequestMapping("/techSupport")
@Controller
public class techSupportController {
    Logger logger = LoggerFactory.getLogger(techSupportController.class);
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ITechSupportService techService;
    @Autowired
    techSupportMainRepository mainRep;
    @Autowired
    techSupportSubRepository subRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    techSupportAttachmentRepository techAttRep;
    @Autowired
    techSupportChangeRecordRepository changeRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    TechSupportMapper techMapper;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    casesSubRepository casesSubRep;

    @RequestMapping("/total")
    public String Total(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, String> X = new HashMap<>();
        for (int i = 0; i < 100; i++) X.put(Integer.toString(i), "0");
        model.put("States", X);
        model.put("RoleName", loginInfo.getRoleName());
        logger.info("X:" + JSON.toJSONString(X));
        return "/work/techSupport/total";
    }

    @RequestMapping("/all")
    public String All(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/techSupport/all";
    }
    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/techSupport/index";
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("Tech"))
                .findFirst();
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            model.put("Load", "{}");
        } else model.put("config", "[]");
        model.put("Mode", "Add");
        model.put("AttID", "[]");
        SimpleDateFormat simmple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("Now", simmple.format(new Date()));

        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        model.put("ViewDetail", 1);
        return "/work/techSupport/add";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> Datas = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            techSupportMain obj = techService.SaveAll(Datas);
            List<techSupportAttachment> atts=techAttRep.findAllByCasesId(obj.getCasesId());
            for(int i=0;i<atts.size();i++){
                techSupportAttachment att=atts.get(i);
                if(StringUtils.isEmpty(att.getDocSn())){
                    att.setDocSn(feeItemMapper.getFlowCode("JDWJ"));
                    techAttRep.save(att);
                }
            }
            result.setData(obj);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {

            result = techService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getFiles")
    public pageObject getFiles(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {

            result = techService.getFiles(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/editOne")
    public String EditOne(String ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("Tech"))
                .findFirst();
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<techSupportMain> findOne = mainRep.findFirstByCasesId(ID);
            if (findOne.isPresent()) {
                techSupportMain main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS = techAttRep.findAllByCasesId(main.getCasesId())
                        .stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));
            }
        } else model.put("config", "[]");
        model.put("Mode", Mode);
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());

        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        model.put("ViewDetail", 1);
        return "/work/techSupport/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("Tech"))
                .findFirst();
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ViewDetail", 1);
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<techSupportMain> findOne = mainRep.findById(ID);
            if (findOne.isPresent()) {
                techSupportMain main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                if(main.getTechMan()!=null){
                    String TechMan=main.getTechMan();
                    List<String> TechMans= Arrays.stream(TechMan.split(",")).collect(Collectors.toList());
                    List<String> Names=new ArrayList<>();
                    for(int i=0;i< TechMans.size();i++){
                        String IID=TechMans.get(i);
                        String TechManName= loginUserMapper.getLoginUserNameById(Integer.parseInt(IID));
                        if(Names.contains(TechManName)==false)Names.add(TechManName);
                    }
                    main.setTechManName(StringUtils.join(Names,","));
                }

                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS = techAttRep.findAllByCasesId(main.getCasesId())
                        .stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));


                if (Mode.equals("Browse")) {
                    String RoleName = loginInfo.getRoleName();
                    if (RoleName.indexOf("技术") > -1 || RoleName.indexOf("项目") > -1) {
                        if (main.getCreateMan() != loginInfo.getUserIdValue()) {
                            model.replace("ViewDetail", 0);
                        }
                    }
                }
            }
        } else model.put("config", "[]");
        model.put("Mode", Mode);
        model.put("RoleName", loginInfo.getRoleName());
        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        return "/work/techSupport/add";
    }

    @ResponseBody
    @RequestMapping("/commit")
    public successResult Commit(int ID, String Result, String ResultText) {
        successResult result = new successResult();
        try {
            techService.Commit(ID, Result, ResultText);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getTechFiles")
    public successResult GetTechFiles(String CasesID) {
        successResult result = new successResult();
        try {
            List<String> Fs =
                    techAttRep.findAllByCasesId(CasesID).stream().map(f -> f.getAttId()).collect(Collectors.toList());
            result.setData(Fs);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveTechFile")
    public successResult SaveTechFile(String CasesID, String AttID) {
        successResult result = new successResult();
        LoginUserInfo Info=CompanyContext.get();
        try {
            Optional<techSupportAttachment> findAtts = techAttRep.findFirstByCasesIdAndAttId(CasesID, AttID);
            if (findAtts.isPresent() == false) {
                techSupportAttachment attachment = new techSupportAttachment();
                attachment.setCasesId(CasesID);
                attachment.setAttId(AttID);
                attachment.setCreateTime(new Date());
                attachment.setCreateMan(Info.getUserIdValue());
                attachment.setDocSn(feeItemMapper.getFlowCode("JDWJ"));
                techAttRep.save(attachment);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteTechFile")
    public successResult DeleteTechFile(String CasesID, String AttID,Integer Check) {
        successResult result = new successResult();
        LoginUserInfo Info=CompanyContext.get();
        try {
            tbAttachment file=null;
            techSupportAttachment att=null;
            Optional<techSupportAttachment> findAtts=techAttRep.findFirstByCasesIdAndAttId(CasesID,AttID);
            Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
            Integer  CreateMan=0;
            if(findAtts.isPresent()){
                att=findAtts.get();
                CreateMan=att.getCreateMan();
            }
            if(findFiles.isPresent()){
                file=findFiles.get();
                if(CreateMan==null) CreateMan=file.getUploadMan();
            } else{ file=new tbAttachment();file.setName("专利交底文件.pdf");}
            if(CreateMan==null){
              Optional<techSupportMain> mains= mainRep.findFirstByCasesId(att.getCasesId());
              if(mains.isPresent()){
                  CreateMan=mains.get().getAuditMan();
              }
            }
            String RoleName=Info.getRoleName();
            if(Check==1)if(CreateMan==null || CreateMan==0) throw new Exception("无法获取创建人!");
            if(Check==1) {
                if (RoleName.equals("系统管理员")==false) {
                    if(  RoleName.indexOf("财务") == -1 && RoleName.indexOf("流程") == -1){
                        if (CreateMan.equals(Info.getUserIdValue()) == false) throw new Exception("只能由上传人删除这个附件!");
                    }
                }
                if (StringUtils.isEmpty(att.getRefId()) == false) throw new Exception("准备删除的文件已被引用，无法删除!");
            }
            techService.DeleteTechFile(CasesID, AttID);
            AddChangeRecord(CasesID,att.getDocSn()+
                    "的交底文件文件:【"+file.getName()+"】被"+Info.getUserName()+ "删除","删除文件",Info);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getStateNumber")
    public successResult GetStateNumber() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OX = techMapper.getStateNumber(Info.getDepIdValue(), Info.getUserIdValue(),
                    Info.getRoleName());
            result.setData(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/doAccount")
    public successResult DoAccount(String Data) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Rows = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            LoginUserInfo Info = CompanyContext.get();
            for(int i=0;i<Rows.size();i++){
                Map<String,Object> Row=Rows.get(i);
                String CasesID=Row.get("CasesID").toString();
                String AttID=Row.get("AttID").toString();
                Optional<techSupportAttachment> findAtts=techAttRep.findFirstByCasesIdAndAttId(CasesID,AttID);
                Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
                if(findAtts.isPresent()){
                    techSupportAttachment att= findAtts.get();

                    tbAttachment file= findFiles.get();
                    Optional<casesSub> findSubs=casesSubRep.findFirstBySubId(att.getRefId());
                    casesSub sub= findSubs.get();

                    att.setAccount(true);
                    att.setAccountMan(Info.getUserIdValue());
                    att.setAccountTime(new Date());
                    techAttRep.save(att);

                    AddChangeRecord(CasesID,sub.getSubNo()+"的技术交底文件文件:【"+file.getName()+"】被"+Info.getUserName()+
                            "结算完成","结算完成",Info);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/doUnAccount")
    public successResult DoUnAccount(String Data) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Rows = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            LoginUserInfo Info = CompanyContext.get();
            for(int i=0;i<Rows.size();i++){
                Map<String,Object> Row=Rows.get(i);
                String CasesID=Row.get("CasesID").toString();
                String AttID=Row.get("AttID").toString();
                Optional<techSupportAttachment> findAtts=techAttRep.findFirstByCasesIdAndAttId(CasesID,AttID);
                Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
                if(findAtts.isPresent()){
                    techSupportAttachment att= findAtts.get();
                    tbAttachment file= findFiles.get();
                    Optional<casesSub> findSubs=casesSubRep.findFirstBySubId(att.getRefId());
                    casesSub sub= findSubs.get();
                    att.setAccount(false);
                    att.setAccountMan(null);
                    att.setAccountTime(null);
                    techAttRep.save(att);


                    AddChangeRecord(CasesID,sub.getSubNo()+"的技术交底文件文件:【"+file.getName()+"】被"+Info.getUserName()+
                            "取消结算","取消结算",Info);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getChangeRecord")
    @ResponseBody
    public pageObject getChangeRecord(String CasesID){
        pageObject object=new pageObject();
        try {
            List<techSupportChangeRecord> records= changeRep.getAllByCasesId(CasesID);
            object.setData(records);
            object.setTotal(records.size());
        }
        catch(Exception ax){
            object.raiseException(ax);
        }
        return object;
    }
    @RequestMapping("/unCheckFile")
    @ResponseBody
    public successResult UnCheckFile(String SubID,String AttID){
        successResult result=new successResult();
        try{
                techService.UnCheckFile(SubID,AttID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/checkFile")
    @ResponseBody
    public successResult CheckFile(String SubID,String AttID){
        successResult result=new successResult();
        try{
            techService.CheckFile(SubID,AttID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
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
    @RequestMapping("/deleteSupport")
    @ResponseBody
    public successResult deleteSupport(String CasesID){
        successResult result=new successResult();
        try {
            techService.DeleteSupport(CasesID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
