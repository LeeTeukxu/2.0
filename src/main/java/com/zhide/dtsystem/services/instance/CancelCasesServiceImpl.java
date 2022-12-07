package com.zhide.dtsystem.services.instance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CancelCasesMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICancelCasesService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CancelCasesServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年06月24日 11:20
 **/
@Service
public class CancelCasesServiceImpl implements ICancelCasesService {
    @Autowired
    cancelCasesMainRepository mainRep;
    @Autowired
    cancelCasesSubRepository subRep;
    @Autowired
    CancelCasesMapper cancelCasesMapper;

    @Autowired
    casesMainRepository caseMainRep;
    @Autowired
    caseHighMainRepository caseHighRep;
    @Autowired
    tradeCasesRepository tradeRep;
    @Autowired
    casesSubRepository caseSubRep;
    @Autowired
    caseHighSubRepository caseHighSubRep;
    @Autowired
    arrivalUseDetailRepository useRep;
    @Autowired
    tbArrivalRegistrationRepository arrRep;
    @Autowired
    tbCustomerRefundRepository custRep;

    @Override
    public pageObject getCasesMain(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams1(request);
        List<Map<String, Object>> datas = cancelCasesMapper.getCasesMain(params);
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
    public List<Map<String, Object>> getAllSubs(String CasesID) {
        return cancelCasesMapper.getSubsByCasesId(CasesID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveAll(cancelCasesMain main, List<cancelCasesSub> Subs) throws Exception {
        if (Subs.size() == 0) throw new Exception("至少要一条交易明缰记录!");
        LoginUserInfo Info = CompanyContext.get();
        if (main.getId() == null) {
            main.setState(1);
            //Double Total = Subs.stream().mapToDouble(f -> f.getKouDai() + f.getKouGuan()).sum();
            //main.setTotalMoney(Total);
            main.setCreateMan(Info.getUserIdValue());
            main.setCreateTime(new Date());
        }
        cancelCasesMain mainOne = mainRep.save(main);
        for (cancelCasesSub sub : Subs) {
            if (sub.getId() == null) {
                sub.setMainId(mainOne.getId());
                sub.setCasesId(mainOne.getCasesId());
                sub.setCreateMan(Info.getUserIdValue());
                sub.setCreateTime(new Date());
            }
            subRep.save(sub);
        }
    }

    @Override
    public pageObject getMain(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = cancelCasesMapper.getMain(params);
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
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String StateText = request.getParameter("State");
        if (StringUtils.isEmpty(StateText) == false) {
            params.put("State", Integer.parseInt(StateText));
        }
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

    public List<Map<String, Object>> getCancelCasesTotal() {
        LoginUserInfo Info = CompanyContext.get();
        return cancelCasesMapper.getCancelCasesTotal(Info.getDepIdValue(), Info.getUserIdValue(), Info.getRoleName());
    }

    @Override
    public pageObject getSub(Integer MainID) {
        pageObject result = new pageObject();
        List<Map<String, Object>> datas = cancelCasesMapper.getSub(MainID);
        result.setTotal(datas.size());
        result.setData(datas);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AuditOne(Integer ID, Integer Result, String Memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<cancelCasesMain> findMains = mainRep.findById(ID);
        if (findMains.isPresent()) {
            cancelCasesMain main = findMains.get();
            main.setAuditMan(Info.getUserIdValue());
            main.setAuditTime(new Date());
            main.setAuditText(Memo);
            if (Result == 1) {
                main.setState(2);
            } else main.setState(3);
            mainRep.save(main);
        } else throw new Exception("操作记录已不存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SetOne(Integer ID, Integer Result, String Memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<cancelCasesMain> findMains = mainRep.findById(ID);
        if (findMains.isPresent()) {
            cancelCasesMain main = findMains.get();
            main.setAuditMan(Info.getUserIdValue());
            main.setAuditTime(new Date());
            main.setAuditText(Memo);
            if (Result == 1) {
                main.setState(5);
                EndCancel(main);
            } else main.setState(4);
            mainRep.save(main);

        } else throw new Exception("操作记录已不存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "删除业务交单中止")
    public void Remove(Integer ID) throws Exception {
        Optional<cancelCasesMain> findMains = mainRep.findById(ID);
        if (findMains.isPresent()) {
            subRep.deleteAllByMainId(ID);
            mainRep.delete(findMains.get());
        } else throw new Exception("删除的业务已不存在，请刷新后重试!");
    }

    @Autowired
    tbClientRepository clientRep;
    private void EndCancel(cancelCasesMain mainObj) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String casesID = mainObj.getCasesId();
        String type = mainObj.getType();
        String DocSN = "";
        int ClientID = 0;
        Optional<casesMain> findMains = caseMainRep.findFirstByCasesId(casesID);
        if (findMains.isPresent()) {
            casesMain main = findMains.get();
            DocSN = main.getDocSn();
            ClientID = main.getClientId();
        } else {
            Optional<caseHighMain> findHighMains = caseHighRep.findFirstByCasesId(casesID);
            if (findHighMains.isPresent()) {
                caseHighMain highMain = findHighMains.get();
                DocSN = highMain.getDocSn();
                ClientID = highMain.getClientId();
            } else {
                Optional<tradeCases> findTrades=tradeRep.findFirstByCasesid(casesID);
                if(findTrades.isPresent()){
                    tradeCases tradeOne= findTrades.get();
                    DocSN=tradeOne.getDocSn();
                    ClientID= tradeOne.getClientId();
                }
            }
        }
        List<String> SubIDS = subRep.findAllByCasesId(casesID).stream().map(f -> f.getSubId()).collect(toList());
        if (mainObj.getRefundMoney() == true && mainObj.getTotalMoney() > 0) {
            tbCustomerRefund refund = boxEntity(ClientID,DocSN, mainObj);
            custRep.save(refund);
        }
        switch (type) {
            case "专利业务协作": {
                List<casesSub> subs = caseSubRep.findAllBySubIdIn(SubIDS);
                for (casesSub sub : subs) {
                    sub.setCancel(true);
                    sub.setCancelMan(Info.getUserIdValue());
                    sub.setCancelTime(new Date());
                    sub.setProcessState(70);
                }
                caseSubRep.saveAll(subs);
                break;
            }
            case "项目业务交单": {
                List<caseHighSub> subs = caseHighSubRep.findAllBySubIdIn(SubIDS);
                for (caseHighSub sub : subs) {
                    sub.setCancel(true);
                    sub.setCancelMan(Info.getUserIdValue());
                    sub.setCancelTime(new Date());
                    sub.setProcessState(70);
                }
                caseHighSubRep.saveAll(subs);
            }
        }
    }

    private tbCustomerRefund boxEntity(int ClientID,String DocSN, cancelCasesMain mainObj) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Double totalGun = mainObj.getGuanMoney();
        Double totalDai = mainObj.getDaiMoney();
        List<String> SubIDS =
                subRep.findAllByCasesId(mainObj.getCasesId()).stream().map(f -> f.getSubId()).collect(toList());
        List<String> SubNos = new ArrayList<>();
        switch (mainObj.getType()) {
            case "专利业务协作": {
                List<casesSub> subs = caseSubRep.findAllBySubIdIn(SubIDS);
                SubNos = subs.stream().map(f -> f.getSubNo()).collect(toList());
                break;
            }
            case "项目业务交单": {
                List<caseHighSub> subs = caseHighSubRep.findAllBySubIdIn(SubIDS);
                SubNos = subs.stream().map(f -> f.getSubNo()).collect(toList());
                break;
            }
        }

        tbCustomerRefund one = new tbCustomerRefund();
        one.setOfficalFeeAmount(Double.toString(totalGun));
        one.setAgencyFeeAmount(Double.toString(totalDai));
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");

        one.setRefundRequestNumber(sdt.append(dt.format(new Date())).toString());
        if (totalDai > 0 && totalGun == 0) {
            one.setRefundType(1);
        } else if (totalDai == 0 && totalGun > 0) {
            one.setRefundType(2);
        } else if (totalDai > 0 && totalGun > 0) {
            one.setRefundType(3);
        } else throw new Exception("官费和代理费报销都为0，数据发生了异常!");

        one.setApplicant(Info.getUserIdValue());
        one.setApproverResult(0);
        one.setAuditResult(0);
        one.setRefundMethod(mainObj.getRefundMethod());

//        one.setDocumentNumber(arrMain.getDocumentNumber());
//        one.setAccountName(arrMain.getPayer());
//        one.setAccountNumber(arrMain.getPaymentAccount());
//        one.setBank(arrMain.getReturnBank());
        one.setDocumentNumber(DocSN);
        one.setBank(mainObj.getBankName());
        one.setAccountName(mainObj.getAccountName());
        one.setAccountNumber(mainObj.getAccountNumber());

        one.setReasonForReturn(StringUtils.join(SubNos, ",") + "业务终止时自动生成财务退款单。");
        one.setUserId(Info.getUserIdValue());
        one.setAddTime(new Date());
        tbClient findClient=clientRep.findAllByClientID(ClientID);
        if(findClient!=null){
            one.setKhName(findClient.getName());
        }
        return one;
    }

    private Map<String, Object> getParams1(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SignTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        String CasesID = request.getParameter("CasesID");
        params.put("CasesID", CasesID);
        String ArrIDX = request.getParameter("ArrID");
        if (org.springframework.util.StringUtils.isEmpty(ArrIDX) == false) {
            params.put("ArrID", ArrIDX);
        }
        String TotalX = request.getParameter("Total");
        if (org.springframework.util.StringUtils.isEmpty(TotalX) == false) {
            params.put("Total", TotalX);
        }
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
}
