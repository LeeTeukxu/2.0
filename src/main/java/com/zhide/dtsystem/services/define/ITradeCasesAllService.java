package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.viewModel.OtherBillObject;

import javax.servlet.http.HttpServletRequest;

public interface ITradeCasesAllService {
    pageObject getData(HttpServletRequest request) throws Exception;
    OtherBillObject getBillObject(String CasesID);
}

