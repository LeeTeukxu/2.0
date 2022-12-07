package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArrivalUseDetailMapper {
    List<Map<String,Object>> getMain(Map<String,Object> params);
    List<Map<String,Object>> getSub(Map<String,Object> params);
}
