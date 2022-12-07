package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.AllNoticeMapper;
import com.zhide.dtsystem.mapper.FeeItemMemoMapper;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.tbTZSSendTypeRepository;
import com.zhide.dtsystem.repositorys.tzsPeriodConfigRepository;
import com.zhide.dtsystem.services.*;
import com.zhide.dtsystem.services.define.IAllNoticeService;
import com.zhide.dtsystem.services.define.ITZSEmailService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AllNoticeServiceImpl implements IAllNoticeService {

    @Autowired
    AllNoticeMapper allNoticeMapper;
    @Autowired
    ITZSEmailService tzsEmail;
    @Autowired
    PantentInfoMemoMapper infoMemoMapper;
    @Autowired
    FeeItemMemoMapper feeItemMemoMapper;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    tbTZSSendTypeRepository tzsSendTypeRepository;
    @Autowired
    tzsPeriodConfigRepository tzsPeriodConfigRepository;
    @Autowired
    CompanyTimeOutCache menuCache;
    @Autowired
    TZSRepository tzsRepository;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject result = new pageObject();
        List<Map<String, Object>> datas = new ArrayList<>();
        int total =0;
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> IDSHash = allNoticeMapper.getAllNoticeIdPage(params);
        if(IDSHash.size()>0) {
            List<String> IDS = IDSHash.stream().map(f -> f.get("shenqingh").toString()).collect(Collectors.toList());
            //int total=allNoticeMapper.getAllNoticeTotal(params);

            if (IDSHash.size() > 0) total = IntegerUtils.parseInt(IDSHash.get(0).get("_TotalNum").toString());
            Map<String, Object> secParams = new HashMap<>();
            secParams.put("sortOrder", params.get("sortOrder"));
            secParams.put("sortField", params.get("sortField"));
            secParams.put("ids", IDS);
            List<Map<String, Object>> rows = allNoticeMapper.getAllDataByIds(secParams);
            List<String> allTongZhiIDS = rows.stream().filter(f -> f.get("PID").toString().equals("0") == false)
                    .map(f -> f.get("FID").toString())
                    .collect(Collectors.toList());
            List<String> SIDS = IDS;
            if (SIDS.size() == 0) SIDS.add("0");
            Map<String, Object> tzsEmailRecord = tzsEmail.getAll(allTongZhiIDS);
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            List<v_PantentInfoMemo> yearfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Year", SIDS);
            List<v_PantentInfoMemo> applyfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Apply", SIDS);
            IDS.stream().forEach(id -> {
                Map<String, Object> kRow = null;
                Optional<Map<String, Object>> pRow =
                        rows.stream().filter(f -> f.get("FID").toString().equals(id)).findFirst();
                if (pRow.isPresent() == true) {
                    kRow = pRow.get();
                    Map<String, Object> row = eachSingleRow(kRow, tzsEmailRecord, memos, yearfeememos, applyfeememos);
                    row.put("isLeaf", false);
                    row.put("_level", 1);
                    row.replace("FAWENRQ", "");
                    row.replace("XIAZAIRQ", "");
                    datas.add(row);
                }
                List<Map<String, Object>> findRows = rows.stream()
                        .filter(f -> f.get("PID").toString().equals(id))
                        .collect(Collectors.toList());

                NBBHInfo nbInfo = null;
                if (kRow != null) {
                    if (kRow.containsKey("NEIBUBH")) {
                        String NEIBUBH = SuperUtils.toString(kRow.get("NEIBUBH"));
                        if (StringUtils.isEmpty(NEIBUBH) == false) nbInfo = NBBHCode.Parse(NEIBUBH);
                    }
                }

                for (int i = 0; i < findRows.size(); i++) {
                    Map<String, Object> row = findRows.get(i);
                    String TONGZHISBH = row.get("FID").toString();
                    row.put("SENDMAIL", tzsEmailRecord.containsKey(TONGZHISBH) ? 1 : 0);
                    row.put("isLeaf", true);
                    row.put("_level", 2);

                    if (nbInfo != null) {
                        nbInfo.foreach((type, ids) -> {
                            List<String> names = ids.stream().map(f -> f.getName()).collect(Collectors.toList());
                            if (names.size() > 0) {
                                row.put(type, Strings.join(names, ','));
                                if (type == "KH") row.put("KHID", ids.get(0).getID());
                            }
                        });
                    }
                    datas.add(row);
                }
            });
        }
        result.setTotal(total);
        result.setData(datas);
        return result;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "FAWENRQ";
        if(sortField.equals("Name"))sortField="Shenqingh";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            highText = URLDecoder.decode(highText);
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
            Map<String, Object> row,
            Map<String, Object> emailRecord,
            List<v_PantentInfoMemo> memos, List<v_PantentInfoMemo> yearfeememos, List<v_PantentInfoMemo> applyfeememos) {
        String SHENQINGH = row.get("shenqingh").toString();
        String TONGZHISBH = row.get("FID").toString();

        row.put("SENDMAIL", emailRecord.containsKey(TONGZHISBH) ? 1 : 0);

        List<List<v_PantentInfoMemo>> mms = new ArrayList<>();
        mms.add(memos);
        mms.add(yearfeememos);
        mms.add(applyfeememos);
        ManyInfoMemoCreator creator = new ManyInfoMemoCreator(mms);
        List<String> words = creator.Build(SHENQINGH);
        row.put("EDITMEMO", words.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            row.put("MEMO", String.join("<br/><br/>", words));
        } else {
            row.put("MEMO", "");
        }
        return row;
    }

    @Override
    public List<Map<String, Object>> getTZSSendTypeData() throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<Map<String, Object>> OO = new ArrayList<>();
        Map<String, Object> menuHash = new HashMap<>();
        List<ComboboxItem> allFuns = getAllTZSPeriodConfig();
        menuHash.put("Functions", allFuns);
        List<String> checks = getVisiblePeriod(Integer.parseInt(Info.getCompanyId()));
        menuHash.put("Checkeds", checks);
        OO.add(menuHash);
        return OO;
    }

    private List<ComboboxItem> getAllTZSPeriodConfig() {
        List<Map<String, Object>> funs = tzsRepository.findTSID();
        List<ComboboxItem> res = new ArrayList<>();
        for (int i=0;i<funs.size();i++) {
            Map<String, Object> fun = funs.get(i);
            ComboboxItem item = new ComboboxItem();
            item.setId(fun.get("tzsPeriodId").toString());
            item.setText(fun.get("text").toString());
            res.add(item);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(List<String> data) {
        tzsSendTypeRepository.deleteAll();
        List<tbTZSSendType> listTZSSendType = new ArrayList<>();
        for (String TZSPeriodID : data) {
            tbTZSSendType tzsSendType = new tbTZSSendType();
            tzsSendType.setTzsPeriodId(TZSPeriodID);
            listTZSSendType.add(tzsSendType);
        }
        tzsSendTypeRepository.saveAll(listTZSSendType);
        return false;
    }

    public List<String> getVisiblePeriod(int CompanyID) {
        List<String> res = new ArrayList<>();
        String Key = "VisiblePeriodChecked:" + CompanyID;
        menuCache.setTimeOut(6000000L);
        if (menuCache.hasExpire(Key)) {
            res = tzsSendTypeRepository.findAll().stream().map(f -> f.getTzsPeriodId()).collect(toList());
            menuCache.setValue(Key, res);
        }else res = menuCache.getArrayToStrings(Key);
        return res;
    }
}
