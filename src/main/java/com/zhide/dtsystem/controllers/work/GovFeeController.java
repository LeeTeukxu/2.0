package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IGovFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName: GovFeeController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月07日 22:49
 **/
@Controller
@RequestMapping("/work/govFee")
public class GovFeeController {
    @Autowired
    IGovFeeService govService;

    @RequestMapping("/wait")
    public String Wait(Map<String,Object> model) {
        LoginUserInfo Info= CompanyContext.get();
        model.put("RoleName",Info.getRoleName());
        return "/work/govFee/wait";
    }

    @RequestMapping("/payFor")
    public String PayFor(Map<String,Object>model) {

        LoginUserInfo Info= CompanyContext.get();
        model.put("RoleName",Info.getRoleName());
        return "/work/govFee/payFor";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GeData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = govService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
