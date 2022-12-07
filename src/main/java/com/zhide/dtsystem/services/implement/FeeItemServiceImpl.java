package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.feeListNameRepository;
import com.zhide.dtsystem.repositorys.t3Repository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.IFeeItemService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeeItemServiceImpl implements IFeeItemService {
    @Autowired
    ViewTZSMapper tzsMapper;

    @Autowired
    PantentInfoMemoMapper infoMemoMapper;

    @Autowired
    FeeItemMapper feeMapper;

    @Autowired
    t3Repository t3Rep;

    @Autowired
    feeListNameRepository feeListNameRep;

    @Autowired
    NBBHCode NBBHCode;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = feeMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("shenqingh").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, memos);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
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
    public boolean ChangePayMark(List<Integer> IDS, int PayState) {
        for (int i = 0; i < IDS.size(); i++) {
            int ID = IDS.get(i);
            Optional<t3> t3s = t3Rep.findById(ID);
            if (t3s.isPresent()) {
                t3 t3 = t3s.get();
                t3.setPstate(PayState);
                t3Rep.save(t3);
            }
        }
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean AddToFeeList(List<Integer> IDS, String JFQD) {
        LoginUserInfo Info = CompanyContext.get();
        String jfpd_id = feeMapper.getFlowCode("QDBH");
        feeListName newFee = new feeListName();
        newFee.setJfqdId(jfpd_id);
        newFee.setJfqd(JFQD);
        newFee.setCreateTime(new Date());
        newFee.setCreateEmp(Info.getUserName());
        feeListNameRep.save(newFee);

        List<t3> allT3 = t3Rep.findAllByIdIn(IDS);
        for (int i = 0; i < allT3.size(); i++) {
            t3 t = allT3.get(i);
            t.setJfqdId(jfpd_id);
            t3Rep.save(t);
        }
        return true;
    }

    @Override
    public boolean ChangeJiaoDuiMoney(List<Integer> IDS, String Money) {
        List<t3> allT3 = t3Rep.findAllByIdIn(IDS);
        for (int i = 0; i < allT3.size(); i++) {
            t3 t = allT3.get(i);
            t.setRengongJdje(Money);
            t3Rep.save(t);
        }
        return true;
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

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<v_PantentInfoMemo> memos) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("shenqingh").toString();
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
            }
        });
        return row;
    }
}
