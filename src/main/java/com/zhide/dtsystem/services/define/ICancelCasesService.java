package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.cancelCasesMain;
import com.zhide.dtsystem.models.cancelCasesSub;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICancelCasesService {
    pageObject getCasesMain(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getAllSubs(String CasesID);
    void SaveAll(cancelCasesMain main,List<cancelCasesSub> Subs) throws Exception;
    pageObject getMain(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getCancelCasesTotal();
    pageObject getSub(Integer MainID);
    void AuditOne(Integer ID,Integer Result,String Memo) throws Exception;
    void SetOne(Integer ID,Integer Result,String Memo) throws Exception;
    void Remove(Integer  ID) throws Exception;
}
