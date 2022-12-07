package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.services.define.ILoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysLoginUserService implements ILoginUserService {
    @Autowired
    SysLoginUserMapper userMapper;
    @Override
    @Cacheable(value="getLoginUserNameIDHash", keyGenerator = "CompanyKeyGenerator")
    public Map<String, String> getAllByNameAndID() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = userMapper.getAllByIDAndName();
        rows.stream().forEach(f -> {
            String Name = f.get("Name").toString();
            String  ID = f.get("ID").toString();
            if (result.containsKey(Name) == false) {
                result.put(Name, ID);
            }
        });
        return result;
    }

    @Override
    @Cacheable(value="getLoginUserIDNameHash", keyGenerator = "CompanyKeyGenerator")
    public Map<String, String> getAllByIDAndName() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = userMapper.getAllByIDAndName();
        rows.stream().forEach(f -> {
            String  ID = f.get("ID").toString();
            if (result.containsKey(ID) == false) {
                String Name = f.get("Name").toString();
                result.put(ID, Name);
            }
        });
        return result;
    }
}
