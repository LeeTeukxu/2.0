package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICasesTechBrowseService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<Map<String, Object>> getAllFreeNums(String CasesID);

}
