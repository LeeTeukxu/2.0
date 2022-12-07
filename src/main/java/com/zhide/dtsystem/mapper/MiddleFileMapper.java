package com.zhide.dtsystem.mapper;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiddleFileMapper {
    List<String> getAllByType(@Param(value = "type") String type, @Param(value = "IDS") List<String> IDS);
}
