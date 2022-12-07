package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

public interface UploadUserMapper {
    @Select(value = "Select Count(0) from Client_${companyID}.dbo.UploadUser Where Account=#{account}")
    boolean IsCPCUser(String companyID, String account);
}
