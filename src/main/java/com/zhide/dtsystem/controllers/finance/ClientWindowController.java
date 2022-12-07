package com.zhide.dtsystem.controllers.finance;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IClientWindowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/finance/ClientWindow")
public class ClientWindowController {

    Logger logger = LoggerFactory.getLogger(ClientWindowController.class);

    @Autowired
    IClientWindowService clientWindowService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        return "/finance/ClientWindow/index";
    }

    @RequestMapping("/getDataWindow")
    @ResponseBody
    public pageObject getDataWindow(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientWindowService.getDataWindow(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
