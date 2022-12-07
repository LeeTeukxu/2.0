package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ajCreateInfo;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;

import java.util.Map;

public interface ICaseNewService {
    casesMain SaveAll(Map<String, Object> Data) throws Exception;

    boolean Commit(int ID, String Result, String ResultText) throws Exception;

    boolean Remove(String CasesID) throws Exception;

    boolean createAJRecord(ajCreateInfo info);

    boolean Complete(String CasesID, int State) throws Exception;
    void SaveSelectMoney(SelectMoneyInfo Info) throws Exception;
    void ReAudit(String CasesID) throws  Exception;

    void CaseMainIDChange(Integer Transfer, Integer Resignation) throws Exception;
}
