package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.ComboboxItem;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface INiCheNoticeService {
    pageObject getData(Map<String, Object> arguments);

    Map<String, Object> getParams(HttpServletRequest request) throws Exception;

    List<ComboboxItem> getTZSStatus();

    File download(String[] Codes) throws Exception;

    File downloadOriginal(String[] Codes) throws Exception;
}
