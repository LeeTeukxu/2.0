package com.zhide.dtsystem.controllers.systems;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.ViewClientMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IClientInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/systems/client")
public class ClientController {
    @Autowired
    ViewClientMapper clientMapper;
    @Autowired
    AllUserListMapper userListMapper;
    @Autowired
    IClientInfoService clientService;

    @RequestMapping("/query")
    public String Query() {
        return "/systems/client/query";
    }

    @RequestMapping("/queryAll")
    public String QueryAll(String KHID,Map<String,Object> model) {
        model.put("KHID", StringUtils.isEmpty(KHID)?"":KHID);
        return "/systems/client/queryAll";
    }

    @ResponseBody
    @RequestMapping("/getAll")
    public pageObject getAll(HttpServletRequest request) {
        Map<String, Object> param = getParams(request);
        pageObject result = new pageObject();
        List<Map<String, Object>> datas = clientMapper.getDataHasEmail(param);
        int Total = 0;
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            datas.stream().forEach(f -> {
                f.remove("_TotalNum");
            });
        }
        result.setData(datas);
        result.setTotal(Total);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllEmailUser")
    public pageObject getAllEmailUser(HttpServletRequest request) {
        Map<String, Object> param = getParams1(request);
        pageObject result = new pageObject();
        List<Map<String, Object>> datas = userListMapper.getAllEmailUser(param);
        int Total = 0;
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            datas.stream().forEach(f -> {
                f.remove("_TotalNum");
            });
        }
        result.setData(datas);
        result.setTotal(Total);
        return result;
    }

    @RequestMapping("/getClientTree")
    public @ResponseBody
    List<TreeListItem> getClientTree() {
        List<TreeListItem> result=new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            result=clientService.getClientTree(Info.getUserIdValue(),Info.getDepIdValue(),Info.getRoleName());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return result;
    }

    @RequestMapping("/getAllClientTree")
    public @ResponseBody
    List<TreeListItem> getAllClientTree() {
        List<TreeListItem> result=new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            result=clientService.getAllClientTree(Info.getUserIdValue(),Info.getDepIdValue(),Info.getRoleName());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return result;
    }

    private Map<String, Object> getParams(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "ClientName";
        String Name = request.getParameter("Name");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if (Strings.isEmpty(Name) == false) {
            params.put("Name", "%" + Name + "%");
        }
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");


        String KHID=request.getParameter("KHID");
        if(StringUtils.isEmpty(KHID)==false){
            List<String> KHIDS=Arrays.asList(KHID.split(",")).stream().collect(Collectors.toList());
            if(KHIDS.size()>0) {
                params.put("KHID", KHIDS);
                params.replace("RoleName", "系统管理员");
            }
        }
        return params;
    }

    private Map<String, Object> getParams1(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "EmpName";
        String Name = request.getParameter("Name");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if (Strings.isEmpty(Name) == false) {
            params.put("EmpName", "%" + Name + "%");
        }
        LoginUserInfo Info = CompanyContext.get();
        if (Info == null) throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }
}
