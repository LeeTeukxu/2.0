package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.GovFeeMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IGovFeeService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: GovFeeServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月09日 8:31
 **/
@Service
public class GovFeeServiceImpl  implements IGovFeeService {
    @Autowired
    GovFeeMapper govMapper;
    @Autowired
    com.zhide.dtsystem.services.NBBHCode NBBHCode;
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception  {
        Map<String,Object> Parameters=getParameters(request);
        List<Map<String,Object>>Datas=govMapper.getData(Parameters);
        pageObject object=new pageObject();
        object.setData(Datas);
        if(Datas.size()>0){
            for(int i=0;i<Datas.size();i++) {
                Map<String,Object>row=Datas.get(i);
                String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));
                NBBHInfo nbInfo = NBBHCode.Parse(NEIBUBH);
                nbInfo.foreach((type, ids) -> {
                    List<String> names = ids.stream().map(f -> f.getName()).collect(Collectors.toList());
                    if (names.size() > 0) {
                        row.put(type, Strings.join(names, ','));
                        if (type == "KH") row.put("KHID", ids.get(0).getID());
                    }
                });
            }
        }
        int Total =0;
        if(Datas.size()>0)Total= Integer.parseInt(Datas.get(0).get("_TotalNum").toString());
        object.setTotal(Total);
        return object;
    }
    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "QX";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String PayState=request.getParameter("PayState");
        params.put("PayState",PayState.equals("0")?"未缴费":"已缴费");
        String AppNo=request.getParameter("AppNo");
        if(StringUtils.isEmpty(AppNo)==false){
            params.put("AppNo",AppNo);
        }
        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());

        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }
}
