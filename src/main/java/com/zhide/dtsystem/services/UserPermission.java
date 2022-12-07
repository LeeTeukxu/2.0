package com.zhide.dtsystem.services;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.services.define.IRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: UserPermission
 * @Author: 肖新民
 * @*TODO:判断当前角色是否有某某权限。
 * @CreateTime: 2020年08月2020-08-07日 16:09
 **/
@Component
public class UserPermission {
    @Autowired
    SysLoginUserMapper sysMapper;
    @Autowired
    IRoleFunctionService roleService;

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-07 23:49
     * @Params:[RoleName, FunName]
     * Description:某个角色是否有某个操作权限。用静态变量做缓存。
     */
    public boolean Exists(String RoleName, String FunName) {
        List<String> Funs = roleService.GetAllFunctionsByRole(RoleName);
        return Funs.contains(FunName);
    }
    public List<String>findAllByFunName(String FunName){
        List<String> Roles=roleService.getAllRoleByFunName(FunName);
        return Roles;
    }
}
