package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface ViewClientMapper {
    List<Map<String, Object>> getDataHasEmail(Map<String, Object> arguments);
}
