package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface FeeItemMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select(value = "Select Distinct yingjiaofydm from t_3")
    List<String> getFeeItems();

    @Select(value = "SELECT distinct [zhuanlilxText] FROM [View_FeeItem]")
    List<String> getZLItems();

    @Select(value = "exec sp_getFlowCode '${Type}'")
    String getFlowCode(@Param(value = "Type") String Type);

    @Select(value = "exec [sp_getYearTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getYearTotal(int DepID, int UserID, String RoleName);

    @Select(value = "exec [sp_getApplyTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getApplyTotal(int DepID, int UserID, String RoleName);

    @Delete(value = "Delete from FlowCode Where Type=#{Type} And Num=#{Num} And convert(varchar(6),CreateTime,112)" +
            "=#{YearMonth}")
    public int deleteFlowCode(String Type, Integer Num, String YearMonth);
}