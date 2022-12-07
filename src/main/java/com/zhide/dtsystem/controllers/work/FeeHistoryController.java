package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IFeeHistoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/watch/feeHistory")
public class FeeHistoryController {

    @Autowired
    IFeeHistoryListService feeHistory;

    @RequestMapping("/index")
    public String Index() {
        return "/work/feeHistory/index";
    }

    @RequestMapping("/list")
    public String List() {
        return "work/feeHistory/list";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeHistory.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
