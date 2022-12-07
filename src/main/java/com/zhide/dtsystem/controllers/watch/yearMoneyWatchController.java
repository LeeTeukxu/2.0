package com.zhide.dtsystem.controllers.watch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.define.IYearWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//@Controller
//@RequestMapping("/watch/yearWatch")
public class yearMoneyWatchController {

    @Autowired
    IYearWatchService yearWatchService;

    @RequestMapping("/index")
    public String Index() {
        return "/work/yearWatch/index";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Datas = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            result.setSuccess(yearWatchService.saveAll(Datas));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = yearWatchService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeAll")
    public successResult removeAll(String IDS) {
        successResult result = new successResult();
        try {
            List<String> IID = JSON.parseArray(IDS, String.class);
            yearWatchService.removeAll(IID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
