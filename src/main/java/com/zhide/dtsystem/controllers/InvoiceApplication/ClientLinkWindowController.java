package com.zhide.dtsystem.controllers.InvoiceApplication;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IClinentLinkWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/InvoiceApplication/ClientLinkWindow")
public class ClientLinkWindowController {
    @Autowired
    IClinentLinkWindowService clinentLinkWindowService;

    @RequestMapping("index")
    public String Index(String customer, Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (customer != null) {
            if (customer.startsWith("%")) {
                try {
                    customer = URLDecoder.decode(customer, "utf-8");
                } catch (Exception ax) {
                    ax.printStackTrace();
                }
            }
        }
        if (customer != null) {
            model.put("customer", customer);
        } else {
            model.put("customer", "");
        }
        model.put("UserID", loginUserInfo.getUserId());
        model.put("UserName", loginUserInfo.getUserName());
        model.put("RoleID", loginUserInfo.getRoleId());
        model.put("RoleName", loginUserInfo.getRoleName());
        model.put("DepID", loginUserInfo.getDepId());
        return "/InvoiceApplication/ClientLinkWindow/index";
    }

    @RequestMapping("/getDataWindow")
    @ResponseBody
    public pageObject getDataWindow(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clinentLinkWindowService.getDataWindow(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
