package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CaseHighTechBrowseMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICaseHighSubUserService;
import com.zhide.dtsystem.services.define.ICaseHighTechBrowseService;
import com.zhide.dtsystem.services.define.ICaseHighUserService;
import com.zhide.dtsystem.services.define.ILoginUserService;
import com.zhide.dtsystem.services.simpleMemoCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CaseHighTechBrowseServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月28日 19:59
 **/
@Service
public class CaseHighTechBrowseServiceImpl implements ICaseHighTechBrowseService {

    @Autowired
    CaseHighTechBrowseMapper browseTechMapper;
    @Autowired
    caseHighSubRepository subRep;
    @Autowired
    caseHighMainRepository mainRep;
    @Autowired
    caseHighSubFilesRepository highSubFilesRep;
    @Autowired
    tbAttachmentRepository attRep;

    @Autowired
    ICaseHighUserService highUserService;
    @Autowired
    ICaseHighSubUserService highSubUserService;

    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ILoginUserService loginUserService;
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = browseTechMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            List<String> SubIDS = datas.stream().map(f -> f.get("SubID").toString()).collect(toList());
            Map<String, String> FileNames = getSubFileName(SubIDS);
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> data = datas.get(i);
                data.remove("_TotalNum");
                String SubID = data.get("SubID").toString();
                if (FileNames.containsKey(SubID)) {
                    String FileName = FileNames.get(SubID);
                    if (StringUtils.isEmpty(FileName) == false) {
                        data.put("AcceptTechFileName", FileName);
                    }
                }
                Object PO = data.get("ProcessText");
                if (ObjectUtils.isEmpty(PO) == false) {
                    String ProcessText = PO.toString();
                    if (StringUtils.isEmpty(ProcessText) == false) {
                        List<simpleMemo> memos = JSON.parseArray(ProcessText, simpleMemo.class);
                        List<String> TT = new ArrayList<>();

                        for (int n = 0; n < memos.size(); n++) {
                            simpleMemo memo = memos.get(n);
                            String V = simpleMemoCreator.createSingle(n + 1, memo);
                            TT.add(V);
                        }
                        if (TT.size() > 0) {
                            data.put("ProcessText", ProcessText);
                            data.put("EDITMODE", 1);
                            data.put("ProcessMemo", StringUtils.join(TT, "<br/><br/>"));
                        }
                    }
                }
                Object lxObj = data.get("LXText");
                if (ObjectUtils.isEmpty(lxObj) == false) {
                    String sqText = lxObj.toString();
                    List<simpleMemo> Ss = JSON.parseArray(sqText, simpleMemo.class);
                    List<String> AAS = new ArrayList<>();
                    for (int x = 0; x < Ss.size(); x++) {
                        simpleMemo s = Ss.get(x);
                        String V = simpleMemoCreator.createSingleEx(x + 1, s);
                        AAS.add(V);
                    }
                    data.replace("LXText", StringUtils.join(AAS, "<br/><br/>"));
                }
            }
            object.setData(datas);
        }
        return object;
    }

    @Override
    public List<Map<String,Object>> getWaitReport(String Type,HttpServletRequest request) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        Map<String,Object> params=new HashMap<>();
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "Num";

        params.put("sortOrder",sortOrder);
        params.put("sortField",sortField);
        params.put("UserID",Info.getUserIdValue());
        params.put("RoleName",Info.getRoleName());
        params.put("Type",Type);
        return  browseTechMapper.getWaitReport(params);
    }

    private Map<String, String> getSubFileName(List<String> SubIDS) {
        Map<String, String> Res = new HashMap<>();
        List<caseHighSubFiles> SubFiles = highSubFilesRep.getAllBySubIdInAndType(SubIDS, "Accept");
        List<String> AttIDS = SubFiles.stream().map(f -> f.getAttId()).collect(toList());
        List<tbAttachment> AttFiles = attRep.findAllByGuidInAndType(AttIDS, 1);
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            List<caseHighSubFiles> sFiles = SubFiles.stream().filter(f -> f.getSubId().equals(SubID)).collect(toList());
            List<String> TinyIDS = sFiles.stream().map(f -> f.getAttId()).collect(toList());
            List<tbAttachment> findAlls = AttFiles.stream()
                    .filter(f -> TinyIDS.contains(f.getGuid()))
                    .sorted(Comparator.comparing(tbAttachment::getUploadTime).reversed())
                    .collect(toList());
            if (findAlls.size() > 0) {
                if (Res.containsKey(SubID) == false) {
                    Res.put(SubID, findAlls.get(0).getName());
                }
            }
        }
        return Res;
    }

    @Override
    public void updateCasesSubList(List<caseHighSub> subs) {
        for (int i = 0; i < subs.size(); i++) {
            caseHighSub sub = subs.get(i);
            subRep.save(sub);
        }
        String CasesID = subs.get(0).getCasesId();
        List<caseHighSub> allSub = subRep.findAllByCasesId(CasesID);

        List<String> TechMans =
                allSub.stream().filter(f -> f.getTechMan() != null && f.getTechMan() > 0).map(f -> f.getTechMan
                ().toString()).distinct().collect(Collectors.toList());
        List<String> TechManagers =
                allSub.stream().filter(f -> StringUtils.isEmpty(f.getTechManager()) == false).map(f -> f
                .getTechManager()).distinct().collect(Collectors.toList());


        String TechMan = StringUtils.join(TechMans, ',');
        String TechManaager = StringUtils.join(TechManagers, ',');

        List<String> SettleMans =
                allSub.stream().filter(f -> f.getSettleMan() != null && f.getSettleMan() > 0).map(f -> f
                .getSettleMan().toString()).distinct().collect(Collectors.toList());
        List<String> SettleManagers =
                allSub.stream().filter(f -> StringUtils.isEmpty(f.getSetttleManager()) == false).map(f -> f
                .getTechManager()).distinct().collect(Collectors.toList());

        String SettleMan = StringUtils.join(SettleMans, ',');
        String SettleManager = StringUtils.join(SettleManagers, ',');

        List<caseHighSub> cs = subRep.findAllByCasesId(CasesID);
        Optional<caseHighMain> findOne = mainRep.findFirstByCasesId(CasesID);
        if (findOne.isPresent()) {
            caseHighMain main = findOne.get();
            main.setTechMan(TechMan);
            main.setTechManager(TechManaager);
            main.setCwMan(SettleMan);
            main.setCwManager(SettleManager);
            mainRep.save(main);
        }
    }

    @Override
    public void changeToHistoryAttachment(String SubID) {
        List<caseHighSubFiles> files = highSubFilesRep.getAllBySubId(SubID);
        if (files.size() > 0) {
            List<String> IDS = files.stream().map(f -> f.getAttId()).collect(toList());
            List<tbAttachment> attachments = attRep.findAllByGuidInOrderByUploadTime(IDS);
            for (int i = 0; i < attachments.size(); i++) {
                tbAttachment tb = attachments.get(i);
                tb.setType(2);
                attRep.save(tb);
            }
        }
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
            if (StateText.equals("9") == false) {
                params.put("State", Integer.parseInt(StateText));
            }
        }
        String CasesID = request.getParameter("CasesID");
        if (StringUtils.isEmpty(CasesID) == false) {
            params.put("CasesID", CasesID);
        }
        LoginUserInfo Info = CompanyContext.get();
        Function<String, String> O = a -> a.toUpperCase();

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
    public void acceptTechTask(List<String> IDS, String AuditMan,String TechMan) throws Exception {
        LoginUserInfo logInfo = CompanyContext.get();
        if(StringUtils.isEmpty(TechMan))TechMan=logInfo.getUserId();
        Integer TechManValue=Integer.parseInt(TechMan);
        List<caseHighSub> items = new ArrayList<>();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (sub.getProcessState() == 50) {
                    sub.setTechMan(TechManValue);
                    sub.setTechManager(loginUserMapper.getManager(logInfo.getUserId()));
                    sub.setTechAuditMan(Integer.parseInt(AuditMan));
                    sub.setAcceptTechTime(new Date());
                    sub.setProcessState(51);
                    items.add(sub);

                    highSubUserService.importOne(sub.getCasesId(), sub.getSubId());
                    highSubUserService.addOne(sub.getCasesId(), sub.getSubId(),TechManValue);
                    highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), Integer.parseInt(AuditMan));
                    highUserService.AddOne(sub.getCasesId(), TechManValue);
                    /*指定核稿人可能不是技术人员的经理，都给加上。*/
                    highUserService.AddOne(sub.getCasesId(), Integer.parseInt(AuditMan));
                } else throw new Exception("当前业务:" + sub.getSubNo() + "已被其他人员抢先处理，请刷新后再选择记录进行操作！");
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void rejectTechTask(List<String> IDS) throws Exception {
        LoginUserInfo logInfo = CompanyContext.get();
        List<caseHighSub> items = new ArrayList<>();

        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (sub.getProcessState() == 51) {


                    highUserService.RemoveOne(sub.getCasesId(),sub.getTechMan());
                    highSubUserService.removeOne(sub.getCasesId(),sub.getSubId(),sub.getTechMan());
                    sub.setTechMan(0);
                    sub.setTechManager("");
                    sub.setTechAuditMan(0);
                    sub.setAcceptTechTime(null);
                    sub.setProcessState(50);
                    items.add(sub);



                    highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                    highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                } else throw new Exception("当前业务:" + sub.getSubNo() + "已被其他人员抢先处理，请刷新后再选择记录进行操作！");
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void CommitTechFiles(List<String> IDS, String Memo) throws Exception {
        List<caseHighSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                sub.setProcessState(52);
                sub.setCommitTechMemo(Memo);
                sub.setCommitTechTime(new Date());
                sub.setCommitTechMan(logInfo.getUserIdValue());
                items.add(sub);

                highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void AuditTechFiles(List<String> IDS, int Result, String Memo) throws Exception {
        List<caseHighSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(54);
                else {
                    sub.setProcessState(53);
                    List<caseHighSubFiles> audFiles = highSubFilesRep.getAllBySubId(sub.getSubId());
                    if (audFiles.size() > 0) {
                        String es = StringUtils.join(audFiles.stream()
                                        .filter(f -> f.getType().equals("Aud"))
                                        .map(f -> f.getAttId()).collect(toList()),
                                ',');
                        sub.setAuditTechFiles(es);
                    }
                }
                sub.setAuditTechTime(new Date());
                sub.setAuditTechMemo(Memo);
                sub.setAuditTechMan(logInfo.getUserIdValue());
                items.add(sub);

                highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (Result == 0) {
                    changeToHistoryAttachment(sub.getSubId());
                }
            }
        }
        updateCasesSubList(items);
    }

    /*未用*/
    @Override
    public void SetTechFiles(List<String> IDS, int Result, String Memo) throws Exception {
        List<caseHighSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(55);
                else {
                    sub.setProcessState(56);
                    List<caseHighSubFiles> expFiles = highSubFilesRep.getAllBySubId(sub.getSubId());
                    if (expFiles.size() > 0) {
                        String es = StringUtils.join(expFiles.stream()
                                        .filter(f -> f.getType().equals("Exp"))
                                        .map(f -> f.getAttId()).collect(toList()),
                                ',');
                        sub.setExpFiles(es);
                    }
                }
                sub.setClientSetTime(new Date());
                sub.setClientSetResult(Result);
                sub.setClientSetMemo(Memo);
                sub.setClientSetMan(logInfo.getUserIdValue());

                items.add(sub);

                highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (Result == 0) {
                    changeToHistoryAttachment(sub.getSubId());
                }
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void SBTechFiles(List<String> IDS, int Result, String Memo) throws Exception {
        List<caseHighSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();

        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(56);
                else sub.setProcessState(55);
                sub.setTechSbTime(new Date());
                sub.setTechSbMan(logInfo.getUserIdValue());
                sub.setTechSbResult(Result);
                sub.setTechSbMemo(Memo);
                items.add(sub);

                highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
            }
        }
        updateCasesSubList(items);
    }

    @Override
    public void AuditSettle(List<String> IDS, int Result, String Memo) throws Exception {
        List<caseHighSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();

        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                caseHighSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(58);
                else sub.setProcessState(57);
                sub.setSettleTime(new Date());
                sub.setSettleMan(logInfo.getUserIdValue());
                sub.setSetttleManager(loginUserMapper.getManager(logInfo.getUserId()));
                sub.setSettleResult(Result);
                sub.setSettleMemo(Memo);
                items.add(sub);
                highUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                highSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
            }
        }
        updateCasesSubList(items);
    }
    @Override
    public void ChangeLXText(String SubID, String NewLXText) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<caseHighSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            caseHighSub subOne = findSubs.get();
            String X = subOne.getMemo();
            if (StringUtils.equals(X, NewLXText) == false) {
                List<simpleMemo> Ms = new ArrayList<>();
                String lxText = subOne.getLxText();
                if (StringUtils.isEmpty(lxText) == false) {
                    Ms = JSON.parseArray(lxText, simpleMemo.class);
                }
                simpleMemo M = new simpleMemo();
                M.setSource(X);
                M.setChange(NewLXText);
                M.setCreateTime(new Date());
                M.setCreateMan(Info.getUserIdValue());
                M.setCreateManName(Info.getUserName());
                Ms.add(M);
                subOne.setMemo(NewLXText);
                subOne.setLxText(JSON.toJSONString(Ms));
                subRep.save(subOne);
            }
        }
    }
    public void ChangeSupportMan(String SubID,String SupportMan){
        Optional<caseHighSub> findSubs=subRep.findFirstBySubId(SubID);
        if(findSubs.isPresent()){
            caseHighSub sub=findSubs.get();
            sub.setSupportMan(SupportMan);
            subRep.save(sub);
        }
    }
    @Autowired
    casesChangeRecordRepository changeRep;

    @Transactional(rollbackFor = Exception.class)
    public void ChangeTechMan(int OldMan,int NewMan,String SubID) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        Optional<caseHighSub> findSubs=subRep.findFirstBySubId(SubID);
        Map<String ,String>IDNameHash=loginUserService.getAllByIDAndName();
        if(findSubs.isPresent()){
            caseHighSub sub=findSubs.get();
            int OldTechMan=sub.getTechMan();
            String OldTechManager=sub.getTechManager();
            String NewTechManager=loginUserMapper.getManager(Integer.toString(NewMan));
            sub.setTechMan(NewMan);
            sub.setTechManager(NewTechManager);
            sub.setAcceptTechTime(new Date());
            subRep.save(sub);

            highSubUserService.replaceOne(sub.getCasesId(),SubID,OldMan,NewMan,OldTechManager,NewTechManager);
            highUserService.replaceOne(sub.getCasesId(),OldMan,NewMan,OldTechManager,NewTechManager);

            Optional<caseHighMain> findMains=mainRep.findFirstByCasesId(sub.getCasesId());
            if(findMains.isPresent()){
                caseHighMain main=findMains.get();
                String NowTech=main.getTechMan();
                String NowManager=main.getTechManager();

                List<Integer> NowTechs= IntegerUtils.parseIntArray(NowTech);
                List<Integer> NowManagers=IntegerUtils.parseIntArray(NowManager);

                int X=NowTechs.indexOf(OldMan);
                if(X>-1)NowTechs.remove(X);
                NowTechs.add(NewMan);

                NowManagers.removeAll(IntegerUtils.parseIntArray(OldTechManager));
                NowManagers.addAll(IntegerUtils.parseIntArray(NewTechManager));

                main.setTechMan(StringUtils.join(NowTechs,","));
                main.setTechManager(StringUtils.join(NowManagers,","));
                mainRep.save(main);
            }
            casesChangeRecord record=new casesChangeRecord();
            record.setCasesId(sub.getCasesId());
            record.setCreateTime(new Date());
            record.setMode("修改");
            record.setUserId(Info.getUserIdValue());
            String ChangeText=
                    sub.getSubNo()+"的技术人员由:【"+ IDNameHash.get(OldMan)+"】,变更为【"+IDNameHash.get(NewMan)+"】。";
            record.setChangeText(ChangeText);
            changeRep.save(record);
        } else throw new Exception("要调整的业务交单对象已不存在!");
    }
}
