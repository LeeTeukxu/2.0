package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface CaseHighTechBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    List<Map<String, Object>> getWaitReport(Map<String, Object> params);
}
