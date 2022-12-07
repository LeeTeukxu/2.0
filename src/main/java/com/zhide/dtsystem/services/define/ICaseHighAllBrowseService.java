package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.caseHighMain;
import com.zhide.dtsystem.viewModel.HighBillObject;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ICaseHighAllBrowseService {
    pageObject getData(HttpServletRequest request) throws Exception;

    caseHighMain SaveAll(Map<String, Object> Datas) throws Exception;

    caseHighMain Commit(int ID, String Result, String ResultText) throws Exception;

    boolean ChangeAllMoney(String CasesID, Double AllMoney);

    boolean SaveMainMemo(String CasesID, String Data) throws Exception;

    boolean SaveSubMemo(String CasesID, String Data) throws Exception;

    boolean RemoveAll(String CasesID) throws Exception;
    void SaveSelectMoney(SelectMoneyInfo Info) throws Exception;
    HighBillObject getBillObject(String CasesID) throws Exception;
    void ReAudit(String CasesID) throws  Exception;

    void CaseHighMainIDChange(Integer Transfer, Integer Resignation) throws Exception;
}

