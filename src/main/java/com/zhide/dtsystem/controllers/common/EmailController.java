package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.RegexUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientLinkersRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tzsEmailRecordRepository;
import com.zhide.dtsystem.services.define.ISendEmailService;
import com.zhide.dtsystem.services.define.ITZSService;
import com.zhide.dtsystem.services.emailTemplateParsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/common/email")
public class EmailController {
    @Autowired
    ISendEmailService emailSender;
    @Autowired
    emailTemplateParsor emailTemplateParsor;
    @Autowired
    ITZSService itzsService;
    @Autowired
    tzsEmailRecordRepository tzsRep;
    @Autowired
    tbClientLinkersRepository linkersRep;
    @Autowired
    tbClientRepository tbClientRep;

    @RequestMapping("/index")
    public String Index(String Code,String KH, Map<String, Object> model) {
        try {
            model.put("Code", Code);
            model.put("KH",StringUtils.isEmpty(KH)?"":KH);
            if(StringUtils.isEmpty(KH)==false){
                List<String>KHIDS= Arrays.asList(KH.split(",")).stream().distinct().collect(Collectors.toList());
                if(KHIDS.size()==1) {
                    int ClientID = Integer.parseInt(KHIDS.get(0));
                    getDefaultEmail(ClientID,model);
                }
            }
            if(model.containsKey("ClientEmail")==false){
                model.put("ClientName", "");
                model.put("ClientEmail", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.put("Content", "");
        }
        return "/common/email/index";
    }
    private void getDefaultEmail(int ClientID,Map<String,Object> model){
        List<tbClientLinkers> findOnes=linkersRep.findAllByClientID(ClientID);
        List<tbClientLinkers> useOnes=findOnes.stream().filter(f->{
            Integer Position =f.getPosition();
            if(Position==null)Position=1;
            return Position==1;
        }).collect(Collectors.toList());
        if(useOnes.size()>0) {
            Optional<tbClient> findClients = tbClientRep.findById(ClientID);
            if (findClients.isPresent()) {
                tbClientLinkers one = findOnes.get(0);
                tbClient client = findClients.get();
                String Email = one.getEmail();
                if (StringUtils.isEmpty(Email) == false) {
                    Email = Email.trim();
                    if (RegexUtils.IsEmail(Email) == true) {
                        model.put("ClientName", client.getName() + "_" + one.getLinkMan());
                        model.put("ClientEmail", Email);
                    }
                }
            }
        }
    }
    @ResponseBody
    @PostMapping(value = "/sendEmail")
    public successResult sendEmail(HttpServletRequest request, String data,String Record) {
        successResult result = new successResult();
        try {
            EmailContent content = JSON.parseObject(data, EmailContent.class);
            emailSender.sendEmailByContent(content);
            //代缴费清单.zip
            List<TextAndValue> list = content.getAttachments();
            for (int i = 0; i < list.size(); i++) {
                String FilePath = list.get(i).getValue();
                deleteFile(FilePath);
            }
            if(StringUtils.isEmpty(Record)==false){
                LoginUserInfo info = CompanyContext.get();
                List<tzsEmailRecord> records = JSON.parseArray(Record, tzsEmailRecord.class);
                for (int i = 0; i < records.size(); i++) {
                    tzsEmailRecord record = records.get(i);
                    record.setTid(UUID.randomUUID().toString());
                    record.setSendTime(new Date());
                    record.setSendUserName(info.getUserName());
                    record.setSendUser(info.getUserIdValue());
                    tzsRep.save(record);
                }
            }
        } catch (Exception e) {
            result.raiseException(e);
        }
        return result;
    }

    public void deleteFile(String FilePath) {
        File file = new File(FilePath);
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    @ResponseBody
    @PostMapping(value = "/getMailContent")
    public successResult getMailContent(String Code) {
        successResult result = new successResult();
        try {
            String Content = emailTemplateParsor.getSendContent(Code);
            result.setData(Content);
        } catch (Exception e) {
            result.raiseException(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllByCodes")
    public successResult getAllByCode(@RequestParam("Code") String[] Code) {
        successResult result = new successResult();
        try {
            List<ComboboxItem> res = itzsService.getItemsByCode(Code);
            result.setData(res);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
