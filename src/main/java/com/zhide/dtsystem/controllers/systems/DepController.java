package com.zhide.dtsystem.controllers.systems;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.services.define.ItbDepListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/systems/dep")
@SimplePageName(name = "tbDepList")
public class DepController {

    @Autowired
    private ItbDepListService itbDepListService;

    @RequestMapping("/index")
    public String Index() {
        return "/systems/dep/index";
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<tbDepList> getAll() {
        return itbDepListService.getAll();
    }

    @RequestMapping("/getAllCanUse")
    @ResponseBody
    public List<tbDepList> getAllCanUse(Map<String, Object> model) {
        List<Map<String, Object>> listNum = itbDepListService.getAllByCanUseAndDepNum();
        model.put("LoginUserNum", listNum);
        return itbDepListService.getAllCanUse();
    }

    @Allow(permission = PermissionType.Save)
    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    @ResponseBody
    public successResult saveAll(@RequestBody List<Map<String, Object>> datas) {
        successResult result = new successResult();
        try {
            itbDepListService.saveAll(datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/removeAll", method = RequestMethod.POST)
    @ResponseBody
    public successResult removeAll(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            itbDepListService.removeAll(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllUsersByDep")
    public List<TreeNode> getAllUsersByDep() {
        return itbDepListService.getAllUsersByDep();
    }

    @ResponseBody
    @RequestMapping("/getAllLoginUsersByDep")
    public List<TreeNode> getAllLoginUserInDep() {
        return itbDepListService.getAllLoginUserInDep();
    }
    @ResponseBody
    @RequestMapping("/getAllLoginUsersByDepNotSelf")
    public List<TreeNode> getAllLoginUserInDepNotSelf(HttpServletRequest request) {
        Integer UserID = Integer.parseInt(request.getParameter("UserID"));
        return itbDepListService.getAllLoginUserInDepNotSelf(UserID);
    }
    @ResponseBody
    @RequestMapping("/getAllLoginUserByFun")
    public  List<Map<String,Object>>getAllLoginUserByFun(String FunName){
        return itbDepListService.getAllLoginUserByFun(FunName);
    }
}
