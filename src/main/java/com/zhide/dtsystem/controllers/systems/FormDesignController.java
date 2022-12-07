package com.zhide.dtsystem.controllers.systems;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbFormDesign;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbFormDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/systems/formDesign")
public class FormDesignController {

    @Autowired
    tbFormDesignRepository formRep;

    @RequestMapping("/index")
    public String Index() {
        return "/systems/formDesign/index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(tbFormDesign Info) {
        successResult result = new successResult();
        try {
            LoginUserInfo logInfo = CompanyContext.get();
            if (Info.getId() == null) {
                Info.setCreateTime(new Date());
                Info.setCreateMan(Integer.parseInt(logInfo.getUserId()));
            } else {
                Optional<tbFormDesign> fSave = formRep.findById(Info.getId());
                if (fSave.isPresent()) {
                    tbFormDesign tSave = fSave.get();
                    Info.setCreateMan(tSave.getCreateMan());
                    Info.setCreateTime(tSave.getCreateTime());
                    Info.setUpdateMan(Integer.parseInt(logInfo.getUserId()));
                    Info.setUpdateTime(new Date());
                }
            }
            formRep.save(Info);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAll")
    public List<tbFormDesign> getAll() {
        return formRep.findAll();
    }
}

