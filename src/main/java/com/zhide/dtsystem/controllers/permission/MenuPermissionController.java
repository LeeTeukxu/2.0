package com.zhide.dtsystem.controllers.permission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.services.define.ItbMenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission/menupermission")
public class MenuPermissionController {

    @Autowired
    ItbMenuPermissionService permissionService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        List<ComboboxItem> maps = permissionService.getAllFunctionItems();
        model.put("FunctionItems", JSON.toJSONString(maps));
        return "/permission/menupermission/index";
    }

    @RequestMapping("/getItemByMenuId")
    @ResponseBody
    public List<Integer> getItemByMenuID(int menuId) {
        return permissionService.getAllByMenuID(menuId);
    }

    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult saveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<Integer, List<Integer>> Datas = JSON.parseObject(Data,
                    new TypeReference<Map<Integer, List<Integer>>>() {
            });
            permissionService.SaveAll(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/copyConfig")
    @ResponseBody
    public successResult CopyConfig(Integer Source, Integer Target) {
        successResult result = new successResult();
        try {
            permissionService.CopyConfig(Source, Target);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
