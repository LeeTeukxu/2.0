package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;

public interface ITradeCasesCompleteService {
    public pageObject getData(HttpServletRequest request) throws Exception;

    boolean Commit(String CasesID, boolean complete) throws Exception;
}
