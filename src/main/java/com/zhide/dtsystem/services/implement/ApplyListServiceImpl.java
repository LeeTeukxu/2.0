package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.ApplyListMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.FeeItemMemoMapper;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.applyFeeListRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.feeListNameRepository;
import com.zhide.dtsystem.repositorys.t3Repository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.IApplyListService;
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
public class ApplyListServiceImpl implements IApplyListService {
    @Autowired
    ViewTZSMapper tzsMapper;


    @Autowired
    ApplyListMapper newMapper;

    @Autowired
    FeeItemMapper feeMapper;

    @Autowired
    t3Repository t3Rep;


    @Autowired
    FeeItemMemoMapper infoMemoMapper;


    @Autowired
    feeListNameRepository feeListNameRep;
    @Autowired
    applyFeeListRepository applyFeeListRep;

    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    casesSubRepository caseSubRep;
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        String pageSize= request.getParameter("pageSize");
        if(StringUtils.isEmpty(pageSize)==false) {
            Map<String, Object> params = getParams(request);
            List<Map<String, Object>> datas = newMapper.getData(params);
            int Total = 0;
            List<Map<String, Object>> PP = new ArrayList<>();
            if (datas.size() > 0) {
                Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
                List<String> SIDS = datas.stream().map(f -> f.get("id").toString()).collect(Collectors.toList());
                List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds("Apply", SIDS);
                datas.stream().forEach(f -> {
                    Map<String, Object> row = eachSingleRow(f, memos);
                    PP.add(f);
                });
                object.setTotal(Total);
                object.setData(PP);
            }
        }
        return object;
    }

    @Override
    public pageObject clientFYJK(Map<String, Object> parameters) {
        pageObject object = new pageObject();
        List<Map<String, Object>> datas = newMapper.clientFYJK(parameters);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("id").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds("Apply", SIDS);
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, memos);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SHENQINGR";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("???????????????????????????????????????");

        String queryText = request.getParameter("Query");
        String queryTexts = request.getParameter("Querys");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        if (queryText != null) {
            if (Strings.isNotEmpty(queryTexts)) {
                List<sqlParameter> Vs = JSON.parseArray(queryTexts, sqlParameter.class);
                List<sqlParameter> AppItems = sqlParameterCreator.convert(Vs);
                params.put("ApplyItems", AppItems);
            } else params.put("ApplyItems", new ArrayList<>());
        }
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        return params;
    }

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<v_PantentInfoMemo> memos) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("id").toString();
        String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));
        PantentInfoMemoCreator creator = new PantentInfoMemoCreator(memos);
        List<String> words = creator.Build(SHENQINGH);
        row.put("EDITMEMO", words.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            row.put("MEMO", String.join("<br/><br/>", words));
        } else {
            row.put("MEMO", "");
        }
        NBBHInfo nbInfo = NBBHCode.Parse(NEIBUBH);
        nbInfo.foreach((type, ids) -> {
            List<String> names = ids.stream().map(f -> f.getName()).collect(Collectors.toList());
            if (names.size() > 0) {
                row.put(type, Strings.join(names, ','));
                if (type == "KH") row.put("KHID", ids.get(0).getID());
                if (type == "BH") {
                    UInfo FU = ids.get(0);
                    Optional<casesSub> findSubs = caseSubRep.findFirstBySubNo(FU.getName());
                    if (findSubs.isPresent()) {
                        row.put("CasesID", findSubs.get().getCasesId());
                    }
                }
            }
        });
        return row;
    }

    @Override
    public Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
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
        String queryText = request.getParameter("Query");
        String queryTexts = request.getParameter("Querys");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        if (Strings.isNotEmpty(queryTexts)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("ApplyItems", OrItems);
        } else params.put("ApplyItems", new ArrayList<>());
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
        } else throw new RuntimeException("???????????????????????????????????????");
        return params;
    }


    @Override
    public List<ComboboxItem> getFeeItems() {
        List<ComboboxItem> items = new ArrayList<>();
        feeMapper.getFeeItems().stream().forEach(f -> {
            ComboboxItem b = new ComboboxItem();
            b.setText(f);
            b.setId(f);
            items.add(b);
        });
        return items;
    }

    @Override
    public List<ComboboxItem> getZLItems() {
        List<ComboboxItem> items = new ArrayList<>();
        feeMapper.getZLItems().stream().forEach(f -> {
            ComboboxItem b = new ComboboxItem();
            b.setText(f);
            b.setId(f);
            items.add(b);
        });
        return items;
    }

    @Override
    public boolean addPayForList(List<Integer> feeItems) {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeXMoney(List<Integer> IDS, double Money) {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<applyFeeList> find = applyFeeListRep.findById(ID);
            if (find.isPresent()) {
                applyFeeList One = find.get();
                One.setXmoney(Money);
                applyFeeListRep.save(One);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangePayForStatus(List<Integer> IDS, String Status) {
        LoginUserInfo Info=CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<applyFeeList> find = applyFeeListRep.findById(ID);
            if (find.isPresent()) {
                applyFeeList one = find.get();
                boolean OK = (Status.equals("1") ? true : false);
                one.setNeedPayFor(OK);
                if(OK==false){
                    one.setJkStatus(3);
                    one.setCancelMan(Info.getUserIdValue());
                    one.setCancelTime(new Date());
                } else one.setJkStatus(0);
                one.setUpMan(Info.getUserIdValue());
                one.setUpTime(new Date());
                applyFeeListRep.save(one);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeSXMoney(List<Integer> IDS, double Money) {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<applyFeeList> find = applyFeeListRep.findById(ID);
            if (find.isPresent()) {
                applyFeeList One = find.get();
                One.setSxmoney(Money);
                applyFeeListRep.save(One);
            }
        }
    }
}
