package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.ViewPatentInfoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.IFeeWatchService;
import com.zhide.dtsystem.services.instance.applyMoneyCreator;
import com.zhide.dtsystem.services.instance.yearMoneyCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeeWatchServiceImpl implements IFeeWatchService {
    @Autowired
    ViewPatentInfoMapper patentInfoMapper;
    @Autowired
    yearFeeListRepository yearFeeRep;
    @Autowired
    applyFeeListRepository applyFeeRep;
    @Autowired
    yearWatchConfigRepository yearWatchRep;
    @Autowired
    applyWatchConfigRepository applyWatchRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    yearMoneyCreator yearCreator;
    @Autowired
    applyMoneyCreator applyCreator;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    allFeePayForResultRepository allPayRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> parameters = getParameters(request);
        List<Map<String, Object>> datas = patentInfoMapper.getWatchData(parameters);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("SHENQINGH").toString()).collect(Collectors.toList());
            datas.stream().forEach(f -> {
                Map<String, Object> X = eachSingleRow(f);
                PP.add(X);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> ImportYearData(String Mode, List<Map<String, Object>> Datas) throws Exception {
        for (int i = 0; i < Datas.size(); i++) {
            Map<String, Object> Data = Datas.get(i);
            String Result = "导入成功";
            try {
                String Shenqingh = Data.get("SHENQINGH").toString();
                String FeePercent = Data.get("FeePercent").toString();
                Integer BeginTimes = Integer.parseInt(Data.get("BeginTimes").toString());
                Integer BeginJiaoFei = Integer.parseInt(Data.get("BeginJiaoFei").toString());
                Optional<pantentInfo> findOnes = pantentRep.findByShenqingh(Shenqingh);
                if (findOnes.isPresent()) {

                    List<yearFeeList> findYears = yearFeeRep.findAllByShenqingh(Shenqingh);
                    if (findYears.size() > 0) {
                        //yearFeeRep.deleteByShenqingh(Shenqingh);
                    }
                    Optional<yearWatchConfig> watchConfigs = yearWatchRep.findAllByShenQingh(Shenqingh);
                    if (watchConfigs.isPresent()) {
                       // yearWatchRep.deleteAllByShenQingh(Shenqingh);
                    }
                    List<yearFeeList> years = yearCreator.ImportYearFeeItems(BeginTimes, BeginJiaoFei, Shenqingh,
                            FeePercent);
                    for (int n = 0; n < years.size(); n++) {
                        yearFeeList year = years.get(n);
                        year.setShenqingh(Shenqingh);
                        yearFeeRep.save(year);
                    }
                    pantentInfo one = findOnes.get();
                    one.setYearWatch(2);
                    if (StringUtils.isEmpty(Mode)) one.setYearSource(4);
                    else one.setYearSource(3);
                    pantentRep.save(one);
                } else throw new Exception(Shenqingh + "在专利信息库中不存在!");

            } catch (Exception ax) {
                Result = ax.getMessage();
                Data.put("ImportResult", Result);
                throw ax;
            }
            Data.put("ImportResult", Result);
        }
        return Datas;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, Object>> ImportApplyData(String Mode, List<Map<String, Object>> Datas) throws Exception {
        for (int i = 0; i < Datas.size(); i++) {
            Map<String, Object> Data = Datas.get(i);
            String Result = "导入成功";
            try {
                String Shenqingh = Data.get("SHENQINGH").toString();
                String FeePercent = Data.get("FeePercent").toString();
                Optional<pantentInfo> findOnes = pantentRep.findByShenqingh(Shenqingh);
                if (findOnes.isPresent()) {
                    Optional<applyWatchConfig> watchConfigs = applyWatchRep.findAllByShenqingh(Shenqingh);
                    if (watchConfigs.isPresent()) {
                        applyWatchRep.deleteByShenqingh(Shenqingh);
                    }
                    List<applyFeeList> findApplys = applyFeeRep.findAllByShenqingh(Shenqingh);
                    if (findApplys.size() > 0) {
                        applyFeeRep.deleteByShenqingh(Shenqingh);
                    }
                    applyCreator.ImportDatas(Shenqingh, FeePercent);

                    pantentInfo One = findOnes.get();
                    /*设置已监控和监控来源为数据导入*/
                    One.setApplyWatch(2);
                    if (StringUtils.isEmpty(Mode)) One.setApplySource(4);
                    else One.setApplySource(3);
                    pantentRep.save(One);

                } else throw new Exception(Shenqingh + "在专利信息库中不存在!");

            } catch (Exception ax) {
                Result = ax.getMessage();
                Data.put("ImportResult", Result);
                throw ax;
            }
            Data.put("ImportResult", Result);
        }
        return Datas;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void ChangeValue(List<String> SH, String Code, Integer State) throws Exception {
        for (int i = 0; i < SH.size(); i++) {
            String SHENQINGH = SH.get(i);
            Optional<pantentInfo> findPantentInfo = pantentRep.findByShenqingh(SHENQINGH);
            if (findPantentInfo.isPresent()) {
                pantentInfo pantentInfo = findPantentInfo.get();
                if (Code.equals("ApplyWatch")) {
                    pantentInfo.setApplyWatch(State);
                    if (State == 3) {
                        List<applyFeeList> apps = applyFeeRep.findAllByShenqingh(SHENQINGH);
                        apps.stream().forEach(f -> {
                            f.setJkStatus(3);
                            f.setNeedPayFor(false);
                            f.setAddPayFor(false);
                        });
                        applyFeeRep.saveAll(apps);
                        //applyFeeRep.deleteByShenqingh(SHENQINGH);
                        //applyWatchRep.deleteByShenqingh(SHENQINGH);
                    }
                    pantentInfo.setApplySource(3);
                } else if (Code.equals("YearWatch")) {
                    pantentInfo.setYearWatch(State);
                    if (State == 3) {
                        //yearFeeRep.deleteByShenqingh(SHENQINGH);
                        //yearWatchRep.deleteAllByShenQingh(SHENQINGH);

                        List<yearFeeList> fees = yearFeeRep.findAllByShenqingh(SHENQINGH);
                        fees.forEach(f -> {
                            f.setJkStatus(3);
                            f.setNeedPayFor(false);
                            f.setAddPayFor(false);
                        });
                        yearFeeRep.saveAll(fees);
                    }
                    pantentInfo.setYearSource(3);
                }
                pantentRep.save(pantentInfo);
            } else throw new Exception(SHENQINGH + "不存在!");
        }
    }

    @Autowired
    applyFeeListRepository applyRep;
    @Autowired
    yearFeeListRepository yearRep;
    @Autowired
    otherOfficeFeeListRepository otherRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveOne(Integer ID) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        Optional<allFeePayForResult> findOnes=allPayRep.findById(ID);
        if(findOnes.isPresent()){
            allFeePayForResult One=findOnes.get();
            String Type=One.getType();
            Integer PayState=One.getPayState();
            if(PayState!=1) throw new Exception("只有待缴费状态记录才可以删除，请刷新后再重试操作!");
            String[] IDS=One.getFeeItemId().split(",");
            for(String IID:IDS){
                Integer TID=Integer.parseInt(IID);
                switch (Type){
                    case "Year":{
                        Optional<yearFeeList> findYears=yearRep.findById(TID);
                        if(findYears.isPresent()){
                            yearFeeList year=findYears.get();
                            year.setAddPayFor(false);
                            year.setJkStatus(0);
                            year.setUpMan(Info.getUserIdValue());
                            year.setUpTime(new Date());
                            yearRep.save(year);
                        }
                    }
                    case "Apply":{
                        Optional<applyFeeList> findApplys=applyRep.findById(TID);
                        if(findApplys.isPresent()){
                            applyFeeList apply=findApplys.get();
                            apply.setAddPayFor(false);
                            apply.setJkStatus(0);
                            apply.setUpMan(Info.getUserIdValue());
                            apply.setUpTime(new Date());
                            applyRep.save(apply);
                        }
                    }
                    case "OtherOfficeFee":{
                        Optional<otherOfficeFee> findOthers=otherRep.findById(TID);
                        if(findOthers.isPresent()){
                            otherOfficeFee apply=findOthers.get();
                            apply.setAddPayFor(false);
                            apply.setOtherOfficeStates(1);
                            otherRep.save(apply);
                        }
                    }
                }
            }
            allPayRep.deleteById(ID);
        } else throw new Exception("准备删除的业务已不存在,请刷新后再尝试操作!");
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
        String Mode = request.getParameter("Mode");
        params.put("Mode", Mode);
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

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("SHENQINGH").toString();
        String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));
        NBBHInfo nbInfo = NBBHCode.Parse(NEIBUBH);
        nbInfo.foreach((type, ids) -> {
            List<String> names = ids.stream().map(f -> f.getName()).collect(Collectors.toList());
            if (names.size() > 0) {
                row.put(type, Strings.join(names, ','));
                if (type == "KH") row.put("KHID", ids.get(0).getID());
            }
        });
        return row;
    }
}
