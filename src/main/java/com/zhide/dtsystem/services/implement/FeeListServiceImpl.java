package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.feeListName;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.feeListNameRepository;
import com.zhide.dtsystem.services.define.IFeeListService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class FeeListServiceImpl implements IFeeListService {

    @Autowired
    FeeListMapper feeListMapper;
    @Autowired
    feeListNameRepository feeListRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = feeListMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    @Override
    public pageObject getFeeItemData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams1(request);
        List<Map<String, Object>> datas = feeListMapper.getFeeItemData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    @Override
    public boolean Save(feeListName feeListName) throws Exception {
        feeListName oldOne = feeListRep.findFirstByJfqdId(feeListName.getJfqdId());
        if (oldOne != null) {
            oldOne.setJfqd(feeListName.getJfqd());
            oldOne.setInvoiceTitle(feeListName.getInvoiceTitle());
            oldOne.setAddress(feeListName.getAddress());
            oldOne.setMobile(feeListName.getMobile());
            oldOne.setLinkMan(feeListName.getLinkMan());
            oldOne.setRemark(feeListName.getRemark());
            oldOne.setPostCode(feeListName.getPostCode());
            feeListRep.save(oldOne);
            return true;
        } else return false;
    }

    @Override
    public boolean Remove(String ID) throws Exception {
        feeListName findOne = feeListRep.findFirstByJfqdId(ID);
        if (findOne != null) {
            feeListRep.delete(findOne);


            return true;
        } else return false;
    }

    @Override
    public boolean AlreadyPay(String ID) throws Exception {
        LoginUserInfo LogInfo = CompanyContext.get();
        if (LogInfo == null) throw new Exception("登录失败，请稍候重试。");
        feeListName findOne = feeListRep.findFirstByJfqdId(ID);
        if (findOne != null) {
            findOne.setFstate(true);
            findOne.setDrawTime(new Date());
            findOne.setDrawEmp(LogInfo.getUserName());
            feeListRep.save(findOne);
            return true;
        } else return false;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "yingjiaof_jiaofeijzr";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

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
        return params;
    }

    private Map<String, Object> getParams1(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "yingjiaof_jiaofeijzr";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String jfqd_id = request.getParameter("jfqd_id");
        params.put("id", jfqd_id);

        return params;
    }
}
