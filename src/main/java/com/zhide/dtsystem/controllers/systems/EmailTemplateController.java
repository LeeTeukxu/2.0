package com.zhide.dtsystem.controllers.systems;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.emailTemplate;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.emailTemplateRepository;
import com.zhide.dtsystem.services.define.IEmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/systems/emailTemplate")
public class EmailTemplateController {


    @Autowired
    IEmailTemplateService emailService;

    @Autowired
    emailTemplateRepository emailRep;

    @RequestMapping("/index")
    public String Index() {
        return "/systems/emailTemplate/index";
    }

    @RequestMapping("/addNew")
    @ResponseBody
    public successResult Add(String TemName, String TemCode) {
        successResult result = new successResult();
        try {
            emailService.AddNew(TemCode, TemName);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getTemplateTypes")
    @ResponseBody
    public List<TreeListItem> getTemplateTypes() {
        List<TreeListItem> result = new ArrayList<>();
        try {
            result = emailService.getEmailTemplateTypes();
        } catch (Exception ax) {

        }
        return result;
    }

    @RequestMapping("/getById")
    @ResponseBody
    public successResult getById(int Id) {
        successResult result = new successResult();
        try {
            Optional<emailTemplate> oobj = emailRep.findById(Id);
            if (oobj.isPresent() == true) {
                emailTemplate obj = oobj.get();
                result.setData(obj.getEmailContent());
            } else throw new Exception("ID：" + Integer.toString(Id) + "的记录不存在。");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public successResult save(int Id, String Content) {
        successResult result = new successResult();
        try {
            LoginUserInfo info = CompanyContext.get();
            Optional<emailTemplate> oobj = emailRep.findById(Id);
            if (oobj.isPresent() == true) {
                emailTemplate obj = oobj.get();
                obj.setEmailContent(Content);
                obj.setUpdateTime(new Date());
                obj.setUpdateMan(Integer.parseInt(info.getUserId()));
                emailRep.save(obj);
            } else throw new Exception("ID：" + Integer.toString(Id) + "的记录不存在。");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(int Id) {
        successResult result = new successResult();
        try {
            LoginUserInfo info = CompanyContext.get();
            Optional<emailTemplate> oobj = emailRep.findById(Id);
            if (oobj.isPresent() == true) {
                emailTemplate obj = oobj.get();
                emailRep.delete(obj);
            } else throw new Exception("ID：" + Integer.toString(Id) + "的记录不存在。");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
