package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PickUpMapper {
    List<String> getPickUpIdPage(Map<String, Object> params);

    List<Map<String, Object>> getAllDataByIds(Map<String, Object> params);

    int getAllPickUpTotal(Map<String, Object> params);

    List<Map<String, Object>> getData(Map<String, Object> arguments);

    List<Map<String, Object>> getDatas(Map<String, Object> arguments);

    List<Map<String, Object>> getDetailDatas(Map<String, Object> arguments);

    @Select(value = "exec [sp_getPickUpTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getPickUpTotal(int DepID, int UserID, String RoleName);
}
