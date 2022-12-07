package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface SQPermissionMapper {
    List<String> getIds(Map<String, Object> params);
}
