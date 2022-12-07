package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface IPickUpService {
    pageObject getData(HttpServletRequest request) throws Exception;

    pageObject getDatas(HttpServletRequest request) throws Exception;

    pageObject getDetailDatas(HttpServletRequest request) throws Exception;

    int AlreadyPickUp(String PickUp, Date PickUpTime, String PickUpNumber) throws Exception;

    int PickUpNo(String Dnum) throws Exception;

    int UpdatePickUpStatusForDZQ(String DrawNo) throws Exception;
}
