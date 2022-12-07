package com.zhide.dtsystem.controllers.watch;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IYearWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/watch/addYearWatchItem")
public class addYearWatchItemController {

    @Autowired
    IYearWatchService yearWatchService;

    @RequestMapping("/index")
    public String Index() {
        return "/work/yearWatch/addWatchItem";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = yearWatchService.getAddYearWatchItem(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllImages")
    public Map<String, Object> getAllImages(String tongzhisbh) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<String> IDS = Arrays.asList(tongzhisbh.split(","));
            List<Map<String, Object>> OO = new ArrayList<>();
            for (int i = 0; i < IDS.size(); i++) {
                String ID = IDS.get(i);
                List<String> Files = yearWatchService.getTZSPaths(ID);
                if (Files.size() > 0) {
                    res.put("status", 1);
                    res.put("start", 0);
                    for (int n = 0; n < Files.size(); n++) {
                        String Src = Files.get(n);
                        Map<String, Object> OX = new HashMap<>();
                        OX.put("src", Src);
                        OX.put("thumb", "");
                        OX.put("alt", "第" + Integer.toString(n + 1) + "个文件");
                        OO.add(OX);
                    }
                }
            }
            res.put("data", OO);
            if (OO.size() == 0) throw new Exception("没有可查看的通知书附件。");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }
}
