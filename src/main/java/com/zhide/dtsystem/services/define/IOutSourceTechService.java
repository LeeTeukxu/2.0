package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.casesOutSourceFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOutSourceTechService {
    boolean AddOutSourceTask(List<String> IDS, Integer OutTechMan);

    pageObject getData(HttpServletRequest request) throws Exception;

    void AcceptTech(List<String> SubIDS);

    void RejectTech(List<String> SubIDS);

    List<casesOutSourceFile> GetSubFile(String SubID, String Type);

    boolean SaveSubFiles(String SubID, String OutID, String AttID, String Type) throws Exception;

    boolean RemoveSubFile(String AttID) throws Exception;

    boolean CommitTech(String SubID);

    boolean AuditTechFiles(List<String> SubID, String Text, String Result) throws Exception;

    Map<Integer, Integer> GetStateNumber();

    boolean CancelOutTech(List<String> IDS);

    void ChangeJS(List<String> SubIDs,Integer NowMan) throws Exception;
}
