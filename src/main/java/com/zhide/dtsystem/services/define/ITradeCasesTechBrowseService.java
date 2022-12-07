package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ITradeCasesTechBrowseService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<Map<String, Object>> getAllFreeNums(String CasesID);

    void acceptTechTask(String CASESIDS,String TechMan) throws Exception;

    void SBTechFiles(int Result, String Memo, String SubIDS) throws Exception;

    void ChangeTcName(String SubID, String NewShenqingName);

    void ChangeTcCategory(String SubID, String NewShenqingName);
}
