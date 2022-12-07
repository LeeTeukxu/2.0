package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.cases;
import com.zhide.dtsystem.models.tbFormDesign;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IAllCasesStateService;
import com.zhide.dtsystem.services.define.ICaseService;
import com.zhide.dtsystem.services.define.ICasesCommitBrowseService;
import com.zhide.dtsystem.services.instance.casesStateCounter;
import com.zhide.dtsystem.services.instance.filesStateCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/casesCommit")
public class CasesCommitController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ICaseService caseService;
    @Autowired
    ICasesCommitBrowseService commitService;
    @Autowired
    casesRepository casesRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    IAllCasesStateService allCaseService;
    @Autowired
    casesStateCounter casesCouter;
    @Autowired
    filesStateCounter filesCounter;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = casesCouter.getResult(UserID, loginInfo.getRoleName());
        Map<String, Integer> Y = filesCounter.getResult(UserID, loginInfo.getRoleName());
        for (String Key : Y.keySet()) {
            X.put(Key, Y.get(Key));
        }
        model.put("States", X);

        if (State == null) State = 1;
        model.put("State", State);
        return "/work/case/commit";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        String Mode = "Browse";
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<cases> findOne = casesRep.findById(ID);
            if (findOne.isPresent()) {
                cases main = findOne.get();
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
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = commitService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

}
