package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.CasesDetail;
import org.apache.ibatis.annotations.Select;
import org.assertj.core.internal.Dates;

import java.util.List;
import java.util.Map;

public interface ColumnChartMapper {
    List<Map<String, Object>>getName(Map<String,Object> params);
    List<String> getReportColumns(Map<String,Object> params);
    List<Map<String,Object>>getWaitDetail(Map<String,Object> params);
}
