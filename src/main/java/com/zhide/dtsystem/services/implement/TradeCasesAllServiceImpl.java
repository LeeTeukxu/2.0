package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CasesMemoMapper;
import com.zhide.dtsystem.mapper.TradeCasesAllBrowseMapper;
import com.zhide.dtsystem.mapper.TradeCasesTechBrowseMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.models.tbClientLinkers;
import com.zhide.dtsystem.models.v_PantentInfoMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientLinkersRepository;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.ITradeCasesAllService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import com.zhide.dtsystem.viewModel.OtherBillObject;
import com.zhide.dtsystem.viewModel.OtherDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeCasesAllServiceImpl implements ITradeCasesAllService {
    @Autowired
    TradeCasesAllBrowseMapper allMapper;
    @Autowired
    CasesMemoMapper memoMapper;
    @Autowired
    tbClientLinkersRepository linkerRep;
    @Autowired
    TradeCasesTechBrowseMapper techMapper;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = allMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> CIDS = datas.stream().map(f -> f.get("CASESID").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = memoMapper.getAllByIds(CIDS);
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> row = datas.get(i);
                Map<String, Object> newRow = eachSingleRow(row, memos);
                PP.add(newRow);
            }
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    String NONE="无相关设置";
    @Override
    public OtherBillObject getBillObject(String CasesID) {
        OtherBillObject billObject = allMapper.getBillObject(CasesID);
        List<tbClientLinkers> linkers = linkerRep.findAllByClientID(billObject.getCLIENTID());
        if (linkers.size() > 0) {
            tbClientLinkers linker=linkers.get(0);
            billObject.setADDRESS(linker.getAddress());
            billObject.setLINKMAN(linker.getLinkMan());
            billObject.setLINKPHONE(linker.getLinkPhone());
            billObject.setMOBILE(linker.getMobile());
        } else {
            billObject.setADDRESS(NONE);
            billObject.setLINKMAN(NONE);
            billObject.setLINKPHONE(NONE);
            billObject.setMOBILE(NONE);
        }
        List<OtherDetail> Items=techMapper.getDetails(CasesID);
        billObject.setRows(Items);
        return billObject;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageSize * pageIndex - 1);
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

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<v_PantentInfoMemo> memos) {
        row.remove("_TotalNum");
        String CasesID = row.get("CASESID").toString();
        PantentInfoMemoCreator creator = new PantentInfoMemoCreator(memos);
        List<String> words = creator.Build(CasesID);
        row.put("EDITMEMO", words.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            row.put("MEMO", String.join("<br/><br/>", words));
        } else {
            row.put("MEMO", "");
        }
        return row;
    }
}
