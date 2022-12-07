package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;

public interface IFeeWaitDetailService {
    pageObject getData(HttpServletRequest request) throws Exception;
}
