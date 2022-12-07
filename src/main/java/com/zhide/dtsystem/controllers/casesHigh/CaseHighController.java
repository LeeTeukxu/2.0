package com.zhide.dtsystem.controllers.casesHigh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.caseHighAttachmentRepository;
import com.zhide.dtsystem.repositorys.caseHighMainRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbFormDesignRepository;
import com.zhide.dtsystem.services.define.ICaseHighAllBrowseService;
import com.zhide.dtsystem.services.define.ICaseHighSubService;
import com.zhide.dtsystem.services.instance.caseHighStateCounter;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CaseHighController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月18日 19:54
 **/
@RequestMapping("/caseHigh")
@Controller
public class CaseHighController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    ICaseHighAllBrowseService highBrowseService;
    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    caseHighAttachmentRepository highAttRep;
    @Autowired
    ICaseHighSubService caseHighSubService;
    @Autowired
    caseHighStateCounter counterService;

    @Autowired
    SysLoginUserMapper loginUserMapper;
    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/caseHigh/index";
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCaseHigh"))
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

        model.put("ViewDetail",1);
        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        return "/work/caseHigh/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCaseHigh"))
                .findFirst();
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ViewDetail",1);
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<caseHighMain> findOne = highMainRep.findById(ID);
            if (findOne.isPresent()) {
                caseHighMain main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                List<caseHighSub> Subs= caseHighSubService.getSubRecords(main.getCasesId());
                if(Subs.size()>0){
                    Double AllMoney=Subs.stream().mapToDouble(f->f.getDaiMoney()+f.getSjfMoney()).sum();
                    main.setAllMoney(AllMoney);
                }
                if (main.getAllMoneySet() == true) {
                    main.setDMoney(main.getAllMoney() - main.getChangeMoney());
                } else main.setDMoney(0.0);

                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS = highAttRep.findAllByCasesId(main.getCasesId())
                        .stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));
                if(Mode.equals("Browse")){
                    String RoleName=loginInfo.getRoleName();
                    if(RoleName.indexOf("技术")>-1 || RoleName.indexOf("项目")>-1){
                        if(main.getCreateMan()!=loginInfo.getUserIdValue()){
                            model.replace("ViewDetail",0);
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
        return "/work/caseHigh/add";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = highBrowseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> Datas = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            caseHighMain obj = highBrowseService.SaveAll(Datas);
            result.setData(obj);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/createAJRecord")
    @ResponseBody
    public successResult createAJRecord(String Data) {
        successResult result = new successResult();
        try {
            highCreateInfo info = JSON.parseObject(Data, highCreateInfo.class);
            caseHighMain main = caseHighSubService.AddAJRecord(info);
            result.setData(main);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getSubRecords")
    public pageObject getSubRecords(String casesId) {
        pageObject object = new pageObject();
        try {
            List<caseHighSub> subs = caseHighSubService.getSubRecords(casesId);
            object.setData(subs);
            object.setTotal(subs.size());
        } catch (Exception ax) {
            object.raiseException(ax);
        }
        return object;
    }

    @ResponseBody
    @RequestMapping("/removeAjDetail")
    public successResult RemoveAjDetail(String AJID) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.stream(AJID.split(",")).collect(Collectors.toList());
            caseHighMain main = caseHighSubService.RemoveSubs(IDS);
            result.setData(main);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveSubs")
    public successResult SaveSubs(String Data) {
        successResult result = new successResult();
        try {
            List<caseHighSub> items = JSON.parseArray(Data, caseHighSub.class);
            caseHighMain main = caseHighSubService.SaveSubs(items);
            result.setData(main);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeAllMoney")
    public successResult ChangeAllMoney(String CasesID, Double changeMoney) {
        successResult result = new successResult();
        try {
            highBrowseService.ChangeAllMoney(CasesID, changeMoney);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/getStateNumbers")
    public successResult getStateNumbers() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            Integer UserID = Integer.parseInt(Info.getUserId());
            String RoleName = Info.getRoleName();
            Map<String, String> OK = counterService.getResult(UserID, RoleName);
            result.setData(OK);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/commit")
    public successResult Commit(int ID, String Result, String ResultText) {
        successResult result = new successResult();
        try {
            highBrowseService.Commit(ID, Result, ResultText);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getSubFiles")
    public successResult GetSubFiles(String SubID, String Type) {
        successResult result = new successResult();
        try {
            List<String> Fs = caseHighSubService.getSubFiles(SubID, Type);
            result.setData(Fs);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveSubFiles")
    public successResult SaveSubFiles(String CasesID, String SubID, String AttID, String Type) {
        successResult result = new successResult();
        try {
            caseHighSubService.SaveSubFiles(CasesID, SubID, AttID, Type);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeSubFiles")
    public successResult RemoveSubFiles(String CasesID, String AttID) {
        successResult result = new successResult();
        try {
            caseHighSubService.RemoveSubFiles(CasesID, AttID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveMainMemo")
    @ResponseBody
    public successResult SaveMainMemo(String CasesID, String Data) {
        successResult result = new successResult();
        try {
            boolean OK = highBrowseService.SaveMainMemo(CasesID, Data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveSubMemo")
    @ResponseBody
    public successResult SaveSubMemo(String SubID, String Data) {
        successResult result = new successResult();
        try {
            boolean OK = highBrowseService.SaveSubMemo(SubID, Data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/removeCases")
    public successResult Remove(String CasesID) {
        successResult result = new successResult();
        try {
            highBrowseService.RemoveAll(CasesID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/saveSelectMoney")
    public successResult SaveSelectMoney(String Data){
        successResult result=new successResult();
        try
        {
            SelectMoneyInfo Info=JSON.parseObject(Data,SelectMoneyInfo.class);
            highBrowseService.SaveSelectMoney(Info);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/reAudit")
    public successResult ReAudit(String CasesID){
        successResult result=new successResult();
        try{
            highBrowseService.ReAudit(CasesID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
