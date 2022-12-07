package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AddYearWatchItemMapper {
    List<Map<String, Object>> getData(Map<String, Object> paramaters);

    @Select(value = "Select top 200 * from View_AddYearWatch Where Shenqingh in(Select Distinct shenqingh from " +
            "tbFeeItem  Where Type='Year') Order by FAWENRQ asc")
    List<Map<String, Object>> getWaitList();
}
