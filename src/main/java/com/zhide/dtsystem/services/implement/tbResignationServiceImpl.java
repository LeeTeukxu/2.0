package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.ResignationMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.models.tbResignationRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbResignationRepository;
import com.zhide.dtsystem.services.Resignation.AllResgination;
import com.zhide.dtsystem.services.Resignation.ClientFollowResignation;
import com.zhide.dtsystem.services.Resignation.ClientInfoResignation;
import com.zhide.dtsystem.services.define.ItbResignationService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class tbResignationServiceImpl implements ItbResignationService {
    @Autowired
    ResignationMapper resignationMapper;
    @Autowired
    tbResignationRepository resignationRepository;
    @Autowired
    AllResgination resgination;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = resignationMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "AddTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
            params.put("DepID",Info.getDepId());

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public tbResignationRecord Save (tbResignationRecord resignationRecord) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Date nowTime = new Date();
        if (Info == null) throw new Exception("登陆失效，请重新登陆！");
        if (resignationRecord.getResignationRecordId() != null) {
            Optional<tbResignationRecord> findOne = resignationRepository.findById(resignationRecord.getResignationRecordId());
            if (findOne.isPresent()) {
                EntityHelper.copyObject(resignationRecord,findOne.get());
            }else {
                resignationRecord.setCreateTime(nowTime);
                resignationRecord.setCreateMan(Info.getUserIdValue());
            }
        }else {
            resignationRecord.setCreateTime(nowTime);
            resignationRecord.setCreateMan(Info.getUserIdValue());
        }
        tbResignationRecord record = resignationRepository.save(resignationRecord);

        resgination.AllResgination(resignationRecord.getTransfer(), resignationRecord.getResignation());

        return record;
    }
}
