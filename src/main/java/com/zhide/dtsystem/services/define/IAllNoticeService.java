package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbMenuList;
import com.zhide.dtsystem.models.tzsPeriodConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IAllNoticeService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<Map<String, Object>> getTZSSendTypeData() throws Exception;

    boolean SaveAll(List<String> data);
}
