package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

public interface CasesNewBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);
    List<Map<String,Object>> getSelectMoney(String CasesID);
    List<Map<String,Object>> getRefundMoney(String DocSN);
}
