package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;

public interface IFymxWindowService {
    pageObject getDataWindow(HttpServletRequest request) throws Exception;
}
