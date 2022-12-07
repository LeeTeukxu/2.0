package com.zhide.dtsystem.controllers.permission;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbRoleClass;
import com.zhide.dtsystem.services.define.ItbRoleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission/roleClass")
public class RoleController {
    @Autowired
    private ItbRoleClassService itbRoleClassService;


    @RequestMapping("/index")
    public String Index() {
        return "/permission/roleClass/index";
    }

    @RequestMapping("/getAllCanUse")
    @ResponseBody
    public List<TreeListItem> getAllCanUseItems() {
        List<TreeListItem> lists = itbRoleClassService.getAllCanuseItems(true);
        return lists;
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<tbRoleClass> getAll() {
        return itbRoleClassService.getAll();
    }

    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(@RequestBody List<tbRoleClass> datas) {
        successResult result = new successResult();
        try {
            itbRoleClassService.saveAll(datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/removeAll")
    @ResponseBody
    public successResult removeAll(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            itbRoleClassService.removeAll(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllUserByRole")
    public List<Map<String, Object>> getAllUserByRole(Integer RoleID) {
        return itbRoleClassService.getAllUserByRole(RoleID);
    }
}
