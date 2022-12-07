package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.tbOriginalKd;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IOriginalService {
    pageObject getData(HttpServletRequest request) throws Exception;

    successResult Register(HttpServletRequest request) throws Exception;

    successResult RegisterShouju(HttpServletRequest request) throws Exception;

    successResult GetToken(HttpServletRequest request) throws Exception;

    int SaveExpress(tbOriginalKd originalKd, String Dnum) throws Exception;

    int UpdateYJZT(String PickUpNumber, String Dnum, String PackageNum) throws Exception;

    boolean remove(List<Integer> ids);
}
