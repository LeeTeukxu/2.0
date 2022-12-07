package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.View_TZS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ViewTZSMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);
    List<Map<String,Object>> getReportData(Map<String,Object> args);
    @Select(value = "Select Top 20 * from View_TZS Where NEIBUBH is not null Order by SQR")
    List<View_TZS> Get();

    @Select(value = "SELECT distinct ZSTATUS FROM View_TZS Where len(isnull(ZSTATUS,''))>0")
    List<String> getTZSStatus();
    @Select(value = "exec [sp_getTZSTotal] ${DepID},${UserID},'${RoleName}','${Type}'")
    List<Map<String, Object>> getTZSTotal(int DepID, int UserID, String RoleName,String Type);

    List<Map<String,Object>> getQuickData(Map<String,Object> args);
    int getQuickCount(Map<String,Object> args);
    List<Map<String,Object>>getAllEmptyApplyMans();
}
