package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.ITradeCasesCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/work/tradeCasesComplete")
@Controller
public class TradeCasesCompleteController {
    @Autowired
    ITradeCasesCompleteService tradeCasesCompleteService;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/tradeCases/complete";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tradeCasesCompleteService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/commit")
    @ResponseBody
    public successResult Commit(String CasesID, boolean complete) {
        successResult result = new successResult();
        try {
            tradeCasesCompleteService.Commit(CasesID, complete);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
