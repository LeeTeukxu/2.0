package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.ICasesAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/work/casesAll")
public class CasesAllController {
    @Autowired
    ICasesAllService allService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        return "/work/case/all";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = allService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
