package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.CasesBillObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CasesAllNewBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    @Select(value="Select * from View_CasesAllNewBrowse Where CasesID=#{CasesID}")
    CasesBillObject getBillObject(String CasesID);
}
