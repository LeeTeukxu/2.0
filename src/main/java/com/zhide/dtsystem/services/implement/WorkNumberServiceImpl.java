package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.WorkNumberMapper;
import com.zhide.dtsystem.models.CacheableTtl;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IWorkNumberService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkNumberServiceImpl implements IWorkNumberService {
    @Autowired
    WorkNumberMapper workMapper;

    @Override
    public List<Map<String,Object>> getTZS(HttpServletRequest request) throws Exception {
        Map<String, Object> arguments = getParams(request);
        List<Map<String, Object>> datas = workMapper.getTZS(arguments);
        return datas;
    }

    @Override
    public List<Map<String,Object>>  getCPC(HttpServletRequest request) throws Exception {
        Map<String, Object> arguments = getParams(request);
        List<Map<String, Object>> datas = workMapper.getCPC(arguments);
        return datas;
    }

    @Override
    public List<Map<String,Object>>  getAddFee(HttpServletRequest request) throws Exception {
        Map<String, Object> arguments = getParams(request);
        List<Map<String, Object>> datas = workMapper.getAddFee(arguments);
        return datas;
    }

    @Override
    public List<Map<String,Object>>  getPantent(HttpServletRequest request) throws Exception {
        Map<String, Object> arguments = getParams(request);
        List<Map<String, Object>> datas = workMapper.getPantent(arguments);
        return datas;
    }

    public Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (StringUtils.isEmpty(sortOrder)) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (StringUtils.isEmpty(sortField)) sortField = "TUploadTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String Date = request.getParameter("Date");
        if (StringUtils.isEmpty(Date)) Date = "Now";
        if (Date.equals("Now")) {
            params.put("Date", simple.format(new Date()));
            //params.put("Date","2021-06-04");
        } else if (Date.equals("Pre")) {
            Date T = new Date();
            Date Pre = DateUtils.addDays(T, -1);
            params.put("Date", simple.format(Pre));
            //params.put("Date","2021-06-03");
        }
        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }
}
