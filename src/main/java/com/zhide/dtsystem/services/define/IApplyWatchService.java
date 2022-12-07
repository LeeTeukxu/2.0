package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IApplyWatchService {
    pageObject getAddApplyWatchItem(HttpServletRequest request) throws Exception;

    List<String> getTZSPaths(String tzsbh) throws Exception;

    boolean saveAll(List<Map<String, Object>> Datas) throws Exception;

    pageObject getData(HttpServletRequest request) throws Exception;

    boolean removeAll(List<String> IDS) throws Exception;
}
