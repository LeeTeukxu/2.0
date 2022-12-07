package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.companyConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyConfigMapper {
    @Select(value = "Select * from Client_${companyID}.dbo.CompanyConfig")
    List<companyConfig> getListByCompanyId(String companyID);
}
