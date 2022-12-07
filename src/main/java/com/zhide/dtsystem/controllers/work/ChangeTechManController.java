package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ChangeTechManResult;
import com.zhide.dtsystem.services.define.IChangeTechManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work/changeTechMan")
public class ChangeTechManController {

    @Autowired
    IChangeTechManService changeTechManService;

    @RequestMapping("/index")
    public String Index() {
        return "/work/patentInfo/changeTechMan";
    }

    @ResponseBody
    @RequestMapping("/changeTechMan")
    public successResult ChangeTechMan(String Data, String User) {
        successResult result = new successResult();
        try {
            List<ChangeTechManResult> Datas = JSON.parseArray(Data, ChangeTechManResult.class);
            Map<Integer, String> Users = JSON.parseObject(User, Map.class);
            result.setSuccess(changeTechManService.SaveAll(Datas, Users));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}

