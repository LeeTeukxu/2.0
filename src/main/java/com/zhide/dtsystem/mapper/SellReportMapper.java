package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface SellReportMapper {
    List<Map<String, Object>> getData(Map<String, Object> args);

    Integer getTotal(Map<String, Object> args);

    Map<String,Object> getSum(Map<String, Object> args);
}
