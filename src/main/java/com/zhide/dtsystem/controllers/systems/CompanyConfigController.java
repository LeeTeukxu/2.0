package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.FtpPath;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.MailConfigRepository;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.companyConfigRepository;
import com.zhide.dtsystem.repositorys.tbExcelTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/companyConfig")
public class CompanyConfigController {
    @Autowired
    companyConfigRepository companyRep;
    @Autowired
    tbExcelTemplateRepository excelRep;
    @Autowired
    MailConfigRepository mailConfigRepository;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        Optional<companyConfig> configs = companyRep.findAll().stream().findFirst();
        Optional<MailConfig> mailconfigs = mailConfigRepository.findAll().stream().findFirst();
        if (configs.isPresent()) {
            model.put("LoadData", JSON.toJSONString(configs.get()));
        }
        if (mailconfigs.isPresent()) {
            model.put("MailLoadData", JSON.toJSONString(mailconfigs.get()));
        }else model.put("MailLoadData", "{}");
        return "/systems/companyConfig/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public successResult Save(String Data) {
        successResult result = new successResult();
        try {
            companyConfig cc = JSON.parseObject(Data, companyConfig.class);
            Optional<companyConfig> findOne = companyRep.findById(cc.getId());
            if (findOne.isPresent()) {
                companyConfig one = findOne.get();
                one.setName(cc.getName());
                one.setAddress(cc.getAddress());
                one.setPhone(cc.getPhone());
                one.setRange(cc.getRange());
                one.setMemo(cc.getMemo());
                one.setImage(cc.getImage());
                one.setBankNo(cc.getBankNo());
                one.setBankName(cc.getBankName());
                one.setBankMan(cc.getBankMan());
                companyRep.save(one);

                result.setData(one);
            } else {
                throw new Exception("公司数据不存在!");
            }

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping(value = "/saveMailConfig", method = RequestMethod.POST)
    @ResponseBody
    public successResult SaveMailConfig(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            MailConfig cc = JSON.parseObject(Data, MailConfig.class);
            MailConfig config = new MailConfig();
            if (cc.getId() == null) {
                config.setAddress(cc.getAddress());
                config.setPhone(cc.getPhone());
                config.setRange(cc.getRange());
                config.setMemo(cc.getMemo());
                config.setCompanyCode(Info.getCompanyId());
            }else {
                Optional<MailConfig> findOne = mailConfigRepository.findById(cc.getId());
                if (findOne.isPresent()) {
                    config = findOne.get();
                    config.setCompanyCode(Info.getCompanyId());
                    config.setAddress(cc.getAddress());
                    config.setPhone(cc.getPhone());
                    config.setRange(cc.getRange());
                    config.setMemo(cc.getMemo());
                }
            }
            mailConfigRepository.save(config);
            result.setData(cc);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/uploadImage")
    public void uploadImage(HttpServletResponse response, MultipartFile imageFile) {
        successResult result = new successResult();
        try {
            byte[] Bs = imageFile.getBytes();
            if (Bs.length > 1024 * 500) {
                throw new Exception("文件不能超过500K大小!");
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String data = "data:image/png;base64," + encoder.encode(Bs);
            result.setData(data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getExcelImage")
    @ResponseBody
    public List<tbExcelTemplate> getExcelImage(String Code) throws Exception {
        List<tbExcelTemplate> result = new ArrayList<>();
        Optional<tbExcelTemplate> finds = excelRep.findFirstByCode(Code);
        if (finds.isPresent()) {
            tbExcelTemplate tb = finds.get();
            result.add(tb);
        } else throw new Exception(Code + "不存在!");
        return result;
    }

    @RequestMapping("/download")
    public void Download(String Code, HttpServletResponse response) {
        File targetFile = null;
        try {
            Optional<tbExcelTemplate> finds = excelRep.findFirstByCode(Code);
            if (finds.isPresent()) {
                tbExcelTemplate tb = finds.get();
                String Name = tb.getName();
                String FileName = Name + ".xls";
                String TemplatePath = tb.getTemplatePath();
                if (StringUtils.isEmpty(TemplatePath) == false) {
                    FTPUtil Ftp = new FTPUtil();
                    if (Ftp.connect()) {
                        byte[] Bs = Ftp.download(tb.getTemplatePath());
                        WebFileUtils.download(FileName, Bs, response);
                    }
                } else {
                    targetFile = ResourceUtils.getFile("classpath:static/template/" + Code + ".xls");
                    WebFileUtils.download(FileName, targetFile, response);
                }
            } else {
                targetFile = ResourceUtils.getFile("classpath:static/template/" + Code + ".xls");
                WebFileUtils.download(targetFile.getName(), targetFile, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping("/uploadExcel")
    public void UploadExcel(String Code, HttpServletResponse response, MultipartFile excelFile) {
        successResult result = new successResult();
        try {
            Optional<tbExcelTemplate> finds = excelRep.findFirstByCode(Code);
            if (finds.isPresent()) {
                tbExcelTemplate tb = finds.get();
                String FileUrl = tb.getTemplatePath();
                LoginUserInfo Info = CompanyContext.get();
                FTPUtil ftpUtil = new FTPUtil();
                if (ftpUtil.connect()) {
                    FtpPath ftpPath = new FtpPath(Info.getCompanyId());
                    String FileName = UUID.randomUUID().toString() + ".xls";
                    if (StringUtils.isEmpty(FileUrl) == false) {
                        String[] Fs = FileUrl.split("/");
                        FileName = Fs[Fs.length - 1];
                    }
                    String FilePath = ftpPath.getAttachment();
                    ftpUtil.upload(excelFile.getInputStream(), FileName, FilePath);
                    if (StringUtils.isEmpty(FileUrl)) {
                        tb.setTemplatePath(FilePath + FileName);
                        tb.setCreateTime(new Date());
                    } else tb.setUpdateTime(new Date());
                    excelRep.save(tb);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
