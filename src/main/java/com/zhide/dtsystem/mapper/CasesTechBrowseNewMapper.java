package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.CasesDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CasesTechBrowseNewMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    @Select(value="Select * from View_CasesTechBrowse Where CasesID=#{CasesID}")
    List<CasesDetail> getDetails(String CasesID);
    List<Map<String,Object>>getWaitReport(Map<String,Object> params);
    List<Map<String,Object>>getWaitDetail(Map<String,Object> params);
    List<Map<String,Object>>getOutTechDetail(Map<String,Object> params);
    List<Map<String,Object>>getClientReport(Map<String,Object> params);
    List<String> getReportColumns(Map<String,Object> params);
    List<Map<String,Object>> getWorkDays(Map<String,Object> params);
}
