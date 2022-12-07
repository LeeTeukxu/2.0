package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ImageUtils;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.models.EmailContent;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TextAndValue;
import com.zhide.dtsystem.models.smtpAccount;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.smtpAccountRepository;
import com.zhide.dtsystem.services.define.ISendEmailService;
import com.zhide.dtsystem.services.instance.emailAttachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class SendEmailService implements ISendEmailService {
    @Autowired
    JavaMailSender mailSender;
    JavaMailSenderImpl instance;
    @Autowired
    smtpAccountRepository smtpRep;

    LoginUserInfo currentUser;
    smtpAccount currentSmpt;

    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private Integer port;

    @Override
    public void sendEmailByContent(EmailContent content) throws Exception {
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        changeToCurrentUserAccount();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        addSenderToInfo(helper, content);
        addReceiveInfo(helper, content);
        addAttachment(helper, content);
        String htmlText = URLDecoder.decode(content.getContent(), "utf-8");
        helper.setText(htmlText, true);
        helper.setSubject(content.getSubject());

        mailSender.send(message);
    }

    @Override
    public void sendPantentEmailByContent(EmailContent content) throws Exception {
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        changeToCurrentUserConfiguration();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        addPatentMailToInfo(helper, content);
        addReceiveInfo(helper, content);
        addAttachment(helper, content);
        String htmlText = URLDecoder.decode(content.getContent(), "utf-8");
        helper.setText(htmlText, true);
        helper.setSubject(content.getSubject());

        mailSender.send(message);
    }

    private void changeToCurrentUserAccount() throws Exception {
        currentUser = CompanyContext.get();
        int userId = Integer.parseInt(currentUser.getUserId());
        currentSmpt = smtpRep.getByUserId(userId);
        if (currentSmpt == null) {
            Optional<smtpAccount> findOnes = smtpRep.findFirstByCompanyDefaultIsTrue();
            if (findOnes.isPresent()) {
                currentSmpt = findOnes.get();
            }
        }
        instance = (JavaMailSenderImpl) mailSender;
        if (currentSmpt != null) {
            instance.setUsername(currentSmpt.getUserName());
            instance.setPassword(currentSmpt.getPassword());
            instance.setHost(currentSmpt.getServer());
            instance.setPort(currentSmpt.getPort());
        } else throw new Exception("请设置个人的邮箱信息，否则无法发送邮件!");
    }

    private void changeToCurrentUserConfiguration() throws Exception {
        instance = (JavaMailSenderImpl) mailSender;
        instance.setUsername(username);
        instance.setPassword(password);
        instance.setHost(host);
        instance.setPort(port);
    }

    private void addSenderToInfo(MimeMessageHelper message, EmailContent content) throws Exception {
        if (currentSmpt != null && Strings.isEmpty(currentSmpt.getUserName()) == false) {
            message.setFrom(currentSmpt.getUserName(), currentSmpt.getNickName());
        } else {
            message.setFrom(instance.getUsername(), currentUser.getUserName());
        }
    }

    private void addPatentMailToInfo(MimeMessageHelper message, EmailContent content) throws Exception {
        message.setFrom(instance.getUsername(), "系统管理员");
    }

    private void addReceiveInfo(MimeMessageHelper message, EmailContent content) throws Exception {
        List<TextAndValue> vs = content.getReceAddress();
        for (int i = 0; i < vs.size(); i++) {
            TextAndValue v = vs.get(i);
            InternetAddress address = new InternetAddress();
            address.setAddress(v.getValue());
            address.setPersonal(v.getText());
            message.addCc(address);
        }
    }


    private void addAttachment(MimeMessageHelper message, EmailContent content) throws Exception {
        List<TextAndValue> atts = content.getAttachments();
        if (atts.size() > 0) {
            emailAttachment att=new emailAttachment(message);
            att.groupAndSave(atts);
        }
    }
}
