package com.zhide.dtsystem.controllers.alert;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.define.INeiBuBHErrorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: NeiBuBHErrorController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月25日 14:23
 **/
@Controller
@RequestMapping("/alert")
public class NeiBuBHErrorController {
    @RequestMapping("/index")
    public String Index() {
        return "/alert/neibubhError";
    }

    @Autowired
    INeiBuBHErrorService nbService;

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = nbService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateAll")
    public successResult UpdateAll(String Old, String Now) {
        successResult result = new successResult();
        try {
            if (StringUtils.isEmpty(Old) || StringUtils.isEmpty(Now)) {
                throw new Exception("变更值和原始值不能为空!");
            }
            Integer Num = nbService.UpdateAll(Old, Now);
            result.setData(Num);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

}
