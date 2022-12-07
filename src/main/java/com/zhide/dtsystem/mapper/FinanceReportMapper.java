package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface FinanceReportMapper {
     List<Map<String,Object>> getData(Map<String,Object> args);
     Integer getTotal(Map<String,Object> args);
    Double getSum(Map<String,Object> args);
}
