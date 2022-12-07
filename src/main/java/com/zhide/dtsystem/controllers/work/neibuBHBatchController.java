package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.define.INBBHBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName: neibuBHBatchController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月17日 14:40
 **/
@Controller
@RequestMapping("/nbBatch")
public class neibuBHBatchController {
    @Autowired
    INBBHBatchService nbService;

    @RequestMapping("/index")
    public String Index(){
        return "/work/neibuBHBatch/index";
    }
    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Old,String Now){
        successResult result=new successResult();
        try{
            Map<String,Object> oo=nbService.SaveAll(Old,Now);
            result.setData(oo);
        }
        catch (Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
