package com.zhide.dtsystem.controllers.CustomerRefund;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IArrivalWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/CustomerRefund/ArrivalWindow")
public class ArrivalWindowController {

    @Autowired
    IArrivalWindowService arrivalWindowService;

    @RequestMapping("index")
    public String Index(Map<String, Object> model) {
        return "/CustomerRefund/ArrivalWindow/index";
    }

    @RequestMapping("/getDataWindow")
    @ResponseBody
    public pageObject getDataWindow(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = arrivalWindowService.getDataWindow(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
