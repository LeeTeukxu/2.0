package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.LoginUserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AllUserListMapper {
    @Select(value = "Select CompanyID from DTSystem.dbo.AllUsersList Where rtrim(ltrim(Account))=#{account}")
    String findCompanyIdByAccount(String account);

    LoginUserInfo findUserInfoByAccountAndCompanyID(@Param(value = "CompanyID") String CompanyID, @Param(value =
            "account") String account);

    @Select(value = "exec sp_syncUser N'${CompanyID}',N'${Account}'")
    void SyncUser(String CompanyID, String Account);

    @Select(value = "Select Distinct CompanyID from DTSystem.dbo.AllUsersList")
    List<String> getCompanyList();

    @Delete(value = "Delete from DTSystem.dbo.AllUsersList Where CompanyID=#{CompanyID} And Account=#{Account}")
    int deleteUserByAccount(String Account, String CompanyID);

    List<Map<String, Object>> getAllEmailUser(Map<String, Object> params);
    List<LoginUserInfo> getAll();

    List<LoginUserInfo> findAllUserInfo();
}
