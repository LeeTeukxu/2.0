package com.zhide.dtsystem.controllers.casesHigh;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.ICaseHighAllBrowseService;
import com.zhide.dtsystem.services.instance.casesStateNewCounter;
import com.zhide.dtsystem.viewModel.HighBillObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName: CasesHighAllController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月18日 16:14
 **/
@RequestMapping("/caseHighAll")
@Controller
public class CaseHighAllController {
    @Autowired
    casesStateNewCounter casesCounter;
    @Autowired
    ICaseHighAllBrowseService allService;
    Logger logger = LoggerFactory.getLogger(CaseHighAllController.class);

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/caseHigh/all";
    }

    @RequestMapping("/total")
    public String Total(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, String> X = casesCounter.getResult(UserID, loginInfo.getRoleName());
        model.put("States", X);
        logger.info("X:" + JSON.toJSONString(X));
        return "work/caseHigh/total";
    }
    @ResponseBody
    @RequestMapping("/getBillObject")
    public successResult getBillObject(String CasesID){
        successResult result=new successResult();
        try
        {
            HighBillObject Obj= allService.getBillObject(CasesID);
            result.setData(Obj);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
