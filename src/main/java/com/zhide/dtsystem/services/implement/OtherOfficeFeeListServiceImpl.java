package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.FeeItemMemoMapper;
import com.zhide.dtsystem.mapper.OtherOfficeFeeListMapper;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.IOtherOfficeFeeListService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OtherOfficeFeeListServiceImpl implements IOtherOfficeFeeListService {
    @Autowired
    ViewTZSMapper tzsMapper;


    @Autowired
    OtherOfficeFeeListMapper otherOfficeFeeListMapper;

    @Autowired
    FeeItemMapper feeMapper;

    @Autowired
    t3Repository t3Rep;


    @Autowired
    FeeItemMemoMapper infoMemoMapper;
    @Autowired
    NBBHCode NBBHCode;

    @Autowired
    feeListNameRepository feeListNameRep;
    @Autowired
    applyFeeListRepository applyFeeListRep;
    @Autowired
    otherOfficeFeeListRepository otherOfficeFeeListRepository;
    @Autowired
    casesSubRepository caseSubRep;
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = otherOfficeFeeListMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("id").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds("OtherOfficeFee", SIDS);
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
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String ID = request.getParameter("ID");
        if (Strings.isNotEmpty(ID)) {
            params.put("IDS", ID);
        }

//        String SHENQINGHS=request.getParameter("SHENQINGH");
//        if (Strings.isNotEmpty(SHENQINGHS)){
//            List<String> listSHENQINGHS = new ArrayList<String>();
//            String[] strSHENQINGHS=SHENQINGHS.split(",");
//            for (int i=0;i<strSHENQINGHS.length;i++){
//                listSHENQINGHS.add(strSHENQINGHS[i]);
//            }
//            params.put("SHENQINGHS",listSHENQINGHS);
//        }else params.put("SHENQINGHS",new ArrayList<>());

        String ExpenseItem = request.getParameter("ExpenseItem");
        if (Strings.isEmpty(ExpenseItem) == false) {
            List<String> listExpenseItem = new ArrayList<String>();
            String[] strExpenseItem = ExpenseItem.split(",");
            for (int i = 0; i < strExpenseItem.length; i++) {
                listExpenseItem.add(strExpenseItem[i]);
            }
            params.put("ExpenseItems", listExpenseItem);
        }

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
    public boolean SaveAll(List<otherOfficeFee> items) {

        for (int i = 0; i < items.size(); i++) {
            otherOfficeFeeListRepository.deleteAllByShenqingh(items.get(i).getShenqingh());
        }
//        for (int i=0;i<items.size();i++){
//            otherOfficeFee row=items.get(i);
//            int ID=row.getId();
//            Optional<otherOfficeFee> newOnes=otherOfficeFeeListRepository.findById(ID);
//            if (newOnes.isPresent()){
//                otherOfficeFee newOne=newOnes.get();
//                newOne.setJiaofeir(row.getJiaofeir());
//                newOne.setExpenseItem(row.getExpenseItem());
//                newOne.setAmount(row.getAmount());
//                newOne.setShenqingh(row.getShenqingh());
//                newOne.setFamingmc(row.getFamingmc());
//                newOne.setShenqinglx(row.getShenqinglx());
//                newOne.setState(row.getState());
//                newOne.setXmoney(row.getXmoney());
//                newOne.setShow(row.getShow());
//                newOne.setYear(row.getYear());
//                newOne.setAddPayFor(row.getAddPayFor());
//                newOne.setNeedPayFor(row.getNeedPayFor());
//                newOne.setSxmoney(row.getSxmoney());
//                newOne.setExpenseItemId(row.getExpenseItemId());
//                newOne.setType(row.getType());
//                newOne.setOtherOfficeStates(row.getOtherOfficeStates());
//                otherOfficeFeeListRepository.save(newOne);
//            }
//            else {
//                otherOfficeFeeListRepository.saveAll(items);
//            }
//        }
        otherOfficeFeeListRepository.saveAll(items);
        return true;
    }

    @Override
    public boolean remove(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            otherOfficeFeeListRepository.deleteById(id);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangePayForStatus(List<Integer> IDS, String Status) {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<otherOfficeFee> find = otherOfficeFeeListRepository.findById(ID);
            if (find.isPresent()) {
                otherOfficeFee one = find.get();
                boolean OK = (Status.equals("1") ? true : false);
                one.setNeedPayFor(OK);
                if (OK == false) {
                    one.setOtherOfficeStates(4);
                } else {
                    one.setOtherOfficeStates(1);
                }
                otherOfficeFeeListRepository.save(one);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeXMoney(List<Integer> IDS, double Money) {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<otherOfficeFee> find = otherOfficeFeeListRepository.findById(ID);
            if (find.isPresent()) {
                otherOfficeFee One = find.get();
                One.setXmoney(Money);
                otherOfficeFeeListRepository.save(One);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeSXMoney(List<Integer> IDS, double Money) {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = IDS.get(i);
            Optional<otherOfficeFee> find = otherOfficeFeeListRepository.findById(ID);
            if (find.isPresent()) {
                otherOfficeFee One = find.get();
                One.setSxmoney(Money);
                otherOfficeFeeListRepository.save(One);
            }
        }
    }

    @Override
    @Transactional
    public boolean Remove(String ID) {
        String[] IDS = ID.split(",");
        for (int i = 0; i < IDS.length; i++) {
            Integer Id = Integer.parseInt(IDS[i]);
            otherOfficeFeeListRepository.deleteAllById(Id);
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getLinkMan(int ClientID) {
        Map<String, Object> params = new HashMap<>();
        params.put("ClientID", ClientID);
        return otherOfficeFeeListMapper.getLinkMan(params);
    }

    @Override
    public List<Map<String, Object>> getLinkManInfo(int LinkID) {
        Map<String, Object> params = new HashMap<>();
        params.put("LinkID", LinkID);
        return otherOfficeFeeListMapper.getLinkManInfo(params);
    }
}
