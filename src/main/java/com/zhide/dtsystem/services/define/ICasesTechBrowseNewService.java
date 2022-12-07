package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ICasesTechBrowseNewService {
    pageObject getData(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getWaitReport(int State,String Type,HttpServletRequest request) throws Exception;

    List<Map<String,Object>> getClientReport(String Type, HttpServletRequest request) throws Exception;

    void changeToHistoryAttachment(String SubID);

    void ChangeShenqingName(String SubID, String NewShenqingName);

    void acceptTechTask(String SubIDS, String AuditMan,String TechMan) throws Exception;

    void rejectTechTask(String SubIDS) throws Exception;

    void CommitTechFiles(String SubIDS, String Memo) throws Exception;

    void AuditTechFiles(int Result, String SubIDS, String Memo) throws Exception;

    void SetTechFiles(int Result, String Memo, String SubIDS) throws Exception;

    void SBTechFiles(int Result, String Memo, String SubIDS) throws Exception;

    void AuditSettle(int Result, String Memo, String SubIDS) throws Exception;

    void ChangeLXText(String SubID, String NewLXText);
    void ChangeSupportMan(String SubID,String SupportMan);
    void ChangeTechMan(int OldMan,int NewMan,String SubID) throws Exception;
    List<Map<String,Object>> getDynamicColumns(String type,int State);

    void ChangeJGDate(String ID, String Field, Date Value);
    void ChangeRequiredDate(String ID, Date Value);
    List<Map<String,Object>>getWorkDays(Map<String,Object> params);
    void CaseSubIDChange(Integer Transfer, Integer Resignation) throws Exception;
}
