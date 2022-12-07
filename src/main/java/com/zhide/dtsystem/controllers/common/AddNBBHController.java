package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.services.NBBHCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: AddNBBHController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年09月06日 10:42
 **/
@Controller
@RequestMapping("/addNBBH")
public class AddNBBHController {
    @Autowired
    NBBHCreator creator;
    @Autowired
    SysLoginUserMapper sysLoginUserMapper;
    @Autowired
    casesSubRepository subRep;

    @RequestMapping("/index")
    public String Index(String SubID, String NBBH, Map<String, Object> model) throws Exception {
        try {
            NBBH = URLDecoder.decode(NBBH, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> Ks = creator.parse(NBBH);
        for (String Key : Ks.keySet()) {
            model.put(Key, Ks.get(Key));
        }
        model.put("NBBH", NBBH);
        model.put("SubID", SubID);
        model.put("Users", JSON.toJSONString(getUsers()));
        return "/common/addNBBH";
    }

    private Map<String, String> getUsers() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = sysLoginUserMapper.getAllByIDAndName();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            result.put(row.get("Name").toString(), row.get("ID").toString());
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(String NBBH, String SubID) {
        successResult result = new successResult();
        try {
            Optional<casesSub> findOnes = subRep.findFirstBySubId(SubID);
            if (findOnes.isPresent()) {
                casesSub One = findOnes.get();
                One.setNbbh(NBBH);
                subRep.save(One);
            } else throw new Exception("操作数据已不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
