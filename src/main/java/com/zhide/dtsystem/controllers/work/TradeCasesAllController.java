package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.ITradeCasesAllService;
import com.zhide.dtsystem.viewModel.OtherBillObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/work/tradeCasesAll")
public class TradeCasesAllController {
    @Autowired
    ITradeCasesAllService tradeCasesAllService;

    @RequestMapping("/index")
    public String Index(Integer State,Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/tradeCases/all";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tradeCasesAllService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getBillObject")
    public successResult getBillObject(String CasesID){
        successResult result=new successResult();
        try
        {
            OtherBillObject Obj= tradeCasesAllService.getBillObject(CasesID);
            result.setData(Obj);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
