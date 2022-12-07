package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.casesSubFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PantentInfoMapper {
    @Select(value = "Select top 20 b.ANJUANBH from PantentInfo  a inner join aj b on a.SHENQINGBH=b.SHENQINGBH  " +
            "where Len(isnull(a.UploadPath,''))=0 and b.ANJUANBH not in (Select CPCID from NotExistCPC) order by " +
            "b.CHUANGJIANRQ  desc")
    public List<String> getNotUploadCPC();

    @Select(value = "exec [sp_getFeeItemTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getFeeItemTotal(int DepID, int UserID, String RoleName);
}
