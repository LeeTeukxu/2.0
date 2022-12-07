package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CasesYwFreeMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.TradeCasesTechBrowseMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesYwItemsRepository;
import com.zhide.dtsystem.repositorys.tradeCasesRepository;
import com.zhide.dtsystem.services.define.ICasesSubUserService;
import com.zhide.dtsystem.services.define.ICasesUserService;
import com.zhide.dtsystem.services.define.ITradeCasesTechBrowseService;
import com.zhide.dtsystem.services.simpleMemoCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TradeCasesTechBrowseServiceImpl implements ITradeCasesTechBrowseService {

    @Autowired
    TradeCasesTechBrowseMapper tradeCasesTechBrowseMapper;
    @Autowired
    CasesYwFreeMapper casesYwFreeMapper;
    @Autowired
    casesYwItemsRepository casesYwItemsRepository;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ICasesSubUserService casesSubUserService;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    tradeCasesRepository mainRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = tradeCasesTechBrowseMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            List<String> SubIDS = datas.stream().map(f -> f.get("SubID").toString()).collect(toList());
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> data = datas.get(i);
                data.remove("_TotalNum");
                String ProcessText = "";
                if (data.containsKey("Memo")) {
                    Object PO = data.get("Memo");
                    if (ObjectUtils.isEmpty(PO) == false) {
                        ProcessText = PO.toString();
                    }
                }
                if (StringUtils.isEmpty(ProcessText) == false) AddMemo(data, ProcessText);
                Object sqObj = data.get("SQText");
                if (ObjectUtils.isEmpty(sqObj) == false) {
                    String sqText = sqObj.toString();
                    List<simpleMemo> Ss = JSON.parseArray(sqText, simpleMemo.class);
                    List<String> AAS = new ArrayList<>();
                    for (int x = 0; x < Ss.size(); x++) {
                        simpleMemo s = Ss.get(x);
                        String V = simpleMemoCreator.createSingleEx(x + 1, s);
                        AAS.add(V);
                    }
                    data.replace("SQText", StringUtils.join(AAS, "<br/><br/>"));
                }
                Object cgObj = data.get("CGText");
                if (ObjectUtils.isEmpty(cgObj) == false) {
                    String cgText = cgObj.toString();
                    List<simpleMemo> Ss = JSON.parseArray(cgText, simpleMemo.class);
                    List<String> AAS = new ArrayList<>();
                    for (int x = 0; x < Ss.size(); x++) {
                        simpleMemo s = Ss.get(x);
                        String V = simpleMemoCreator.createSingleEx(x + 1, s);
                        AAS.add(V);
                    }
                    data.replace("CGText", StringUtils.join(AAS, "<br/><br/>"));
                }
            }
            object.setData(datas);
        }
        return object;
    }

    @Override
    public List<Map<String, Object>> getAllFreeNums(String CasesID) {
        return casesYwFreeMapper.getAllFreeNums(CasesID);
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
            if (StateText.equals("All") == false) {
                params.put("State", Integer.parseInt(StateText));
            }
        }
        String CasesID = request.getParameter("CasesID");
        if (StringUtils.isEmpty(CasesID) == false) {
            params.put("CasesID", CasesID);
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

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public void acceptTechTask(String CASESIDS,String TechMan) throws Exception {
        LoginUserInfo logInfo = CompanyContext.get();
        List<casesYwItems> items = new ArrayList<>();
        List<String> IDS = Arrays.asList(CASESIDS.split(","));

        if(StringUtils.isEmpty(TechMan))TechMan=logInfo.getUserId();
        Integer TechManValue=Integer.parseInt(TechMan);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesYwItems> findOnes = casesYwItemsRepository.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesYwItems sub = findOnes.get();
                if (sub.getProcessState() == 50) {
                    sub.setTechMan(TechManValue);
                    sub.setTechManager(loginUserMapper.getManager(TechMan));
                    sub.setFilingTime(new Date());
                    sub.setProcessState(5);
                    items.add(sub);

                    casesSubUserService.importOne(sub.getCasesId(), sub.getSubId());
                    casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), TechManValue);
                    casesUserService.AddOne(sub.getCasesId(), TechManValue);
                } else throw new Exception("当前业务:" + sub.getSubNo() + "已被其他人员抢先处理，请刷新后再选择记录进行操作！");
            }
        }
        updateCasesSubList(items);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCasesSubList(List<casesYwItems> subs) {
        for (int i = 0; i < subs.size(); i++) {
            casesYwItems sub = subs.get(i);
            casesYwItemsRepository.save(sub);
        }
        LoginUserInfo Info = CompanyContext.get();
        String CasesID = subs.get(0).getCasesId();
        List<casesYwItems> allSub = casesYwItemsRepository.findAllByCasesId(CasesID);

        List<String> TechMans =
                allSub.stream().filter(f -> f.getTechMan() != null && f.getTechMan() > 0).map(f -> f.getTechMan
                        ().toString()).distinct().collect(Collectors.toList());
        List<String> TechManagers =
                allSub.stream().filter(f -> StringUtils.isEmpty(f.getTechManager()) == false).map(f -> f
                        .getTechManager()).distinct().collect(Collectors.toList());


        String TechMan = StringUtils.join(TechMans, ',');
        String TechManaager = StringUtils.join(TechManagers, ',');

        List<casesYwItems> cs = casesYwItemsRepository.findAllByCasesId(CasesID);
        int CompleteNum = cs.stream().filter(f -> f.getProcessState() == 64).collect(toList()).size();
        Optional<tradeCases> findOne = mainRep.findFirstByCasesid(CasesID);
        if (findOne.isPresent()) {
            tradeCases main = findOne.get();
            main.setTechMan(TechMan);
            main.setTechManager(TechManaager);
            if (cs.size() == CompleteNum) {
                main.setState(5);
                main.setFinishTime(new Date());
                main.setFinishMan(Info.getUserIdValue());
            }
            mainRep.save(main);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SBTechFiles(int Result, String Memo, String SubIDS) throws Exception {
        List<casesYwItems> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesYwItems> findOnes = casesYwItemsRepository.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesYwItems sub = findOnes.get();
                if (Result == 1) {
                    sub.setProcessState(6);
                    sub.setFilingTime(new Date());
                    sub.setTechMan(logInfo.getUserIdValue());
                }
                else {
                    sub.setProcessState(5);
                    sub.setFilingTime(null);
                    sub.setTechMan(null);
                }
                sub.setTechSbResult(Result);
                sub.setTechSbMemo(Memo);
                items.add(sub);

                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void ChangeTcName(String SubID, String NewShenqingName) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesYwItems> findSubs = casesYwItemsRepository.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesYwItems subOne = findSubs.get();
            String X = subOne.getTcName();
            if (StringUtils.isEmpty(X) == true) X = "";
            if (X.equals(NewShenqingName) == false) {
                List<simpleMemo> Ms = new ArrayList<>();
                String sqText = subOne.getSqText();
                if (StringUtils.isEmpty(sqText) == false) {
                    Ms = JSON.parseArray(sqText, simpleMemo.class);
                }
                simpleMemo M = new simpleMemo();
                M.setSource(X);
                M.setChange(NewShenqingName);
                M.setCreateTime(new Date());
                M.setCreateMan(Info.getUserIdValue());
                M.setCreateManName(Info.getUserName());
                Ms.add(M);
                subOne.setTcName(NewShenqingName);
                subOne.setSqText(JSON.toJSONString(Ms));
                casesYwItemsRepository.save(subOne);
            }
        }
    }
    @Override
    public void ChangeTcCategory(String SubID, String NewShenqingName) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesYwItems> findSubs = casesYwItemsRepository.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesYwItems subOne = findSubs.get();
            String X = subOne.getTcName();
            if (StringUtils.isEmpty(X) == true) X = "";
            if (X.equals(NewShenqingName) == false) {
                List<simpleMemo> Ms = new ArrayList<>();
                String cgText = subOne.getCgText();
                if (StringUtils.isEmpty(cgText) == false) {
                    Ms = JSON.parseArray(cgText, simpleMemo.class);
                }
                simpleMemo M = new simpleMemo();
                M.setSource(X);
                M.setChange(NewShenqingName);
                M.setCreateTime(new Date());
                M.setCreateMan(Info.getUserIdValue());
                M.setCreateManName(Info.getUserName());
                Ms.add(M);
                subOne.setTcCategory(NewShenqingName);
                subOne.setCgText(JSON.toJSONString(Ms));
                casesYwItemsRepository.save(subOne);
            }
        }
    }

    private void AddMemo(Map<String, Object> data, String ProcessText) {
        if (StringUtils.isEmpty(ProcessText) == false) {
            List<simpleMemo> memos = JSON.parseArray(ProcessText, simpleMemo.class);
            List<String> TT = new ArrayList<>();

            for (int n = 0; n < memos.size(); n++) {
                simpleMemo memo = memos.get(n);
                String V = simpleMemoCreator.createSingle(n + 1, memo);
                TT.add(V);
            }
            if (TT.size() > 0) {
                data.put("Memo", ProcessText);
                data.put("EDITMODE", 1);
                data.put("ProcessMemo", StringUtils.join(TT, "<br/><br/>"));
            }
        }
    }
}
