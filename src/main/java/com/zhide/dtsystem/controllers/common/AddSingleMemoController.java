package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.simpleMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IAddSingleMemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AddSingleMemoController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月01日 10:10
 **/
@Controller
@RequestMapping("/addSingleMemo")
public class AddSingleMemoController {
    @Autowired
    IAddSingleMemoService memoService;
    @RequestMapping("/index")
    public String Index(String ID, Map<String, Object> model) {
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserIdValue());
        model.put("UserName", Info.getUserName());
        model.put("ID", ID);
        String[] IDS=ID.split(",");
        model.put("BatchMode",IDS.length>1);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        model.put("Now", simple.format(new Date()));
        return "/common/addMemo";
    }

    @RequestMapping("/saveImage")
    @ResponseBody
    public successResult SaveImage(String Data) {
        successResult result = new successResult();
        try {
            simpleMemo memo= JSON.parseObject(Data, simpleMemo.class);
            memoService.SaveImage(memo);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getData")
    @ResponseBody
    public List<simpleMemo> getData(String SubID){
        try {
            return memoService.getData(SubID);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
