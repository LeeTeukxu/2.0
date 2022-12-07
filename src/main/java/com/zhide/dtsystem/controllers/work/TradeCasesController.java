package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.CasesMemoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesMemoService;
import com.zhide.dtsystem.services.define.ICasesYwDetailNewService;
import com.zhide.dtsystem.services.define.ITradeCaseService;
import com.zhide.dtsystem.services.define.ITradeCasesBrowseService;
import com.zhide.dtsystem.services.instance.tradeCasesFilesStateCounter;
import com.zhide.dtsystem.services.instance.tradeCasesStateCounter;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/work/tradeCases")
public class TradeCasesController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ITradeCaseService tradeCaseService;
    @Autowired
    ITradeCasesBrowseService tradeCasesBrowseService;
    @Autowired
    tradeCasesRepository tradeCasesRepository;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    tradeCasesStateCounter tradeCasesStateCounter;
    @Autowired
    tradeCasesFilesStateCounter tradeCasesFilesStateCounter;
    @Autowired
    CasesMemoMapper casesMemoMapper;
    @Autowired
    ICasesMemoService memoService;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    ICasesYwDetailNewService ywDetailNewService;
    @Autowired
    casesYwItemsRepository subRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    tbLoginUserRepository loginUserRepository;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/tradeCases/index";
    }

    @RequestMapping("/total")
    public String Total(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = tradeCasesStateCounter.getResult(UserID, loginInfo.getRoleName());
        Map<String, Integer> Y = tradeCasesFilesStateCounter.getResult(UserID, loginInfo.getRoleName());
        for (String Key : Y.keySet()) {
            X.put(Key, Y.get(Key));
        }
        model.put("States", X);
        return "/work/tradeCases/total";
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("TradeCase"))
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
        return "/work/tradeCases/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("TradeCase"))
                .findFirst();
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ViewDetail",1);
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<tradeCases> findOne = tradeCasesRepository.findById(ID);
            if (findOne.isPresent()) {
                tradeCases main = findOne.get();
                if (main.getClientId() != null) {
                    Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                    if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                }
                Double xTotalMoney = subRep.findAllByCasesId(main.getCasesid())
                        .stream().mapToDouble(f -> f.getDai() + f.getGuan()).sum();
                if (main.isAllMoneySet() == false) {
                    main.setAllMoney(xTotalMoney);
                }
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS =
                        casesAttRep.findAllByCasesId(main.getCasesid()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));
                if(Mode.equals("Browse")){
                    List<tbLoginUser> list = loginUserRepository.findAll();
                    String RoleName=loginInfo.getRoleName();
                    if (RoleName.indexOf("流程") > -1 || RoleName.indexOf("系统管理员") > -1 || RoleName.indexOf("财务") > -1 || main.getCreateMan() == loginInfo.getUserIdValue() || main.getAuditMan() == loginInfo.getUserIdValue()){
                        model.replace("ViewDetail",1);
                    }else if (main.getCreateManager() != null){
                        String[] createManagers = main.getCreateManager().split(",");
                        for (int i=0;i<createManagers.length;i++) {
                            if (model.get("ViewDetail").equals(0)) {
                                String createManager = createManagers[i];
                                list.stream().filter(id -> id.getUserId() == Integer.parseInt(createManager)).forEach(f -> {
                                    if (loginInfo.getDepId().equals(f.getDepId().toString())) {
                                        model.replace("ViewDetail", 1);
                                        return;
                                    } else model.replace("ViewDetail", 0);
                                });
                            }
                        }
                    }else if (main.getAuditManager() != null){
                        String[] auditManagers = main.getAuditManager().split(",");
                        for (int i=0;i<auditManagers.length;i++) {
                            if (model.get("ViewDetail").equals(0)) {
                                String auditManager = auditManagers[i];
                                list.stream().filter(ids -> ids.getUserId() == Integer.parseInt(auditManager)).forEach(f -> {
                                    if (loginInfo.getDepId().equals(f.getDepId().toString())) {
                                        model.replace("ViewDetail", 1);
                                        return;
                                    } else model.replace("ViewDetail", 0);
                                });
                            }
                        }
                    }
                    else model.replace("ViewDetail",0);

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
        return "/work/tradeCases/add";
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
            tradeCases obj = tradeCaseService.SaveAll(Datas);
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
            result = tradeCasesBrowseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllByCasesId")
    public List<casesYwItems> getAllByCasesId(String casesId) {
        if (StringUtils.isEmpty(casesId) == false) {
            return casesYwRep.findAllByCasesId(casesId);
        } else return new ArrayList<>();
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
            tradeCaseService.Commit(ID, Result, ResultText);
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
            tradeCaseService.Remove(CasesID);
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
            Map<String, Integer> OK = tradeCasesStateCounter.getResult(UserID, RoleName);
            Map<String, Integer> Y = tradeCasesFilesStateCounter.getResult(UserID, RoleName);
            for (String Key : Y.keySet()) {
                OK.put(Key, Y.get(Key));
            }
            result.setData(OK);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/addMemo")
    public String AddMemo(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        return "/work/tradeCases/addMemo";
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

    @RequestMapping("/createYwRecord")
    @ResponseBody
    public successResult createYwRecord(String Data) {
        successResult result = new successResult();
        try {
            ywCreateInfo info = JSON.parseObject(Data, ywCreateInfo.class);
            tradeCaseService.createYwRecord(info);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveYws")
    @ResponseBody
    public successResult SaveYws(String Data) {
        successResult result = new successResult();
        try {
            List<casesYwItems> items = JSON.parseArray(Data, casesYwItems.class);
            ywDetailNewService.SaveSubs(items);
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
            Optional<tradeCases> findMains = tradeCasesRepository.findFirstByCasesid(CasesID);
            if (findMains.isPresent()) {
                tradeCases main = findMains.get();
                main.setAllMoney(allMoney);
                main.setAllMoneySet(true);
                tradeCasesRepository.save(main);
            } else throw new Exception(CasesID + "不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/removeYwDetail")
    public successResult RemoveYwDetail(String YWID) {
        successResult result = new successResult();
        try {
            ywDetailNewService.Remove(YWID);
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
            List<String> Fs = ywDetailNewService.getSubFiles(SubID, Type);
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
            ywDetailNewService.SaveSubFiles(CasesID, SubID, AttID, Type);
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
            ywDetailNewService.SaveSelectMoney(Info);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
