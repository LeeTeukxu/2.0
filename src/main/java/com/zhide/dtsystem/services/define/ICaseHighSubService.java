package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.caseHighMain;
import com.zhide.dtsystem.models.caseHighSub;
import com.zhide.dtsystem.models.highCreateInfo;

import java.util.List;

/**
 * @ClassName: ICaseHighSubService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月24日 17:37
 **/
public interface ICaseHighSubService {
    caseHighMain AddAJRecord(highCreateInfo info) throws Exception;

    List<caseHighSub> getSubRecords(String CasesID);

    caseHighMain RemoveSubs(List<String> IDS) throws Exception;

    caseHighMain SaveSubs(List<caseHighSub> Subs) throws Exception;

    List<String> getSubFiles(String SubID, String Type);

    boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type);

    boolean RemoveSubFiles(String CasesID, String AttID);

    void CaseHighSubIDChange(Integer Transfer, Integer Resignation) throws Exception;
}
