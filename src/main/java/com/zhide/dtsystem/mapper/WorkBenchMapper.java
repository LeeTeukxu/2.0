package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface WorkBenchMapper {
    List<Map<String, Object>> getPantentTotalByType(String roleName, int userID);
    List<Map<String, Object>> getBuZhengNoticeTotal(String roleName, int depID, int userID);
    List<Map<String, Object>> getShenChaNoticeTotal(String roleName, int depID, int userID);
    List<Map<String,Object>> getLastMonthTZS(Map<String,Object> argums);
    List<Map<String,Object>> getLastMonthGovFee(Map<String,Object> argums);
    @Select(value = "Select DepName,TechMan from View_WorkMonths a inner join tbDepList b on a.DepName=b.Name  " +
            "Where WorkYear=#{Year} Order by b.SN")
    List<Map<String,Object>> getWorkColumns(int Year);
    @Select(value="Select Distinct TechMan from View_WorkMonths Where WorkYear=#{Year}")
    List<String> getTechMans(int Year);
    List<Map<String,Object>> getWorkMonths(Map<String,Object> args);
    @Select(value="Select Count(0) from CasesMain")
    int getCaseMainNum();
}
