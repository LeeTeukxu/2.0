package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FinancialInitalMapper;
import com.zhide.dtsystem.models.FinanChangeRecord;
import com.zhide.dtsystem.models.FinancialInitial;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.FinancialInitialRepository;
import com.zhide.dtsystem.repositorys.tbFormDesignRepository;
import com.zhide.dtsystem.services.define.IFinancialInitalService;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class FinancialInitialServiceImpl implements IFinancialInitalService {
    @Autowired
    FinancialInitalMapper financialInitalMapper;
    @Autowired
    FinancialInitialRepository financialInitialRepository;
    @Autowired
    tbFormDesignRepository tbFormDesignRepository;
    @Autowired
    ItbDictDataService dictService;
    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = financialInitalMapper.getData(params);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FinancialInitial Save(@Param(value = "Data") FinancialInitial financialInitial, String Text,
            String CommitType) throws Exception {
        if (financialInitial == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        if (financialInitial.getFinancialInitialId() == null) {
            financialInitial.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            financialInitial.setAddTime(new Date());
            if (financialInitial.getInitialState() == 1) {
                financialInitial.setOfficalFeeAmount("-" + financialInitial.getOfficalFeeAmount());
                financialInitial.setAgencyFeeAmount("-" + financialInitial.getAgencyFeeAmount());
            }
        } else {
            Optional<FinancialInitial> find =
                    financialInitialRepository.findById(financialInitial.getFinancialInitialId());
            if (find.isPresent()) {
                if (financialInitial.getInitialState() == 1) {
                    financialInitial.setOfficalFeeAmount("-" + financialInitial.getOfficalFeeAmount());
                    financialInitial.setAgencyFeeAmount("-" + financialInitial.getAgencyFeeAmount());
                }
                EntityHelper.copyObject(financialInitial, find.get());
            } else
                throw new Exception("数据异常:" + Integer.toString(financialInitial.getFinancialInitialId()) + "无法获取到数据!");
        }
        FinancialInitial f = financialInitialRepository.save(financialInitial);
        if (Text != "") {
            CreateChangeRecord(CommitType, Text, f);
        }
        return f;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            financialInitialRepository.deleteById(id);
        }
        return false;
    }

    private FinanChangeRecord CreateChangeRecord(String Mode, String Text, FinancialInitial financialInitial) {
        LoginUserInfo info = CompanyContext.get();
        FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
        finanChangeRecord.setPid(financialInitial.getFinancialInitialId());
        finanChangeRecord.setChangeText(Text);
        finanChangeRecord.setMode(Mode);
        finanChangeRecord.setModuleName("FinancialInitial");
        finanChangeRecord.setUserId(Integer.parseInt(info.getUserId()));
        finanChangeRecord.setCreateTime(new Date());
        return finanChangeRecordRepository.save(finanChangeRecord);
    }
}
