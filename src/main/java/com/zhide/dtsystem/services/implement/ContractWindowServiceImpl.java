package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.ContractWindowMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.models.tbInvoiceApplication;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbInvoiceApplicationRepository;
import com.zhide.dtsystem.services.define.IContractWindowService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContractWindowServiceImpl implements IContractWindowService {
    @Autowired
    ContractWindowMapper contractWindowMapper;
    @Autowired
    tbInvoiceApplicationRepository appRepository;

    @Override
    public pageObject getDataWindow(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> datas = contractWindowMapper.getDataWindow(params);
        int total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            List<tbInvoiceApplication> app = appRepository.findAll();
            total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            datas.stream().forEach(f -> {
                Map<String, Object> newRow = eachSingleRow(f, app);
                PP.add(newRow);
            });
            object.setTotal(total);
            object.setData(PP);
        }
        return object;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SN";
        String key = request.getParameter("key");
        String customer = request.getParameter("customer");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if (Strings.isEmpty(key) == false) {
            key = URLDecoder.decode(key, "utf-8");
            params.put("key", "%" + key + "%");
        }
        if (Strings.isEmpty(customer) == false) params.put("customer", customer);
        String Type = request.getParameter("Type");
        if (Strings.isEmpty(Type) == false) params.put("Type", Type);

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            highText = URLDecoder.decode(highText);
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());

        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("UserID", Info.getUserId());
            params.put("UserName", Info.getUserName());
            params.put("RoleID", Info.getRoleId());
            params.put("RoleName", Info.getRoleName());
            params.put("DepID", Info.getDepId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<tbInvoiceApplication> app) {
        row.remove("TotalNum");
        if (app.size() > 0) {
            app.stream().forEach(f -> {
                String contractNo=f.getContractNo();
                if(StringUtils.isEmpty(contractNo)==false){
                    if (f.getContractNo().equals(row.get("ContractNo"))) {
                        row.put("IsInvoice", "已开票");
                    } else row.put("IsInvoice", "未开票");
                } else row.put("IsInvoice", "未开票");
            });
        }else row.put("IsInvoice", "未开票");
        return row;
    }
}
