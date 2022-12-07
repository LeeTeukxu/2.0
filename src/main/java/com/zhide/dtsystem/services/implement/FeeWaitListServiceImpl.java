package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeWaitListMapper;
import com.zhide.dtsystem.mapper.OtherOfficeFeeListMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IFeeWaitListService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import com.zhide.dtsystem.viewModel.ExportFeeItem;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class FeeWaitListServiceImpl implements IFeeWaitListService {
    @Autowired
    FeeWaitListMapper feeWaitListMapper;

    @Autowired
    yearFeePayForResultRepository yearFeeRep;

    @Autowired
    yearFeeListRepository yearListRep;
    @Autowired
    applyFeeListRepository applyListRep;
    @Autowired
    OtherOfficeFeeListMapper otherOfficeFeeListMapper;
    @Autowired
    otherOfficeFeeListRepository otherOfficeFeeListRepository;
    @Autowired
    pantentInfoRepository pantentInfoRepository;
    @Autowired
    allFeePayForResultRepository allPayRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = feeWaitListMapper.getData(params);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(int ID, String SHENQINGH, String OtherOfficeID, String auditText) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<allFeePayForResult> one = yearFeeRep.findById(ID);
        if (one.isPresent()) {
            allFeePayForResult fee = one.get();
            fee.setPayState(2);
            fee.setAuditMan(Integer.parseInt(Info.getUserId()));
            fee.setAuditTime(new Date());
            fee.setAuditText(auditText);
            yearFeeRep.save(fee);
            if (fee.getType().equals("Year")) {
                List<String> listFeeItemId = Arrays.asList(fee.getFeeItemId().split(","));
                List<yearFeeList> years = new ArrayList<>();
                for (int i = 0; i < listFeeItemId.size(); i++) {
                    Integer yearId = Integer.parseInt(listFeeItemId.get(i));
                    Optional<yearFeeList> findYears = yearListRep.findById(yearId);
                    if (findYears.isPresent()) {
                        yearFeeList year = findYears.get();
                        year.setJkStatus(5);
                        year.setPayMan(Info.getUserIdValue());
                        year.setPayTime(new Date());
                        year.setPayState(1);
                        years.add(year);
                    }
                }
                if (years.size() > 0) yearListRep.saveAll(years);
            } else if (fee.getType().equals("Apply")) {
                List<applyFeeList> applys = new ArrayList<>();
                List<String> listFeeItemId = Arrays.asList(fee.getFeeItemId().split(","));
                for (int i = 0; i < listFeeItemId.size(); i++) {
                    Integer applyId = Integer.parseInt(listFeeItemId.get(i));
                    Optional<applyFeeList> findApplys = applyListRep.findById(applyId);
                    if (findApplys.isPresent()) {
                        applyFeeList apply = findApplys.get();
                        apply.setJkStatus(5);
                        apply.setPayMan(Info.getUserIdValue());
                        apply.setPayTime(new Date());
                        apply.setPayState(1);
                        applys.add(apply);
                    }
                }
                if (applys.size() > 0) applyListRep.saveAll(applys);
            } else {
                Integer Count = otherOfficeFeeListMapper.getSQHAndID(SHENQINGH, Integer.parseInt(OtherOfficeID));
                if (Count > 0) {
                    List<String> listOtherOfficeId = Arrays.asList(OtherOfficeID.split(","));
                    List<otherOfficeFee> others = new ArrayList<>();
                    for (int i = 0; i < listOtherOfficeId.size(); i++) {
                        Integer otherId = Integer.parseInt(listOtherOfficeId.get(i));

                        Optional<otherOfficeFee> findOthers = otherOfficeFeeListRepository.findById(otherId);
                        if (findOthers.isPresent()) {
                            otherOfficeFee other = findOthers.get();
                            other.setOtherOfficeStates(3);
                            other.setPayMan(Info.getUserIdValue());
                            other.setPayTime(new Date());
                            other.setPayState(1);
                            others.add(other);
                        }
                        // otherOfficeFeeListRepository.update(3, Integer.parseInt(OtherOfficeID));
                    }
                    if (others.size() > 0) otherOfficeFeeListRepository.saveAll(others);
                }
            }
            return true;
        } else throw new Exception("指定的记录不存在。");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reject(int ID) throws Exception {
        Optional<allFeePayForResult> one = yearFeeRep.findById(ID);
        LoginUserInfo Info=CompanyContext.get();
        if (one.isPresent()) {
            allFeePayForResult fee = one.get();
            String IDS = fee.getFeeItemId();
            String Type = fee.getType();
            String[] IDArray = IDS.split(",");
            for (int i = 0; i < IDArray.length; i++) {
                Integer LID = Integer.parseInt(IDArray[i]);
                if (Type.equals("Year")) {
                    Optional<yearFeeList> sfindYear = yearListRep.findById(LID);
                    if (sfindYear.isPresent()) {
                        yearFeeList findYear = sfindYear.get();
                        findYear.setAddPayFor(false);
                        findYear.setJkStatus(0);
                        findYear.setUpMan(Info.getUserIdValue());
                        findYear.setUpTime(new Date());

                        yearListRep.save(findYear);
                        //pantentInfoRepository.UpdateYearJkStatus(4, findYear.getId());
                    }
                } else if (Type.equals("Apply")) {
                    Optional<applyFeeList> sfindApply = applyListRep.findById(LID);
                    if (sfindApply.isPresent()) {
                        applyFeeList findApply = sfindApply.get();
                        findApply.setAddPayFor(false);
                        findApply.setJkStatus(0);
                        findApply.setUpTime(new Date());
                        findApply.setUpMan(Info.getUserIdValue());
                        applyListRep.save(findApply);
                        pantentInfoRepository.UpdateApplyJkStatus(4, findApply.getId());
                    }
                } else if (Type.equals("OtherOfficeFee")) {
                    Optional<otherOfficeFee> otOfficeFee = otherOfficeFeeListRepository.findById(LID);
                    if (otOfficeFee.isPresent()) {
                        otherOfficeFee find = otOfficeFee.get();
                        find.setOtherOfficeStates(1);
                        find.setAddPayFor(false);
                        otherOfficeFeeListRepository.save(find);
                        //otherOfficeFeeListRepository.update(1, find.getId());
                    }
                }
            }
            //yearFeeRep.delete(fee);
            fee.setPayState(3);
            fee.setAuditMan(Info.getUserIdValue());
            fee.setAuditTime(new Date());
            fee.setAuditText("已驳回待缴费用申请");
            yearFeeRep.save(fee);
            return true;
        } else throw new Exception("指定的记录不存在。");
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SHENQINGR";
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
    public List<ExportFeeItem> getSelectedDatas(List<Integer> IDS){
        List<ExportFeeItem> Items=new ArrayList<>();
        for(Integer ID:IDS){
            Optional<allFeePayForResult> findResults=allPayRep.findById(ID);
            if(findResults.isPresent()){
                allFeePayForResult allResult=findResults.get();
                String IDX=allResult.getFeeItemId();
                if(StringUtils.isEmpty(IDX)) continue;
                List<Integer> TIDS= ListUtils.parse(IDX,Integer.class);
                if(TIDS.size()==0) continue;
                String Type=allResult.getType();
                if(Type.equals("Apply")){
                  List<applyFeeList> ass= applyListRep.findAllByIdIn(TIDS);
                  ass.stream().forEach(f->{
                      if(f.getFeename().indexOf("印花税")==-1) {
                          ExportFeeItem item = new ExportFeeItem();
                          item.setFeeName(f.getFeename());
                          item.setClientName(allResult.getTickHeader());
                          item.setShenqingh(f.getShenqingh());
                          item.setMoney(f.getXmoney() == null ? f.getMoney() : f.getXmoney());
                          item.setCreditCode(allResult.getCreditCode());
                          item.setIndex(Items.size() + 1);
                          Items.add(item);
                      }
                  });
                }
                else if(Type.equals("Year")){
                    List<yearFeeList>yss=yearListRep.findAllByIdIn(TIDS);
                    yss.stream().forEach(f->{
                        if(f.getFeename().indexOf("印花税")==-1) {
                            ExportFeeItem item = new ExportFeeItem();
                            item.setFeeName(f.getFeename());
                            item.setClientName(allResult.getTickHeader());
                            item.setShenqingh(f.getShenqingh());
                            item.setMoney(f.getXmoney() == null ? f.getMoney() : f.getXmoney());
                            item.setCreditCode(allResult.getCreditCode());
                            item.setIndex(Items.size() + 1);
                            Items.add(item);
                        }
                    });
                }
                else if(Type.equals("OtherOfficeFee")){
                    List<otherOfficeFee> oss=otherOfficeFeeListRepository.findAllByIdIn(TIDS);
                    oss.stream().forEach(f->{
                        if(f.getExpenseItem().indexOf("印花税")==-1) {
                            ExportFeeItem item = new ExportFeeItem();
                            item.setFeeName(f.getExpenseItem());
                            item.setClientName(allResult.getTickHeader());
                            item.setShenqingh(f.getShenqingh());
                            item.setMoney(Double.parseDouble(Float.toString(f.getAmount())));
                            item.setCreditCode(allResult.getCreditCode());
                            item.setIndex(Items.size() + 1);
                            Items.add(item);
                        }
                    });
                }
            }
        }
        return Items;
    }
}
