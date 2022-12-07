package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleFunctionSaveMapper {
    @Select(value = "SELECT rtrim(ltrim(dbo.FunctionPermissionList.Name)) as Name  FROM  dbo.FunctionPermissionList " +
            "INNER JOIN   dbo.tbRoleFunctionSave ON dbo.FunctionPermissionList.FID = dbo.tbRoleFunctionSave.FunID " +
            "WHERE (dbo.tbRoleFunctionSave.RoleID =#{RoleID})  AND (dbo.tbRoleFunctionSave.MenuID = #{MenuID})")
    List<String> getAllRoleFunctions(String MenuID, String RoleID);

    @Select(value = "Select rtrim(ltrim(b.Name))  from tbPermissionItem a inner join FunctionPermissionList b on a" +
            ".FunID=b.FID  " +
            "Where a.MenuID=#{MenuID}")
    List<String> getAllFunctions(String MenuID);
    @Select(value = "SELECT distinct(replace(a.Name,' ','')) as Name  FROM  dbo.FunctionPermissionList a INNER JOIN dbo.tbRoleFunctionSave b ON a.FID = b.FunID inner join tbRoleClass c on b.RoleID=c.RoleID  Where RoleName=#{RoleName}")
    List<String> getAllFunctionsByRole(String RoleName);
    @Select(value="Select distinct  c.RoleName from tbRoleFunctionSave a inner join FunctionPermissionList b on a" +
            ".FunID=b.FID inner join tbRoleClass c on a.RoleID=c.RoleID Where b.Name=#{FunName}")
    List<String>getAllRoleByFunName(String FunName);
}
