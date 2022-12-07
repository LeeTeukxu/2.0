package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysLoginUserMapper {
    @Select(value = "Select EmpName  as Name,UserID as ID from v_LoginUser")
    List<Map<String, Object>> getAllByIDAndName();

    @Select(value = "Select EmpName  as Name,EmpID as ID from v_LoginUser")
    List<Map<String, Object>> getAllLoginUserIDAndNameHash();

    @Select(value = "Select EmpName from v_LoginUser Where UserID=#{ID}")
    String getLoginUserNameById(int ID);

    @Select(value = "SELECT LoginCode FROM v_LoginUser WHERE UserID=#{ID}")
    String getLoginCodeById(int ID);

    @Select(value = "Select DepID from v_LoginUser Where UserID=#{ID}")
    Integer getLoginUserDepIDbyId(int ID);

    @Select(value = "Select dbo.getManagerByUserID(${UserID})")
    String getManager(String UserID);

    @Select(value = "Select top 1 UserID from v_LoginUser Where RoleID=2")
    Integer getAdminstratorUser();

    @Select(value = "Select Name from View_UserOfRoleInCases Where RoleName=#{RoleName}")
    List<String> getAllCasesFunsByRole(String RoleName);

    @Select(value = "Select * from dbo.GetUserTreeByRole(${RoleID})")
    List<Map<String, Object>> getAllUserByRole(Integer RoleID);

    @Select(value = "Select * from v_LoginUser Where  RoleName like '%${RoleName}%'")
    List<Map<String, Object>> getAllManagers(String RoleName);

    @Select(value = "Select * from v_LoginUser")
    List<Map<String, Object>> getAllUsers();
    @Select(value="Select Count(0) from v_LoginUser  Where EmpName=#{Name}")
    Integer getNumberByName(String Name);
}
