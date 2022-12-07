package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.caseCounterInfo;

import java.util.List;

public interface caseHighCounterMapper {
    List<caseCounterInfo> getMain();

    List<caseCounterInfo> getSub();
}
