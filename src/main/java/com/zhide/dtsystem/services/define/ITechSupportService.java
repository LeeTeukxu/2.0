package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.techSupportMain;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ITechSupportService {
    techSupportMain SaveAll(Map<String,Object> Data) throws Exception;
    pageObject getData(HttpServletRequest request) throws  Exception;
    pageObject getFiles(HttpServletRequest request) throws  Exception;
    boolean Commit(int ID, String Result, String ResultText) throws Exception;
    void DeleteTechFile(String CasesID,String AttID) throws  Exception;
    void CheckFile(String SubID,String AttID) throws Exception;
    void UnCheckFile(String SubID,String AttID) throws Exception;
    void DeleteSupport(String CasesID) throws Exception;
}
