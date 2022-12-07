package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface AllNoticeMapper {
    List<Map<String, Object>> getAllNoticeIdPage(Map<String, Object> params);

    List<Map<String, Object>> getAllDataByIds(Map<String, Object> params);

    int getAllNoticeTotal(Map<String, Object> params);
}
