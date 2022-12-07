package com.zhide.dtsystem.services;

import com.zhide.dtsystem.common.DepDataHelper;
import com.zhide.dtsystem.mapper.SQPermissionMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SQPermission {
    @Autowired
    tbLoginUserRepository loginUserRepository;
    @Autowired
    SQPermissionMapper sqPermissionMapper;

    public List<String> getByCurrentUser() throws Exception {
        List<String> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new RuntimeException("登录信息已失败，请重新登录 。");
        String roleName = info.getRoleName();
        if (roleName.equals("系统管理员")) return result;
        if (roleName.indexOf("流程") > -1) return result;
        if (roleName.indexOf("财务") > -1) return result;


        if (roleName.indexOf("经理") > -1) {
            params.replace("roleName", "1");
            int depId = Integer.parseInt(info.getDepId());
            List<Integer> deps = DepDataHelper.getAllChildrenId(depId);
            List<Integer> Users = loginUserRepository.findAllByDepIdIn(deps).stream().map(f -> f.getUserId()).collect
                    (Collectors.toList
                            ());
            params.put("Ids", Users);
        } else {
            params.put("Ids", Arrays.asList(info.getUserId()));
            params.replace("roleName", "1");
        }
        if (roleName == "KH") params.put("userType", "KH");
        else params.put("userType", "EMP");
        List<String> allPermission = sqPermissionMapper.getIds(params);
        return allPermission;
    }
}
