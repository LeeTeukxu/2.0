package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.tradeCases;
import com.zhide.dtsystem.models.ywCreateInfo;

import java.util.Map;

public interface ITradeCaseService {
    tradeCases SaveAll(Map<String, Object> Data) throws Exception;

    boolean Commit(int ID, String Result, String ResultText) throws Exception;

    boolean Remove(String CasesID) throws Exception;

    boolean createYwRecord(ywCreateInfo info);

    void TradeCaseUserIDChange(Integer Transfer, Integer Resignation) throws Exception;
}
