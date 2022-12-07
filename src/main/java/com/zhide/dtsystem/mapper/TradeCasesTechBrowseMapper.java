package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.viewModel.OtherDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TradeCasesTechBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    @Select(value="Select * from View_TradeCasesTechBrowse Where CasesID=#{CasesID}")
    List<OtherDetail> getDetails(String CasesID);
}
