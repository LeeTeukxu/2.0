package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IWorkBenchService {
    List<Map<String, Object>> getPantentTotalByType();
    List<Map<String, Object>> getBuZhengNoticeTotal();
    List<Map<String, Object>> getShenChaNoticeTotal();
    List<Map<String, Object>> getLastMonthTZS(String Key, String Type,HttpServletRequest request) throws Exception;
    List<Map<String, Object>> getLastMonthGovFee( HttpServletRequest request) throws  Exception;
    Map<String, List<String>> getWorkColumns(int Year) ;
    List<Map<String,Object>> getWorkMonths(int Year);
}
