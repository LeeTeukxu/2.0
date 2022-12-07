package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.OtherBillObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TradeCasesAllBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    @Select(value="Select * from View_TradeCasesBrowseAll Where CasesID=#{CasesID}")
    OtherBillObject getBillObject(String CasesID);
}
