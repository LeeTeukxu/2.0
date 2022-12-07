package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.HighBillObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CaseHighAllBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    @Select("Select * from View_CaseHighAllBrowse Where CasesID=#{CasesID}")
    HighBillObject  getBillObject(String CasesID);
}
