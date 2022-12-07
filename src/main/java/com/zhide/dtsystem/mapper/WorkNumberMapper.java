package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkNumberMapper {
    List<Map<String, Object>> getTZS(Map<String, Object> params);
    List<Map<String, Object>> getCPC(Map<String, Object> params);
    List<Map<String, Object>> getAddFee(Map<String, Object> params);
    List<Map<String,Object>> getPantent(Map<String,Object> params);
}
