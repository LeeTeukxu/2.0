package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbCLevel;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.tbCLevelRepository;
import com.zhide.dtsystem.services.define.ICLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: CLevelController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月29日 17:16
 **/
@RestController
@RequestMapping("/cLevel")
public class CLevelController {
    @Autowired
    ICLevelService clService;
    @Autowired
    tbCLevelRepository cLevelRep;
    @PostMapping("/save")
    public successResult Save(String Data){
        successResult result=new successResult();
        try {
            clService.Save(Data);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @GetMapping("/getItems")
    public List<ComboboxItem> getItems(){
        return clService.getItems();
    }
    @PostMapping("/removeOne")
    public successResult RemoveOne(int ID){
        successResult result=new successResult();
        try
        {
            clService.RemoveOne(ID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @PostMapping("/getData")
    public List<tbCLevel> getData(){
        return cLevelRep.findAll();
    }

}
