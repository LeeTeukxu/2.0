package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IFeeWatchService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<Map<String, Object>> ImportYearData(String Mode, List<Map<String, Object>> Data) throws Exception;

    List<Map<String, Object>> ImportApplyData(String Mode, List<Map<String, Object>> Data) throws Exception;

    void ChangeValue(List<String> SH, String Code, Integer State) throws Exception;
    void RemoveOne(Integer ID) throws Exception;
}
