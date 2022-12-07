package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CancelCasesMapper {
    List<Map<String, Object>> getSubsByCasesId(String CasesID);
    List<Map<String,Object>> getMain(Map<String,Object> args);
    List<Map<String,Object>> getCasesMain(Map<String,Object> args);
    @Select(value = "exec [sp_getCancelCasesTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String, Object>> getCancelCasesTotal(int DepID, int UserID, String RoleName);
    List<Map<String,Object>> getSub(Integer MainID);
}
