package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesYwAccept;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.services.define.IShenBaoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work/shenBaoDetail")
public class ShenBaoDetailController {
    @Autowired
    IShenBaoDetailService shenBaoDetailService;
    @Autowired
    casesYwAcceptRepository casesYwAccRep;

    @RequestMapping("/index")
    public String Index(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserId());
        return "/work/case/shenBaoDetail";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult save(String Data) {
        successResult result = new successResult();
        try {
            List<casesYwAccept> Datas = JSON.parseArray(Data, casesYwAccept.class);
            boolean OX = shenBaoDetailService.SaveAll(Datas);
            result.setSuccess(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public List<casesYwAccept> getData(String CasesID) {
        return casesYwAccRep.findAllByCasesId(CasesID);
    }

    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public successResult removeAll(Integer ID) {
        successResult result = new successResult();
        try {
            boolean OX = shenBaoDetailService.RemoveAll(ID);
            result.setSuccess(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
