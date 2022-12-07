package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.v_PantentInfoMemo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CasesMemoMapper {
    @Select(value = "Select * from v_CasesMemo Where CasesID=#{CasesID}")
    List<Map<String, Object>> getData(String CasesID);

    List<v_PantentInfoMemo> getAllByIds(List<String> ids);
}
