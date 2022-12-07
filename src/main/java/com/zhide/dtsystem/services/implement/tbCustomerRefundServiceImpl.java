package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.tbCustomerRefundMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbCustomerRefundRepository;
import com.zhide.dtsystem.services.define.ItbCustomerRefundService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class tbCustomerRefundServiceImpl implements ItbCustomerRefundService {

    @Autowired
    tbCustomerRefundMapper customerRefundMapper;

    @Autowired
    tbCustomerRefundRepository customerRefundRepository;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = customerRefundMapper.getData(params);
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
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbCustomerRefund Save(@Param(value = "Data") tbCustomerRefund customerRefund,
            tbArrivalRegistration arrivalRegistration, String Text, String CommitType) throws Exception {
        if (customerRefund == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date nowTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        if (customerRefund.getCustomerRefundRequestId() == null) {
            if (arrivalRegistration.getDocumentNumber() != null) {
                customerRefund.setDocumentNumber(arrivalRegistration.getDocumentNumber());
            }
            customerRefund.setRefundRequestNumber(sdt.append(dt.format(nowTime)).toString());
            customerRefund.setApplicant(Integer.parseInt(loginUserInfo.getUserId()));
            customerRefund.setApproverResult(0);
            customerRefund.setAuditResult(0);
            customerRefund.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            customerRefund.setAddTime(new Date());
        } else {
            customerRefund.setDocumentNumber(arrivalRegistration.getDocumentNumber());
            customerRefund.setApproverResult(0);
            customerRefund.setAuditResult(0);
            Optional<tbCustomerRefund> fCustomerRefund =
                    customerRefundRepository.findById(customerRefund.getCustomerRefundRequestId());
            if (fCustomerRefund.isPresent()) {
                EntityHelper.copyObject(customerRefund, fCustomerRefund.get());
            }
        }
        tbCustomerRefund result = customerRefundRepository.save(customerRefund);
        if (Text != "") {
            CreateChangeRecord(CommitType, Text, result);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(@Param(value = "Data") tbCustomerRefund customerRefund,
            tbArrivalRegistration arrivalRegistration, String Text, String CommitType) throws Exception {
        if (customerRefund == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date nowTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (customerRefund.getCustomerRefundRequestId() != null) {
            if (arrivalRegistration.getDocumentNumber() != null) {
                customerRefund.setDocumentNumber(arrivalRegistration.getDocumentNumber());
            }
            if (customerRefund.getRefundRequestNumber() == "") {
                customerRefund.setRefundRequestNumber(sdt.append(dt.format(nowTime)).toString());
            }
            result = UPDATE(customerRefund);
            if (Text != "") {
                CreateChangeRecord(CommitType, Text, customerRefund);
            }
        }
        return result;
    }

    public int UPDATE(tbCustomerRefund customerRefund) {
        int result = customerRefundRepository.UPDATE(customerRefund.getReasonForReturn(),
                customerRefund.getRefundRequestNumber(),
                customerRefund.getApplicant(),
                customerRefund.getRefundType(),
                customerRefund.getAgencyFeeAmount(),
                customerRefund.getOfficalFeeAmount(),
                customerRefund.getDocumentNumber(),
                customerRefund.getRefundMethod(),
                customerRefund.getBank(),
                customerRefund.getAccountNumber(),
                customerRefund.getAccountName(),
                customerRefund.getAddTime(),
                customerRefund.getUserId(),
                customerRefund.getCustomerRefundRequestId());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            customerRefundRepository.deleteById(id);
            finanChangeRecordRepository.deleteAllByPidAndModuleName(id, "Arrival");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbCustomerRefund jlsp(@Param(value = "Data") tbCustomerRefund customerRefund) throws Exception {
        tbCustomerRefund result = new tbCustomerRefund();
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Optional<tbCustomerRefund> fArr =
                customerRefundRepository.findById(customerRefund.getCustomerRefundRequestId());
        customerRefund.setApprover(Integer.parseInt(loginUserInfo.getUserId()));
        customerRefund.setApproverDate(new Date());
        if (fArr.isPresent()) {
            EntityHelper.copyObject(customerRefund, fArr.get());
        }
        result = customerRefundRepository.save(customerRefund);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbCustomerRefund cwsp(tbCustomerRefund customerRefund) throws Exception {
        tbCustomerRefund result = new tbCustomerRefund();
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Optional<tbCustomerRefund> fArr =
                customerRefundRepository.findById(customerRefund.getCustomerRefundRequestId());
        customerRefund.setReviewer(Integer.parseInt(loginUserInfo.getUserId()));
        customerRefund.setDateOfReview(new Date());
        if (fArr.isPresent()) {
            EntityHelper.copyObject(customerRefund, fArr.get());
        }
        result = customerRefundRepository.save(customerRefund);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbCustomerRefund SaveJinLi(@Param(value = "Data") tbCustomerRefund customerRefund, String Text,
            String CommitType) throws Exception {
        if (customerRefund == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (customerRefund.getCustomerRefundRequestId() != null) {
            result = updateJinLi(customerRefund, Integer.parseInt(loginUserInfo.getUserId()), new Date());
        }
        if (Text != "") {
            CreateChangeRecord(CommitType, Text, customerRefund);
        }
        return customerRefund;
    }

    public int updateJinLi(tbCustomerRefund customerRefund, int Approver, Date ApproverDate) {
        int result = customerRefundRepository.updateJinLi(Approver, ApproverDate,
                customerRefund.getApproverResult(),
                customerRefund.getApproverDescription(),
                customerRefund.getCustomerRefundRequestId());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbCustomerRefund SaveCaiWu(@Param(value = "Data") tbCustomerRefund customerRefund, String Text,
            String CommitType) throws Exception {
        if (customerRefund == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (customerRefund.getCustomerRefundRequestId() != null) {
            result = updateCaiWu(customerRefund, Integer.parseInt(loginUserInfo.getUserId()), new Date());
        }
        if (Text != "") {
            CreateChangeRecord(CommitType, Text, customerRefund);
        }
        return customerRefund;
    }

    public int updateCaiWu(tbCustomerRefund customerRefund, int Reviewer, Date DateOfReview) {
        int result = customerRefundRepository.updateCaiWu(Reviewer, DateOfReview,
                customerRefund.getAuditResult(),
                customerRefund.getReviewerDescription(),
                customerRefund.getCustomerRefundRequestId());
        return result;
    }

    private FinanChangeRecord CreateChangeRecord(String Mode, String Text, tbCustomerRefund tbCustomerRefund) {
        LoginUserInfo info = CompanyContext.get();
        FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
        finanChangeRecord.setPid(tbCustomerRefund.getCustomerRefundRequestId());
        finanChangeRecord.setChangeText(Text);
        finanChangeRecord.setMode(Mode);
        finanChangeRecord.setModuleName("CustomerRefund");
        finanChangeRecord.setUserId(Integer.parseInt(info.getUserId()));
        finanChangeRecord.setCreateTime(new Date());
        return finanChangeRecordRepository.save(finanChangeRecord);
    }
}
