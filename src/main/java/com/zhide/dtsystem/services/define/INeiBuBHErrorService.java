package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.NBBHInfo;

import javax.servlet.http.HttpServletRequest;

public interface INeiBuBHErrorService {
    pageObject getData(HttpServletRequest request) throws Exception;

    Integer UpdateAll(String Old, String Now) throws Exception;
    void SaveAll(String Shenqingh, NBBHInfo getInfo);
}
