package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.setNBBHMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/systems/setNBBH")
public class setNBBHController {

    @Autowired
    SysLoginUserMapper sysLoginUserMapper;

    @Autowired
    setNBBHMapper setNBBHMapper;

    @RequestMapping("/edit")
    public String Edit(int ID, Map<String, Object> model) {
        if (ID != 0) {
            model.put("LoadData", JSON.toJSONString(getZlxz(ID)));
        } else model.put("LoadData", "{}");
        model.put("Users", JSON.toJSONString(getUsers()));
        return "/systems/setNBBH/edit";
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

    private Map<String, String> getZlxz(int ID) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Map<String, String> results = new HashMap<>();
        List<Map<String, Object>> rows = setNBBHMapper.getZlxz(ID);
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            results.put("JS", row.get("JS").toString());
            results.put("XS", row.get("XS").toString());
            results.put("LC", loginUserInfo.getUserId());
            results.put("KH", row.get("KH").toString());
        }
        return results;
    }
}
