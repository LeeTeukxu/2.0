package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbFormDesign;
import com.zhide.dtsystem.models.tradeCases;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ITradeCaseService;
import com.zhide.dtsystem.services.define.ITradeCasesCommitBrowseService;
import com.zhide.dtsystem.services.instance.tradeCasesFilesStateCounter;
import com.zhide.dtsystem.services.instance.tradeCasesStateCounter;
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
@RequestMapping("/work/tradeCasesCommit")
public class TradeCasesCommitController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ITradeCaseService tradeCaseService;
    @Autowired
    ITradeCasesCommitBrowseService tradeCasesCommitBrowseService;
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

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = tradeCasesStateCounter.getResult(UserID, loginInfo.getRoleName());
        Map<String, Integer> Y = tradeCasesFilesStateCounter.getResult(UserID, loginInfo.getRoleName());
        for (String Key : Y.keySet()) {
            X.put(Key, Y.get(Key));
        }
        model.put("States", X);

        if (State == null) State = 1;
        model.put("State", State);
        return "/work/tradeCases/commit";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("PayCase"))
                .findFirst();
        String Mode = "Browse";
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<tradeCases> findOne = tradeCasesRepository.findById(ID);
            if (findOne.isPresent()) {
                tradeCases main = findOne.get();
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
        return "/work/tradeCases/add";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tradeCasesCommitBrowseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

}
