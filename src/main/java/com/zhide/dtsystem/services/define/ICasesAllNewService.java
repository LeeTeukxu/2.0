package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.viewModel.CasesBillObject;

import javax.servlet.http.HttpServletRequest;

public interface ICasesAllNewService {
    pageObject getData(HttpServletRequest request) throws Exception;
    CasesBillObject getBillObject(String CasesID) throws Exception;
}

