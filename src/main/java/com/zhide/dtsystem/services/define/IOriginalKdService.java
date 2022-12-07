package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbOriginalKd;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface IOriginalKdService {
    public pageObject getData(HttpServletRequest request) throws Exception;

    int ExpressAlready(String Render, Date DeliveryTime, List<String> PackageNum) throws Exception;

    int ExpressNot(List<String> PackageNum) throws Exception;

    tbOriginalKd SaveExpress(tbOriginalKd originalKd) throws Exception;

    int OriginalKdNo(List<String> Dnum, String IsSelectAll, List<String> PackageNum) throws Exception;
}
