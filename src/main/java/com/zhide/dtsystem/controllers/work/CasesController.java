package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.CasesMemoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.instance.casesStateCounter;
import com.zhide.dtsystem.services.instance.filesStateCounter;
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
@RequestMapping("/work/cases")
public class CasesController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ICaseService caseService;
    @Autowired
    ICasesBrowseService browseService;
    @Autowired
    casesRepository casesRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAjDetailRepository casesAjRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    IAllCasesStateService allCaseService;
    @Autowired
    casesStateCounter casesCounter;
    @Autowired
    filesStateCounter filesCounter;
    @Autowired
    casesStateCounter casesCouter;
    @Autowired
    CasesMemoMapper casesMemoMapper;
    @Autowired
    ICasesMemoService memoService;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    casesAjAttachmentRepository casesAjAttRep;

    @Autowired
    ICasesAJDetailService ajDetailService;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {

        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/case/index";
    }

    @RequestMapping("/total")
    public String Total(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = casesCounter.getResult(UserID, loginInfo.getRoleName());
        Map<String, Integer> Y = filesCounter.getResult(UserID, loginInfo.getRoleName());
        for (String Key : Y.keySet()) {
            X.put(Key, Y.get(Key));
        }
        model.put("States", X);
        return "work/case/total";
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
        return "/work/case/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<cases> findOne = casesRep.findById(ID);
            if (findOne.isPresent()) {
                cases main = findOne.get();
                Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS =
                        casesAttRep.findAllByCasesId(main.getCasesid()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));
            }
        } else model.put("config", "[]");
        model.put("Mode", Mode);
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        return "/work/case/add";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> Datas = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            cases obj = caseService.SaveAll(Datas);
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

    @ResponseBody
    @RequestMapping("/getAllByCasesId")
    public List<casesYwItems> getAllByCasesId(String CasesID) {
        if (StringUtils.isEmpty(CasesID) == false) {
            return casesYwRep.findAllByCasesId(CasesID);
        } else return new ArrayList<>();
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<casesChangeRecord> getChangeRecord(String CasesID) {
        return casesChangeRep.findAllByCasesIdOrderByCreateTimeDesc(CasesID);
    }

    @ResponseBody
    @RequestMapping("/getAjbyCasesId")
    public List<casesAjDetail> getAJbyCasesId(String CasesID) {
        if (StringUtils.isEmpty(CasesID) == false) {
            return casesAjRep.findAllByCasesId(CasesID);
        } else return new ArrayList<>();
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
            Map<String, Integer> OK = casesCounter.getResult(UserID, RoleName);
            Map<String, Integer> Y = filesCounter.getResult(UserID, RoleName);
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
        return "/work/case/addMemo";
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

    @ResponseBody
    @RequestMapping("/removeAjAttachment")
    public successResult removeAjAttachment(String AJID, String AttID) {
        successResult result = new successResult();
        try {
            ajDetailService.RemoveAjAttachment(AJID, AttID);
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
}
