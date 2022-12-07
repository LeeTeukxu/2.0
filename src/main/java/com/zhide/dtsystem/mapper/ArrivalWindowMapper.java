package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArrivalWindowMapper {
    List<Map<String, Object>> getDataWindow(Map<String, Object> params);
}
