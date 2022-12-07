package com.zhide.dtsystem.controllers.permission;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.managerPermission;
import com.zhide.dtsystem.services.define.IManagerPermissionService;
import com.zhide.dtsystem.viewModel.ManagerPermissonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName: ManagerPermissonController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月05日 9:05
 **/

@Controller
@RequestMapping("/permission/manager")
public class ManagerPermissionController {
    @Autowired
    IManagerPermissionService mpService;

    @RequestMapping("/index")
    public String Index() {
        return "/permission/manager/index";
    }

    @RequestMapping("/getTree")
    @ResponseBody
    public List<TreeNode> GetManagerTree() {
        return mpService.GetTree();
    }

    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<TreeNode> GetAllUser() {
        return mpService.GetAllUserTree();
    }

    @ResponseBody
    @RequestMapping("/getSavedConfig")
    public successResult GetSaveConfig(Integer UserID) {
        successResult result = new successResult();
        try {
            List<managerPermission> ms = mpService.GetSavedConfig(UserID);
            result.setData(ms);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            ManagerPermissonResult OBB = JSON.parseObject(Data, ManagerPermissonResult.class);
            mpService.SaveAll(OBB);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
