package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.cancelCasesMain;
import com.zhide.dtsystem.models.cancelCasesSub;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.cancelCasesMainRepository;
import com.zhide.dtsystem.services.define.ICancelCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CancelCasesController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年06月23日 11:04
 **/
@Controller
@RequestMapping("/work/cancelCases")
public class CancelCasesController {
    @Autowired
    ICancelCasesService cancelCasesService;
    @Autowired
    cancelCasesMainRepository mainRep;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("UserID", loginInfo.getUserId());
        model.put("RoleName", loginInfo.getRoleName());
        model.put("RoleID", loginInfo.getRoleId());
        return "/work/cancelCases/index";
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        model.put("Mode","Add");
        return "/work/cancelCases/add";
    }

    @RequestMapping("/browse")
    public String Browse(Map<String, Object> model) {
        model.put("Mode","Browse");
        return "/work/cancelCases/add";
    }
    @RequestMapping("/audit")
    public String Audit(Map<String,Object> model){
        model.put("Mode","Audit");
        return "/work/cancelCases/add";
    }
    @RequestMapping("/set")
    public String Set(Map<String,Object> model){
        model.put("Mode","Set");
        return "/work/cancelCases/add";
    }
    @ResponseBody
    @RequestMapping("/getSubsByCasesId")
    public successResult getSubsByCasesID(String CasesID) {
        successResult result = new successResult();
        try {
            result.setData(cancelCasesService.getAllSubs(CasesID));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Main, String Sub) {
        successResult result = new successResult();
        try {
            cancelCasesMain main = JSON.parseObject(Main, cancelCasesMain.class);
            List<cancelCasesSub> subs = JSON.parseArray(Sub, cancelCasesSub.class);
            cancelCasesService.SaveAll(main, subs);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getMain")
    public pageObject getMain(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = cancelCasesService.getMain(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/refreshTotal")
    public successResult refreshTotal() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OS = cancelCasesService.getCancelCasesTotal();
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getSub")
    public pageObject getSub(Integer MainID){
        pageObject result=new pageObject();
        if(MainID==null)MainID=0;
        result=cancelCasesService.getSub(MainID);
        return result;
    }
    @ResponseBody
    @RequestMapping("/auditOne")
    public successResult AuditOne(Integer ID,int Result,String Memo){
        successResult result=new successResult();
        try
        {
            cancelCasesService.AuditOne(ID,Result,Memo);
        }
        catch(Exception ax){
            result.raiseException( ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/setOne")
    public successResult SetOne(Integer ID,int Result,String Memo){
        successResult result=new successResult();
        try
        {
            cancelCasesService.SetOne(ID,Result,Memo);
        }
        catch(Exception ax){
            result.raiseException( ax);
        }
        return result;
    }
    @RequestMapping("/getCasesMain")
    @ResponseBody
    public pageObject getCasesMain(HttpServletRequest request) {
        pageObject result=null ;
        try {
            result = cancelCasesService.getCasesMain(request);
        } catch (Exception e) {
            result.raiseException(e);
            //e.printStackTrace();
        }
        return result;
    }
    @PostMapping("/remove")
    @ResponseBody
    public successResult Remove(Integer ID){
        successResult result=new successResult();
        try {
            cancelCasesService.Remove(ID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
