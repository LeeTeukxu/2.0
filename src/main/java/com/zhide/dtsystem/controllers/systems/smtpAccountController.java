package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SmtpAccountMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.smtpAccount;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.smtpAccountRepository;
import com.zhide.dtsystem.services.define.IsmtpAccountService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/systems/smtpAccount")
public class smtpAccountController {

    @Autowired
    smtpAccountRepository smtpAccountRepository;

    @Autowired
    IsmtpAccountService ismtpAccountService;

    @Autowired
    SmtpAccountMapper smtpAccountMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Optional<smtpAccount> findtb = smtpAccountMapper.getAllByUserId(Integer.parseInt(loginUserInfo.getUserId()));
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
        } else model.put("LoadData", "{}");
        return "/systems/smtpAccount/index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        smtpAccount res = null;
        try {
            String Data = request.getParameter("Data");
            String UserName = request.getParameter("UserName");
            if (Strings.isEmpty(Data) == false) {
                smtpAccount sAccount = JSON.parseObject(Data, smtpAccount.class);
                res = ismtpAccountService.Save(sAccount, UserName);
                result.setData(res);
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
