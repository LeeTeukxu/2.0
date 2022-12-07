package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface ContractWindowMapper {
    List<Map<String, Object>> getDataWindow(Map<String, Object> params);
}
