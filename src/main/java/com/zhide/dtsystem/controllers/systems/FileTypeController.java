package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbFileClass;
import com.zhide.dtsystem.models.tbRoleClass;
import com.zhide.dtsystem.services.define.ItbFileClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FileTypeController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月15日 9:48
 **/
@Controller
@RequestMapping("/systems/fileType")
public class FileTypeController {
    @Autowired
    ItbFileClassService fileClassService;

    @RequestMapping("/index")
    public String Index(){
        return "/systems/fileType/index";
    }
    @RequestMapping("/getAllCanUse")
    @ResponseBody
    public List<TreeListItem> getAllCanUseItems(Boolean ShowNum) {
        if(ShowNum==null)ShowNum=false;
        List<TreeListItem> lists = fileClassService.getAllCanuseItems(ShowNum);
        return lists;
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<tbFileClass> getAll() {
        return fileClassService.getAll();
    }

    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            List<tbFileClass> datas= JSON.parseArray(Data,tbFileClass.class);
            fileClassService.saveAll(datas);
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
            fileClassService.removeAll(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
