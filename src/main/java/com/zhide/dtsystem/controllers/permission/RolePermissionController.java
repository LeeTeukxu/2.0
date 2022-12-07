package com.zhide.dtsystem.controllers.permission;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.CompanyTimeOutCache;
import com.zhide.dtsystem.services.define.ItbRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission/rolepermission")
public class RolePermissionController {

    @Autowired
    ItbRolePermissionService rolePermissionService;
    @Autowired
    CompanyTimeOutCache menuCache;

    @RequestMapping("/index")
    public String Index() {
        return "/permission/rolepermission/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public List<Map<String, Object>> getData(int roleId) throws Exception {

        return rolePermissionService.findAllByRoleId(roleId);
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(int roleId, @RequestBody Map<Integer, List<Integer>> data) {
        successResult result = new successResult();
        try {
            menuCache.setTimeOut(600000L);
            rolePermissionService.SaveAll(roleId, data);
            menuCache.delete("VisibleMenu:" + new Integer(roleId).toString());
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
