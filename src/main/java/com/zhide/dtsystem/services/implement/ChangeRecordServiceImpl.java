package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbFormDesignRepository;
import com.zhide.dtsystem.services.define.IChangeEntity;
import com.zhide.dtsystem.services.define.IChangeRecordService;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @ClassName: ChangeRecordServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月23日 14:22
 **/
@Service
public class ChangeRecordServiceImpl implements IChangeRecordService {
    @Autowired
    ItbDictDataService dictService;
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    tbClientRepository clientRep;

    @Override
    public void CreateAndSave(List<String> fields, String ConfigName, IChangeEntity obj) {
        LoginUserInfo Info = CompanyContext.get();
        int UserID = Integer.parseInt(Info.getUserId());
        String formText = obj.getFormText();
        String preFormText = obj.getPreFormText();
        Map<String, Object> pre = new HashMap<>();
        Map<String, Object> now = JSON.parseObject(formText, new TypeReference<Map<String, Object>>() {
        });
        if (ObjectUtils.isEmpty(preFormText) == false) {
            pre = JSON.parseObject(preFormText, new TypeReference<Map<String, Object>>() {
            });
        }

        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals(ConfigName))
                .findFirst();
        String xFormText = tf.get().getAllText();
        List<Map<String, Object>> Configs = JSON.parseObject(xFormText, new TypeReference<List<Map<String, Object>>>() {
        });
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String Mode = "修改";
            String preValue = "";
            String nowValue = "";
            String label = "";
            String type = "";
            String url = "";
            String name = "";
            if (now.containsKey(field)) {
                nowValue = now.get(field).toString();
            }
            if (pre.containsKey(field)) {
                preValue = pre.get(field).toString();
            } else Mode = "新增";
            Optional<Map<String, Object>> findConfig = Configs.stream()
                    .filter(f -> f.get("name").toString().equals(field)).findFirst();
            if (findConfig.isPresent()) {
                Map<String, Object> Config = findConfig.get();
                label = Config.get("label").toString();
                type = Config.get("type").toString();
                if (Config.containsKey("url")) {
                    url = Config.get("url").toString();
                }
                name = Config.get("name").toString();
            }
            if (nowValue.equals(preValue) == false) {
                nowValue = translateValue(type, name, url, nowValue);
                preValue = translateValue(type, name, url, preValue);
                casesChangeRecord record = new casesChangeRecord();
                record.setCasesId(obj.getCasesId());
                record.setUserId(UserID);
                record.setMode(Mode);
                String ChangeText = label + "由:【" + preValue + "】，被修改为:【" + nowValue + "】";
                if (Mode == "新增") ChangeText = "新增了:" + label + ",值为:【" + nowValue + "】";
                record.setChangeText(ChangeText);
                record.setCreateTime(new Date());
                casesChangeRep.save(record);
            }
        }
    }

    private String translateValue(String type, String name, String url, String value) {
        if (type.equals("text") == true || type.equals("textearea") == true || type.equals("date")) {
            return value;
        } else if (type.equals("select")) {
            String[] ss = url.split("\\?");
            Integer dtId = Integer.parseInt(ss[1].split("=")[1]);
            List<ComboboxItem> items = dictService.getByDtId(dtId);
            Optional<ComboboxItem> findItems = items.stream().filter(f -> f.getId().equals(value)).findFirst();
            if (findItems.isPresent()) {
                return findItems.get().getText();
            } else return value;
        } else if (type.equals("buttonedit")) {
            if (StringUtils.isEmpty(value) == false) {
                if (name.equals("ClientID")) {
                    Optional<tbClient> clients = clientRep.findById(Integer.parseInt(value));
                    if (clients.isPresent()) {
                        return clients.get().getName();
                    } else return value;
                } else return value;
            } else return value;
        } else return value;
    }
}
