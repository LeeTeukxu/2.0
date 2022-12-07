package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.ComboboxItem;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface INoticeService {
    pageObject getData(String Type, Map<String, Object> arguments);
    List<Map<String,Object>> getReportData(HttpServletRequest request) throws  Exception;
    Map<String, Object> getParams(HttpServletRequest request) throws Exception;
    List<ComboboxItem> getTZSStatus();
    File download(String[] Codes) throws Exception;
    File downloadOriginal(String[] Codes) throws Exception;
    File downloadDBFile(String Codes) throws Exception;
    byte[] downloadPdf(String[] Codes) throws Exception;
}
