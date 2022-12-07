package com.zhide.dtsystem.services;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.companyConfig;
import com.zhide.dtsystem.models.emailTemplate;
import com.zhide.dtsystem.models.emailTemplateParameters;
import com.zhide.dtsystem.repositorys.companyConfigRepository;
import com.zhide.dtsystem.repositorys.emailTemplateParametersRepository;
import com.zhide.dtsystem.repositorys.emailTemplateRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class emailTemplateParsor {
    HttpServletRequest request;
    List<emailTemplateParameters> paramatersList = null;

    public emailTemplateParsor(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    emailTemplateParametersRepository emailParametersRep;
    @Autowired
    emailTemplateRepository emailRep;
    @Autowired
    companyConfigRepository companyRep;

    public String getSendContent(String Code) throws Exception {
        emailTemplate template = getTemplateByCode(Code);
        List<emailTemplateParameters> parameters = getParamatersListByCode(template.getId());
        Map<String, Object> param = createParameter(parameters, request);
        Configuration configuration = new Configuration();
        StringWriter writer = new StringWriter();
        Template tt = new Template("Template", new StringReader(template.getEmailContent()), configuration);
        tt.process(param, writer);
        return writer.toString();
    }

    public String getPreviewContent() {
        return "";
    }

    private List<emailTemplateParameters> getParamatersListByCode(int PID) {
        return emailParametersRep.findAllByPid(PID);
    }

    private Map<String, Object> createParameter(List<emailTemplateParameters> ps, HttpServletRequest request) throws
            Exception {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < ps.size(); i++) {
            emailTemplateParameters p = ps.get(i);
            int type = p.getType();
            String Name = p.getName();
            if (type == 1) {
                String V = request.getParameter(Name);
                result.put(Name, V);
            } else if (type == 2) {
                String G = request.getParameter(Name);
                List<Map<String, Object>> oo = new ArrayList<Map<String, Object>>();
                G = URLDecoder.decode(G, "utf-8");
                List<Map<String, Object>> rows = JSON.parseObject(G, oo.getClass());
                result.put(Name, rows);
            }
        }
        companyConfig comConfig = companyRep.findAll().get(0);
        result.put("companyRange", StringUtils.isEmpty(comConfig.getRange()) == true ? red("【请去公司基本信息修改经营范围】") :
                comConfig
                .getRange());
        result.put("companyPhone", StringUtils.isEmpty(comConfig.getPhone()) == true ? red("【请去公司基本信息修改联系电话】") :
                comConfig
                .getPhone());
        result.put("companyMemo", StringUtils.isEmpty(comConfig.getMemo()) == true ? red("【请去公司基本信息修改公司简介】") : comConfig
                .getMemo());
        result.put("companyAddress", StringUtil.isNullOrEmpty(comConfig.getAddress()) == true ? red(
                "【请去公司基本信息修改公司地址】") : comConfig
                .getAddress());
        result.put("companyName", StringUtils.isEmpty(comConfig.getName()) == true ? red("【请去公司基本信息修改公司名称】") : comConfig
                .getName());
        return result;
    }

    private emailTemplate getTemplateByCode(String Code) {
        return emailRep.findAllByCode(Code).stream().findFirst().get();
    }

    private String red(String text) {
        return "<span  style='color:red'>" + text + "</span>";

    }
}
