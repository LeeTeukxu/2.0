package com.zhide.dtsystem.controllers.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.CasesMemoMapper;
import com.zhide.dtsystem.mapper.CasesNewBrowseMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.instance.casesStateNewCounter;
import com.zhide.dtsystem.services.instance.filesStateCounter;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/cases")
public class CasesNewController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ICaseNewService caseService;
    @Autowired
    ICasesBrowseNewService browseService;
    @Autowired
    casesMainRepository casesRep;
    @Autowired
    casesAjDetailRepository casesAjRep;
    @Autowired
    casesNewAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    IAllCasesStateService allCaseService;
    @Autowired
    casesStateNewCounter casesCounter;
    @Autowired
    filesStateCounter filesCounter;
    @Autowired
    CasesMemoMapper casesMemoMapper;
    @Autowired
    ICasesMemoService memoService;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    casesAjAttachmentRepository casesAjAttRep;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    ICasesAJDetailNewService ajDetailService;

    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    CasesNewBrowseMapper casesNewBrowseMapper;
    Logger logger = LoggerFactory.getLogger(CasesNewController.class);

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {

        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/caseNew/index";
    }

    @RequestMapping("/queryTechRecord")
    public String QueryTechRecord() {
        return "/work/caseNew/queryTechRecord";
    }

    @RequestMapping("/total")
    public String Total(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, String> X = casesCounter.getResult(UserID, loginInfo.getRoleName());
        model.put("States", X);
        model.put("RoleName",loginInfo.getRoleName());
        logger.info("X:" + JSON.toJSONString(X));
        return "work/caseNew/total";
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
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
        model.put("ViewDetail",1);
        return "/work/caseNew/add";
    }

    @RequestMapping("/editOne")
    public String EditOne(String ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<casesMain> findOne = casesRep.findFirstByCasesId(ID);
            if (findOne.isPresent()) {
                casesMain main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                Double totalMoney = main.getAllMoney();
                Double xTotalMoney = subRep.findAllByCasesId(main.getCasesId())
                        .stream().mapToDouble(f -> f.getDaiMoney() + f.getGuanMoney()).sum();
                if (main.isAllMoneySet() == false) {
                    main.setAllMoney(xTotalMoney);
                    main.setDMoney(0.0);
                } else {
                    main.setDMoney(totalMoney - xTotalMoney);
                }
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS = casesAttRep.findAllByCasesId(main.getCasesId())
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
        model.put("ViewDetail",1);
        return "/work/caseNew/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ViewDetail",1);
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<casesMain> findOne = casesRep.findById(ID);
            if (findOne.isPresent()) {
                casesMain main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                Double totalMoney = main.getAllMoney();
                Double xTotalMoney = subRep.findAllByCasesId(main.getCasesId())
                        .stream().mapToDouble(f -> f.getDaiMoney() + f.getGuanMoney()).sum();
                if (main.isAllMoneySet() == false) {
                    main.setAllMoney(xTotalMoney);
                    main.setDMoney(0.0);
                } else {
                    main.setDMoney(totalMoney - xTotalMoney);
                }
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS = casesAttRep.findAllByCasesId(main.getCasesId())
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
        return "/work/caseNew/add";
    }

    @ResponseBody
    @RequestMapping("/getProducts")
    public List<ProductNode> getProducts() {
        List<ProductNode> result = new ArrayList<>();
        List<productItemType> items = productRep.findAll();
        List<String> Names = items.stream().map(f -> f.getType()).distinct().collect(Collectors.toList());
        int Num = 1;
        for (int i = 0; i < Names.size(); i++) {
            String Name = Names.get(i);
            ProductNode root = new ProductNode();
            root.setText(Name);
            root.setId(Integer.toString(Num));
            root.setPid("0");
            result.add(root);
            List<productItemType> childs =
                    items.stream().filter(f -> f.getType().equals(Name)).collect(Collectors.toList());
            for (int n = 0; n < childs.size(); n++) {
                productItemType item = childs.get(n);
                ProductNode child = new ProductNode();
                child.setPid(root.getId());
                child.setId(item.getFid());
                child.setText(item.getName().trim());
                child.setName(item.getType());
                child.setPrice(item.getPrice());
                child.setMaxDays(item.getMaxDays());
                child.setCost(item.getCost());
                child.setRequired(item.getRequired());
                result.add(child);
            }
            Num++;
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
            casesMain obj = caseService.SaveAll(Datas);
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
            result = browseService.getData(request);
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
            ajCreateInfo info = JSON.parseObject(Data, ajCreateInfo.class);
            caseService.createAJRecord(info);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getSubRecords")
    @ResponseBody
    public pageObject getSubRecords(String casesId) {
        pageObject results = new pageObject();
        try {
            List<casesSub> subs = subRep.findAllByCasesId(casesId);
            int Num = subs.size();
            for (int i = 0; i < Num; i++) {
                casesSub sub = subs.get(i);
                Double Guan = sub.getGuanMoney();
                Double Dai = sub.getDaiMoney();
                Integer xNum = sub.getNum();
                Double Total = Dai * xNum + Guan;
                sub.setTotalMoney(Total);
            }
            results.setData(subs);
            results.setTotal(subs.size());
        } catch (Exception ax) {
            results.raiseException(ax);
        }
        return results;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<casesChangeRecord> getChangeRecord(String CasesID) {
        return casesChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
    }

    @ResponseBody
    @RequestMapping("/commit")
    public successResult Commit(int ID, String Result, String ResultText) {
        successResult result = new successResult();
        try {
            caseService.Commit(ID, Result, ResultText);
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
            caseService.Remove(CasesID);
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
            Map<String, String> OK = casesCounter.getResult(UserID, RoleName);
            result.setData(OK);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/addMemo")
    public String AddMemo(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        String[] IDS=CasesID.split(",");
        model.put("BatchMode",IDS.length>1);
        return "/work/caseNew/addMemo";
    }

    @RequestMapping("/getMemoData")
    @ResponseBody
    public List<Map<String, Object>> getMemoData(String CasesID) {
        return casesMemoMapper.getData(CasesID);
    }

    @RequestMapping("/saveMemo")
    @ResponseBody
    public successResult SaveMemo(String Data) {
        successResult result = new successResult();
        try {
            List<casesMemo> items = JSON.parseArray(Data, casesMemo.class);
            memoService.SaveAll(items);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveSubs")
    @ResponseBody
    public successResult SaveSubs(String Data) {
        successResult result = new successResult();
        try {
            List<casesSub> items = JSON.parseArray(Data, casesSub.class);
            ajDetailService.SaveSubs(items);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeMemo")
    public successResult RemoveMemo(String CasesID, Integer ID) {
        successResult result = new successResult();
        try {
            boolean X = memoService.Remove(CasesID, ID);
            result.setSuccess(X);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeAjDetail")
    public successResult RemoveAjDetail(String AJID) {
        successResult result = new successResult();
        try {
            ajDetailService.Remove(AJID);
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
            List<String> Fs = ajDetailService.getSubFiles(SubID, Type);
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
            ajDetailService.SaveSubFiles(CasesID, SubID, AttID, Type);
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
            ajDetailService.RemoveSubFiles(CasesID, AttID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeAllMoney")
    public successResult ChangeAllMoney(String CasesID, Double allMoney) {
        successResult result = new successResult();
        try {
            Optional<casesMain> findMains = casesRep.findFirstByCasesId(CasesID);
            if (findMains.isPresent()) {
                casesMain main = findMains.get();
                main.setAllMoney(allMoney);
                main.setAllMoneySet(true);
                casesRep.save(main);
            } else throw new Exception(CasesID + "不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/completeOne")
    public successResult Complete(String CasesID) {
        successResult result = new successResult();
        try {
            caseService.Complete(CasesID, 8);

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/unCompleteOne")
    public successResult UnComplete(String CasesID) {
        successResult result = new successResult();
        try {
            caseService.Complete(CasesID, 7);

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
            caseService.SaveSelectMoney(Info);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/getSelectMoney")
    public List<Map<String,Object>>getSelectMoney(String CasesID){
        List<Map<String,Object>> Datas= casesNewBrowseMapper.getSelectMoney(CasesID);
        if(Datas.size()>0) {
            Map<String, Object> Data = Datas.get(0);
            String DocSN = Data.get("DocSN").toString();
            List<Map<String, Object>> Os = casesNewBrowseMapper.getRefundMoney(DocSN);
            for (int i = 0; i < Os.size(); i++) {
                Map<String, Object> O = Os.get(i);
                Datas.add(O);
            }
        }
        return Datas;
    }
    @ResponseBody
    @PostMapping("/reAudit")
    public successResult ReAudit(String CasesID){
        successResult result=new successResult();
        try{
            caseService.ReAudit(CasesID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
