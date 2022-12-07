package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.CompanyTimeOutCache;
import com.zhide.dtsystem.services.define.IAllNoticeService;
import com.zhide.dtsystem.services.define.IPatentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work/allNotice")
public class AllNoticeController {
    @Autowired
    IAllNoticeService allNoticeService;
    @Autowired
    IPatentInfoService patentInfoService;
    @Autowired
    CompanyTimeOutCache menuCache;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo Info= CompanyContext.get();
        model.put("RoleName",Info.getRoleName());
        model.put("Users", JSON.toJSONString(patentInfoService.GetLoginUserHash()));
        return "/work/allNotice/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = allNoticeService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/tzssendtypeindex")
    public String TZSSendTypeIndex() {
        return "/work/TZSSendType/index";
    }
    
    @RequestMapping("/getTZSSendTypeData")
    @ResponseBody
    public List<Map<String, Object>> getTZSSendTypeData() throws Exception {
        return allNoticeService.getTZSSendTypeData();
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(@RequestBody List<String> data) {
        LoginUserInfo Info = CompanyContext.get();
        successResult result = new successResult();
        try {
            menuCache.setTimeOut(600000L);
            allNoticeService.SaveAll(data);
            menuCache.delete("VisiblePeriodChecked:" + new Integer(Info.getCompanyId()));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
