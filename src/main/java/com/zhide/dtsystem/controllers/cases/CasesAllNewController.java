package com.zhide.dtsystem.controllers.cases;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.ICasesAllNewService;
import com.zhide.dtsystem.viewModel.CasesBillObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/casesAll")
public class CasesAllNewController {
    @Autowired
    ICasesAllNewService allService;

    @Autowired
    ApplicationContext context;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        return "/work/caseNew/all";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {

            result = allService.getData(request);
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
          CasesBillObject Obj= allService.getBillObject(CasesID);
          result.setData(Obj);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
