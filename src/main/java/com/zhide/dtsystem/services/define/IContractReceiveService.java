package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.contractReceive;

import javax.servlet.http.HttpServletRequest;

public interface IContractReceiveService {
    boolean Save(contractReceive obj) throws Exception;

    pageObject getData(HttpServletRequest request) throws Exception;

    boolean Remove(Integer ID) throws Exception;

    void  ChangeDrawEmp(String ID, String Value);
}
