package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface OutTechMainBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> args);

    List<Map<Integer, Integer>> getStateNumbers(String RoleName,Integer UserID);
}
