package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface FeeListMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    List<Map<String, Object>> getFeeItemData(Map<String, Object> arguments);
}
