package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.models.tbClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/clientInfo")
public class ImportClientDataController {
    @Autowired
    ClientInfoMapper clientInfoMapper;

    Logger logger = LoggerFactory.getLogger(ImportClientDataController.class);

    @RequestMapping("/ImportClientData")
    public String ImportClientData(String Type, String Code, String FileName, Map<String, Object> model) {
        StringBuilder sb = new StringBuilder();
        tbClient client;
        List<Map<String, String>> list = clientInfoMapper.getAllClient();
        List<String> KS=list.stream().map(f->f.get("Name").toString()).collect(Collectors.toList());
        model.put("Type", Type);
        model.put("Code", Code);
        model.put("FileName", FileName);
        model.put("AllClient", JSON.toJSONString(StringUtils.join(KS,",")));
        return "/work/client/ImportClientData";
    }
}
