package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.RoleFunctionSaveMapper;
import com.zhide.dtsystem.services.define.IRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: RoleFunctionsServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月13日 21:46
 **/
@Service
public class RoleFunctionsServiceImpl implements IRoleFunctionService {


    @Autowired
    RoleFunctionSaveMapper funSaveMapper;

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 当前角色可用的。
     *
     * @return
     */
    @Override
    @Cacheable(value = "RoleHasFuns", keyGenerator = "CompanyKeyGenerator")
    public List<String> GetAllRoleFunctions(String RoleID, String MenuID) {
        return funSaveMapper.getAllRoleFunctions(MenuID, RoleID);
    }

    @Override
    @Cacheable(value = "RoleAllFuns", keyGenerator = "CompanyKeyGenerator")
    public List<String> GetAllFunctions(String MenuID) {
        return funSaveMapper.getAllFunctions(MenuID);
    }

    @Override
    @Cacheable(value = "AllRoleFuns", keyGenerator = "CompanyKeyGenerator")
    public List<String>GetAllFunctionsByRole(String RoleName){
        return funSaveMapper.getAllFunctionsByRole(RoleName);
    }

    @Override
    @Cacheable(value = "getAllRoleByFunName", keyGenerator = "CompanyKeyGenerator")
    public List<String> getAllRoleByFunName(String FunName) {
        return funSaveMapper.getAllRoleByFunName(FunName);
    }
}
