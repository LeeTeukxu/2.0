package com.zhide.dtsystem.controllers.InvoiceApplication;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IContractWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/InvoiceApplication/ContractWindow")
public class ContractWindowController {
    @Autowired
    IContractWindowService contractWindowService;

    @RequestMapping("index")
    public String Index(String customer, Map<String, Object> model) {
        if (customer.startsWith("%")) {
            try {
                customer = URLDecoder.decode(customer, "utf-8");
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
        model.put("customer", customer);
        return "/InvoiceApplication/ContractWindow/index";
    }

    @RequestMapping("/getDataWindow")
    @ResponseBody
    public pageObject getDataWindow(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = contractWindowService.getDataWindow(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
