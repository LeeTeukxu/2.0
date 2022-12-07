package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.caseHighSub;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICaseHighTechBrowseService {
    pageObject getData(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getWaitReport(String Type,HttpServletRequest request) throws Exception;
    void updateCasesSubList(List<caseHighSub> subs);

    void changeToHistoryAttachment(String SubID);

    void acceptTechTask(List<String> IDS, String AuditMan,String TechMan) throws Exception;

    void rejectTechTask(List<String> IDS) throws Exception;

    void AuditTechFiles(List<String> IDS, int Result, String Memo) throws Exception;

    void CommitTechFiles(List<String> IDS, String Memo) throws Exception;

    void SetTechFiles(List<String> IDS, int Result, String Memo) throws Exception;

    void SBTechFiles(List<String> IDS, int Result, String Memo) throws Exception;

    void AuditSettle(List<String> IDS, int Result, String Memo) throws Exception;
    void ChangeLXText(String SubID, String NewLXText);
    void ChangeSupportMan(String SubID,String SupportMan);
    void ChangeTechMan(int OldMan,int NewMan,String SubID) throws Exception;
}
