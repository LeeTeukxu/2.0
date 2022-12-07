package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.SimplePageName;
import com.zhide.dtsystem.services.define.IFeeItemService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work/feeItem")
@SimplePageName(name = "FeeItem")
public class FeeItemController {

    org.slf4j.Logger logger = LoggerFactory.getLogger(FeeItemController.class);
    @Autowired
    IFeeItemService feeItemService;

    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        Date dNow = new Date();
        Date yNow = DateUtils.addDays(dNow, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.put("now", simpleDateFormat.format(dNow));
        model.put("next", simpleDateFormat.format(yNow));
        model.put("feeItems", JSON.toJSONString(feeItemService.getFeeItems()));
        model.put("zlItems", JSON.toJSONString(feeItemService.getZLItems()));
        return "/work/feeItem/index";
    }

    @PostMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeItemService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/addToFeeList")
    @ResponseBody
    public successResult addToFeeList(String IDS, String JFQD) {
        successResult result = new successResult();
        try {
            List<Integer> IDD = JSON.parseArray(IDS, Integer.class);
            feeItemService.AddToFeeList(IDD, JFQD);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changePayMark")
    @ResponseBody
    public successResult changePayMark(String IDS, int PayState) {
        successResult result = new successResult();
        try {
            List<Integer> IDD = JSON.parseArray(IDS, Integer.class);
            feeItemService.ChangePayMark(IDD, PayState);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeJiaoDuiMoney")
    @ResponseBody
    public successResult changeJiaoDuiMoney(String IDS, String Money) {
        successResult result = new successResult();
        try {
            List<Integer> IDD = JSON.parseArray(IDS, Integer.class);
            feeItemService.ChangeJiaoDuiMoney(IDD, Money);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
