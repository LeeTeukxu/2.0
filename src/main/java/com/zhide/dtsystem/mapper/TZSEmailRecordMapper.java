package com.zhide.dtsystem.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TZSEmailRecordMapper {
    List<Map<String, Object>> getAll(List<String> IDS);
}
