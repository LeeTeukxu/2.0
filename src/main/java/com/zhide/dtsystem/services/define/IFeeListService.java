package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.feeListName;

import javax.servlet.http.HttpServletRequest;

public interface IFeeListService {
    pageObject getData(HttpServletRequest request) throws Exception;

    pageObject getFeeItemData(HttpServletRequest request) throws Exception;

    boolean Save(feeListName feeListName) throws Exception;

    boolean Remove(String ID) throws Exception;

    boolean AlreadyPay(String ID) throws Exception;
}
