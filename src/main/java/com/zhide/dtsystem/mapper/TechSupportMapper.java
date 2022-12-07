package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TechSupportMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    List<Map<String,Object>> getFiles(Map<String,Object> params);
    @Select(value = "exec [sp_getTechSupportTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String,Object>> getStateNumber(int DepID,int UserID,String RoleName);
}
