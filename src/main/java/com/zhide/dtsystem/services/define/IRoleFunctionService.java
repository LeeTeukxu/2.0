package com.zhide.dtsystem.services.define;

import java.util.List;

public interface IRoleFunctionService {
    List<String> GetAllRoleFunctions(String RoleID, String MenuID);

    List<String> GetAllFunctions(String MenuID);

    List<String>GetAllFunctionsByRole(String RoleName);
    List<String>getAllRoleByFunName(String FunName);
}
