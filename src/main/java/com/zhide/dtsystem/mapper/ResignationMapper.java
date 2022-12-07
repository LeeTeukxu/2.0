package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResignationMapper {

    List<Map<String, Object>> getData(Map<String, Object> arguments);
}
