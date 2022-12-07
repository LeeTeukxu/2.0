package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface IPatentInfoService {
    Map<String, String> GetLoginUserHash();

    pageObject getData(Map<String, Object> parameters);

    Map<String, Object> getParameters(HttpServletRequest request) throws Exception;

    void ChangeValue(List<String> IDS, String field, Object value) throws Exception;

    File download(String[] Codes) throws Exception;

    File alldownload(String[] Codes) throws Exception;

    boolean ImportData(Map<String,Object> row) throws Exception;

    List<Map<String, Object>> getPantentTotal(int DepID, int UserID, String RoleName);

    void ChangeNBBH(Integer Transfer, Integer Resgination) throws Exception;
    void PatentInfoPermissionChangeUserID(Integer Transfer, Integer Resgination) throws Exception;
}
