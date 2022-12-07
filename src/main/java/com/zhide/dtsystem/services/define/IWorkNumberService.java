package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IWorkNumberService {
    List<Map<String,Object>> getTZS(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getCPC(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getAddFee(HttpServletRequest request) throws Exception;
    List<Map<String,Object>> getPantent(HttpServletRequest request) throws Exception;
}
