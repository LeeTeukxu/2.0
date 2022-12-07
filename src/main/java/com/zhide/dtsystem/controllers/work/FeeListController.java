package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.feeListName;
import com.zhide.dtsystem.services.define.IFeeListService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/work/feeList")
public class FeeListController {

    @Autowired
    IFeeListService feeListService;
    org.slf4j.Logger logger = LoggerFactory.getLogger(FeeListController.class);

    @RequestMapping("/index")
    public String Index() {
        return "/work/feeList/index";
    }

    @PostMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeListService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @PostMapping("/getFeeItemData")
    @ResponseBody
    public pageObject getFeeItemData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeListService.getFeeItemData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(String Data) {
        successResult result = new successResult();
        try {
            feeListName feeListName = JSON.parseObject(Data, com.zhide.dtsystem.models.feeListName.class);
            result.setSuccess(feeListService.Save(feeListName));
        } catch (Exception e) {
            result.raiseException(e);
        }
        return result;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public successResult Remove(String ID) {
        successResult result = new successResult();
        try {
            result.setSuccess(feeListService.Remove(ID));
        } catch (Exception e) {
            result.raiseException(e);
        }
        return result;
    }

    @RequestMapping("/alreadyPay")
    @ResponseBody
    public successResult AlreadyPay(String ID) {
        successResult result = new successResult();
        try {
            result.setSuccess(feeListService.AlreadyPay(ID));
        } catch (Exception e) {
            result.raiseException(e);
        }
        return result;
    }
}
