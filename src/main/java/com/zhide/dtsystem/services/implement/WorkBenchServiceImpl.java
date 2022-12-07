package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.RegexUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.WorkBenchMapper;
import com.zhide.dtsystem.models.CacheableTtl;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IWorkBenchService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

@Service
public class WorkBenchServiceImpl implements IWorkBenchService {
    @Autowired
    WorkBenchMapper workBenchMapper;

    @Override
    @CacheableTtl(keyGenerator = "CompanyUserKeyGenerator",ttl=3600,value="getPantentTotalByType")
    public List<Map<String, Object>> getPantentTotalByType() {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        List<String> Types = Arrays.asList("发明专利", "外观设计", "实用新型");
        String roleName = loginUserInfo.getRoleName();
        int userId = loginUserInfo.getUserIdValue();
        List<Map<String, Object>> result = workBenchMapper.getPantentTotalByType(roleName, userId);
        for (int i = 0; i < Types.size(); i++) {
            String Type = Types.get(i);
            Optional<Map<String, Object>> Fx = result.stream().filter(f -> f.get("name").toString().equals(Type))
                    .findFirst();
            if (Fx.isPresent() == false) {
                Map<String, Object> F = new HashMap<>();
                F.put("name", Type);
                F.put("value", 0);
                result.add(F);
            }
        }
        return result;
    }
    @Override
    @CacheableTtl(keyGenerator = "CompanyUserKeyGenerator",ttl=3600,value= "getBuZhengNoticeTotal")
    public List<Map<String, Object>> getBuZhengNoticeTotal() {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        String roleName = loginUserInfo.getRoleName();
        int userId = loginUserInfo.getUserIdValue();
        int depId = Integer.parseInt(loginUserInfo.getDepId());
        List<Map<String, Object>> result = workBenchMapper.getBuZhengNoticeTotal(roleName, depId, userId);
        return result;
    }

    @Override
    @CacheableTtl(keyGenerator = "CompanyUserKeyGenerator",ttl=3600,value= "getBuZhengNoticeTotal")
    public List<Map<String, Object>> getShenChaNoticeTotal() {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        String roleName = loginUserInfo.getRoleName();
        int userId = loginUserInfo.getUserIdValue();
        int depId = Integer.parseInt(loginUserInfo.getDepId());
        List<Map<String, Object>> result = workBenchMapper.getShenChaNoticeTotal(roleName, depId, userId);
        return result;
    }

    @Override
    @CacheableTtl(keyGenerator = "CompanyUserKeyGenerator",ttl=600,value="getLastMonthTZS")
    public List<Map<String, Object>> getLastMonthTZS(String Key, String Type,HttpServletRequest request) throws  Exception {
        Map<String,Object> params=getParams(request);
        return workBenchMapper.getLastMonthTZS(params);
    }
    @Override
    public List<Map<String, Object>> getLastMonthGovFee(HttpServletRequest request) throws  Exception {
        Map<String,Object> params=getParams1(request);
        List<Map<String,Object>> Datas= workBenchMapper.getLastMonthGovFee(params);
        return Datas;
    }

    @Override
    @CacheableTtl(value = "getWorkColumns",ttl=120000)
    public Map<String, List<String>> getWorkColumns(int Year) {
        List<Map<String,Object>> datas=workBenchMapper.getWorkColumns(Year);
        Map<String,List<String>> column=new HashMap<>();
        List<String> Deps=new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            Map<String,Object> data=datas.get(i);
            String DepName=data.get("DepName").toString();
            String TechMan=data.get("TechMan").toString();
            List<String> Mans=new ArrayList<>();
            if(Deps.contains(DepName)==false) Deps.add(DepName);
            if(column.containsKey(DepName)==false){
                Mans.add(TechMan);
                column.put(DepName,Mans);
            } else {
                Mans=(List<String>)column.get(DepName);
                if(Mans.contains(TechMan)==false) Mans.add(TechMan);
                column.replace(DepName,Mans);
            }
        }
        column.put("Deps",Deps);
        return column;
    }

    @Override
    @CacheableTtl(value="getWorkMonths",ttl=180)
    public List<Map<String, Object>> getWorkMonths(int Year) {
        List<Map<String,Object>> rows=new ArrayList<>();
        List<String> TechMans= workBenchMapper.getTechMans(Year);
        if(TechMans.size()>0) {
            Map<String, Object> args = new HashMap<>();
            args.put("Year", Year);
            args.put("Items", TechMans);
            rows = workBenchMapper.getWorkMonths(args);
            rows.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String s1=o1.get("WorkMonth").toString();
                    String s2=o2.get("WorkMonth").toString();
                    Integer X1=GetNum(s1);
                    Integer X2=GetNum(s2);
                    return Integer.compare(X1,X2);
                }
            });
        }
        return rows;
    }
    private Integer GetNum(String s1){
        Integer X1=0;
        if(s1.indexOf("月")>-1) X1=Integer.parseInt(s1.replace("月",""));
        else {
            if(s1.startsWith("X")) X1=13;
            else if(s1.startsWith("Y"))X1=14;
            else if(s1.startsWith("Z"))X1=15;
        }
        return X1;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "asc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "Days";
        Map<String, Object> params = new HashMap<>();
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String Type = request.getParameter("Type");
        params.put("Type",Type);
        String Days=request.getParameter("Days");
        params.put("Days",Integer.parseInt(Days));
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
    private Map<String, Object> getParams1(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "asc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "Days";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageSize * pageIndex - 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String Days=request.getParameter("Days");
        params.put("Days",Integer.parseInt(Days));
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
