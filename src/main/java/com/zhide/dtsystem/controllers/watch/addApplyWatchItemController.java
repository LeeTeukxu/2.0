package com.zhide.dtsystem.controllers.watch;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IApplyWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
//@RequestMapping("/watch/addApplyWatchItem")
public class addApplyWatchItemController {

    @Autowired
    IApplyWatchService applyWatchService;

    @RequestMapping("/index")
    public String Index() {
        return "/work/applyWatch/addWatchItem";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = applyWatchService.getAddApplyWatchItem(request);
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
            List<String> Files = applyWatchService.getTZSPaths(tongzhisbh);
            if (Files.size() > 0) {
                res.put("status", 1);
                res.put("start", 0);
                List<Map<String, Object>> OO = new ArrayList<>();
                for (int i = 0; i < Files.size(); i++) {
                    String Src = Files.get(i);
                    Map<String, Object> OX = new HashMap<>();
                    OX.put("src", Src);
                    OX.put("thumb", "");
                    OX.put("alt", "第" + Integer.toString(i) + "个文件");
                    OO.add(OX);
                }
                res.put("data", OO);
            } else throw new Exception("没有可查看的通知书附件。");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }
}
