package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ViewPatentInfoMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);

    List<Map<String, Object>> getWatchData(Map<String, Object> params);

    @Select(value = "exec [sp_getPantentTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String, Object>> getPantentTotal(int DepID, int UserID, String RoleName);

    List<Map<String,Object>> getSubFileAndOutFile(Map<String, Object> param);
}
