package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.GlobalContext;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.emailTemplate;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.emailTemplateRepository;
import com.zhide.dtsystem.services.define.IEmailTemplateService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmailTemplateService implements IEmailTemplateService {
    @Autowired
    emailTemplateRepository emailRep;

    @Override
    public boolean AddNew(String Code, String Name) throws IOException {
        LoginUserInfo Info = CompanyContext.get();
        String basePath = GlobalContext.getTemplateUrl();
        String tempFile = basePath + "\\shared\\defaultEmailTemplate.flt";
        String tempText = FileUtils.readFileToString(new File(tempFile), "utf-8");

        emailTemplate newOne = new emailTemplate();
        newOne.setCode(Code);
        newOne.setName(Name);
        newOne.setEmailContent(tempText);
        newOne.setCreateMan(Integer.parseInt(Info.getUserId()));
        newOne.setCreateTime(new Date());
        emailRep.save(newOne);
        return true;
    }


    @Override
    public List<TreeListItem> getEmailTemplateTypes() {
        List<TreeListItem> nodes = new ArrayList<>();
        List<emailTemplate> emails = emailRep.findAll(Sort.by(Sort.Direction.DESC, "UpdateTime"));
        for (int i = 0; i < emails.size(); i++) {
            emailTemplate email = emails.get(i);
            TreeListItem item = new TreeListItem(Integer.toString(email.getId()), "1", email.getName());
            nodes.add(item);
        }
        LoginUserInfo Info=CompanyContext.get();
        TreeListItem root = new TreeListItem("1", "0", Info.getCompanyName());
        nodes.add(0, root);
        return nodes;
    }
}
