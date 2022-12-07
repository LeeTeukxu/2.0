package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.caseCounterInfo;

import java.util.List;
import java.util.Map;

public interface tradeCasesCounterMapper {
    List<caseCounterInfo> getMain();

    List<caseCounterInfo> getSub(Map<String, Object> params);

    List<caseCounterInfo> getTradeCases(Map<String,Object> params);
}
