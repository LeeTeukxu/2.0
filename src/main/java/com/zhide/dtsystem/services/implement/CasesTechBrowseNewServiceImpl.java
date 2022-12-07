package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.CasesTechBrowseNewMapper;
import com.zhide.dtsystem.mapper.JGDateMemoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.JGDateMemoCreator;
import com.zhide.dtsystem.services.NBBHCreator;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.simpleMemoCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CasesTechBrowseNewServiceImpl implements ICasesTechBrowseNewService {

    @Autowired
    CasesTechBrowseNewMapper browseTechMapper;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    casesSubFilesRepository subFileRep;
    @Autowired
    tbAttachmentRepository attRep;

    @Autowired
    casesOutSourceMainRepository outMainRep;

    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    ICasesSubUserService casesSubUserService;
    @Autowired
    NBBHCreator nbCreator;
    @Autowired
    ICasesSubFileService subFileService;
    @Autowired
    IOutSourceTechService casesOutSourceService;
    @Autowired
    IOutSourceTechService outTechService;
    @Autowired
    casesOutSourceMainRepository casesOutMainRep;
    @Autowired
    casesOutSourceFileRepository casesOutFileRep;
    @Autowired
    IOutTechRecordService outRecordService;
    @Autowired
    ILoginUserService loginUserService;
    @Autowired
    JGDateMemoRepository jgDateMemoRepository;
    @Autowired
    JGDateMemoMapper jgDateMemoMapper;
    @Autowired
    productItemTypeRepository productRep;

    Logger logger = LoggerFactory.getLogger(CasesTechBrowseNewServiceImpl.class);
    Map<String, String> mainCache = new HashMap<>();
    @Autowired
    casesChangeRecordRepository changeRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        mainCache.clear();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = browseTechMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> CIDS = datas.stream().map(f -> f.get("SubID").toString()).collect(Collectors.toList());
            List<JGDateMemo> memos = jgDateMemoMapper.getAllByIds(CIDS);
            object.setTotal(Total);
            List<String> SubIDS = datas.stream().map(f -> f.get("SubID").toString()).collect(toList());
            Map<String, String> FileNames = getSubFileName(SubIDS);
            List<casesOutSourceMain> findOutMains = outMainRep.findAllBySubIdInAndProcessTextIsNotNull(SubIDS);
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> data = datas.get(i);
                data.remove("_TotalNum");
                String SubID = data.get("SubID").toString();
                String CasesID = data.get("CasesID").toString();
                if (FileNames.containsKey(SubID)) {
                    String FileName = FileNames.get(SubID);
                    if (StringUtils.isEmpty(FileName) == false) {
                        data.put("AcceptTechFileName", FileName);
                    }
                }
                Optional<casesOutSourceMain> findOnes = findOutMains.stream()
                        .filter(f -> f.getSubId().equals(SubID))
                        .findFirst();
                String ProcessText = "";
                if (findOnes.isPresent()) {
                    casesOutSourceMain One = findOnes.get();
                    ProcessText = One.getProcessText();

                } else {
                    if (data.containsKey("ProcessText")) {
                        Object PO = data.get("ProcessText");
                        if (ObjectUtils.isEmpty(PO) == false) {
                            ProcessText = PO.toString();
                        }
                    }
                }
                if (StringUtils.isEmpty(ProcessText) == false) {
                    LoginUserInfo Info = CompanyContext.get();
                    String RoleName = Info.getRoleName();
                    if (RoleName.indexOf("技术") == -1) {
                        AddMemo(data, ProcessText);
                    } else {
                        AddMemo(data, AddMainMemo(ProcessText, CasesID));
                    }
                } else {
                    AddMemo(data, AddMainMemo(ProcessText, CasesID));
                }
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
                Map<String, Object> row = datas.get(i);
                Map<String, Object> newRow = eachSingleRow(row, memos);
                PP.add(newRow);
            }
            object.setData(PP);
        }
        return object;
    }

    private String AddMainMemo(String ProcessText, String CasesID) {
        if (StringUtils.isEmpty(ProcessText)) ProcessText = "[]";
        SimpleDateFormat ss = new SimpleDateFormat("yyy-MM-dd HH:mm");
        List<simpleMemo> Ms = JSON.parseArray(ProcessText, simpleMemo.class);
        simpleMemo mm = new simpleMemo();
        String mainMemo = "";
        if (mainCache.containsKey(CasesID)) {
            mainMemo = mainCache.get(CasesID);
        } else {
            Optional<casesMain> findMains = mainRep.findFirstByCasesId(CasesID);
            if (findMains.isPresent()) {
                casesMain main = findMains.get();
                mainMemo = main.getMemo();
                if (StringUtils.isEmpty(mainMemo)) mainMemo = "";
                mainMemo = mainMemo.replace("$", "") + "$" + ss.format(main.getCreateTime());
                mainCache.put(CasesID, mainMemo);
            }
        }
        if (StringUtils.isEmpty(mainMemo) == false) {
            String[] tss = mainMemo.split("\\$");
            mm.setMemo(tss[0]);
            try {
                mm.setCreateTime(ss.parse(tss[1]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mm.setCreateManName("系统管理员");
            Ms.add(mm);
        }
        return JSON.toJSONString(Ms);
    }

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<JGDateMemo> memos) {
        row.remove("_TotalNum");
        String SubID = row.get("SubID").toString();
        JGDateMemoCreator creator = new JGDateMemoCreator(memos);
        List<String> words = creator.Build(SubID);
        row.put("EDITMEMO", row.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            String PS="";
            if(row.containsKey("ProcessMemo")){
                PS=row.get("ProcessMemo").toString();
                row.remove("ProcessMemo");
            }
            String VK= String.join("<br/><br/>", words);
            PS+=VK;
            row.put("ProcessMemo",PS);
        } else row.put("JGDateMEMO", "");
        return row;
    }

    public List<Map<String, Object>> getDynamicColumns(String Type, int State) {
        List<Map<String, Object>> res = new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        Map<String, Object> params = new HashMap<>();
        params.put("RoleName", Info.getRoleName());
        params.put("UserID", Info.getUserIdValue());
        params.put("Type", Type);
        params.put("State", State);
        List<String> Cs = browseTechMapper.getReportColumns(params);
        for (int i = 0; i < Cs.size(); i++) {
            String c = Cs.get(i);
            Map<String, Object> O = new HashMap<>();
            O.put("field", "col" + Integer.toString(i + 1));
            O.put("header", c);
            res.add(O);
        }
        return res;
    }

    @Override
    public List<Map<String, Object>> getWaitReport(int State, String Type, HttpServletRequest request) throws Exception {
        //List<productItemType> products=productRep.findAll();
        LoginUserInfo Info = CompanyContext.get();
        Map<String, Object> params = new HashMap<>();
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "Num";

        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        params.put("UserID", Info.getUserIdValue());
        params.put("RoleName", Info.getRoleName());
        params.put("Type", Type);
        params.put("State", State);
        String Cols = request.getParameter("Cols");
        List<gridColumnInfo> cols = JSON.parseArray(Cols, gridColumnInfo.class);
        List<Map<String, Object>> Datas = browseTechMapper.getWaitReport(params);
        List<Map<String, Object>> Ds = browseTechMapper.getWaitDetail(params);
        List<Map<String, Object>> Os = browseTechMapper.getOutTechDetail(params);
        for (Map<String, Object> data : Datas) {
            String Name = data.get("Name").toString();
            List<Map<String, Object>> Details =
                    Ds.stream().filter(f -> f.get("Name").toString().equals(Name)).collect(toList());
            for (Map<String, Object> detail : Details) {
                String YName = detail.get("YName").toString().trim();
                Double  Num = Double.parseDouble(detail.get("Num").toString());
//                Optional<productItemType> findProducts=
//                        products.stream().filter(f->f.getName().trim().equals(YName)).findFirst();
//                if(findProducts.isPresent()){
//                    productItemType product= findProducts.get();
//                    Integer workDays=product.getWorkDays();
//                    if(workDays!=null) Num=Num*workDays;
//                }
                Optional<gridColumnInfo> findCols = cols.stream().filter(f -> f.getHeader().equals(YName)).findFirst();
                if (findCols.isPresent()) {
                    String colName = findCols.get().getField();
                    data.put(colName, Num);
                }
            }
            List<Map<String, Object>> Outs =
                    Os.stream().filter(f -> f.get("Name").toString().equals(Name)).collect(toList());
            for (Map<String, Object> out : Outs) {
                int OutTech = Integer.parseInt(out.get("OutTech").toString());
                Double Num = Double.parseDouble(out.get("Num").toString());
                if (OutTech == 1) {
                    data.put("OutTechTrue", Num);
                } else if (OutTech == 0) {
                    data.put("OutTechFalse", Num);
                }
            }
        }
        return Datas;
    }

    @Override
    public List<Map<String, Object>> getClientReport(String Type, HttpServletRequest request) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "Num";
        String Begin = request.getParameter("Begin");
        String End = request.getParameter("End");
        if (StringUtils.isEmpty(Begin)) {
            Begin = simple.format(new Date(2020, 1, 1));
        }
        if (StringUtils.isEmpty(End)) {
            End = simple.format(new Date());
        }
        String Names = request.getParameter("Names");
        if (StringUtils.isEmpty(Names) == false) {
            params.put("Begin", Begin);
            params.put("End", End);
            params.put("Names", Names);
            params.put("sortOrder", sortOrder);
            params.put("sortField", sortField);
            params.put("UserID", Info.getUserIdValue());
            params.put("RoleName", Info.getRoleName());
            params.put("Type", Type);
            return browseTechMapper.getClientReport(params);
        } else return new ArrayList<>();
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
                data.put("ProcessText", ProcessText);
                data.put("EDITMODE", 1);
                data.put("ProcessMemo", StringUtils.join(TT, "<br/><br/>"));
            }
        }
    }

    private Map<String, String> getSubFileName(List<String> SubIDS) {
        Map<String, String> Res = new HashMap<>();
        List<casesSubFiles> SubFiles = subFileRep.getAllBySubIdInAndType(SubIDS, "Accept");
        List<String> AttIDS = SubFiles.stream().map(f -> f.getAttId()).collect(toList());
        List<tbAttachment> AttFiles = attRep.findAllByGuidInAndType(AttIDS, 1);
        if (AttFiles.size() < AttIDS.size()) {
            List<String> IDD =
                    AttFiles.stream().map(f -> f.getGuid()).filter(f -> AttIDS.contains(f) == false).collect(toList());
            if (IDD.size() >= 0) {
                List<tbAttachment> Ls = attRep.findAllByGuidInAndType(AttIDS, 2);
                AttFiles.addAll(Ls);
            }
        }
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            List<casesSubFiles> sFiles = SubFiles.stream().filter(f -> f.getSubId().equals(SubID)).collect(toList());
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

    /**
     * create by: mmzs
     * description: 主要是为了集中处理清除级存的问题。接单、核稿等操作都会清除getAllCasesSub的级存。重新获取。
     * create time:2020-07-24 17：51
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCasesSubList(List<casesSub> subs) {
        for (int i = 0; i < subs.size(); i++) {
            casesSub sub = subs.get(i);
            subRep.save(sub);
        }
        LoginUserInfo Info = CompanyContext.get();
        String CasesID = subs.get(0).getCasesId();
        List<casesSub> allSub = subRep.findAllByCasesId(CasesID);

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

        //String SettleMan = StringUtils.join(SettleMans, ',');
        //String SettleManager = StringUtils.join(SettleManagers, ',');

        List<casesSub> cs = subRep.findAllByCasesId(CasesID);
        int CompleteNum = cs.stream().filter(f -> f.getProcessState() == 62).collect(toList()).size();
        Optional<casesMain> findOne = mainRep.findFirstByCasesId(CasesID);
        if (findOne.isPresent()) {
            casesMain main = findOne.get();
            main.setTechMan(TechMan);
            main.setTechManager(TechManaager);
            //main.setCwMan(SettleMan);
            //main.setCwManager(SettleManager);
            logger.info("被调用:updateCasesSubList方法，{}发生了改变，目前CompleteNum:{},Sub Size:{},状态是:{}", main.getDocSn(),
                    CompleteNum,
                    cs.size(),
                    main.getState());
            if (cs.size() == CompleteNum) {
                main.setState(8);
                main.setCompleteTime(new Date());
                main.setCompleteMan(Info.getUserIdValue());
                logger.info("{}完成了所有业务审核。状态更改为8", main.getDocSn());
            }
            mainRep.save(main);
        } else logger.info("交单业务:{},CasesID:{}无法找到对应的实例，判断是否完成失败。", subs.get(0).getSubNo(), CasesID);
    }

    @Override
    public void changeToHistoryAttachment(String SubID) {
        List<casesSubFiles> files = subFileRep.getAllBySubId(SubID);
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

    @Override
    public void ChangeShenqingName(String SubID, String NewShenqingName) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub subOne = findSubs.get();
            String X = subOne.getShenqingName();
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
                subOne.setShenqingName(NewShenqingName);
                subOne.setSqText(JSON.toJSONString(Ms));
                subRep.save(subOne);
            }
        }
    }

    @Override

    public void ChangeLXText(String SubID, String NewLXText) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub subOne = findSubs.get();
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

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void acceptTechTask(String SubIDS, String AuditMan, String TechMan) throws Exception {
        LoginUserInfo logInfo = CompanyContext.get();
        List<casesSub> items = new ArrayList<>();
        List<String> IDS = Arrays.asList(SubIDS.split(","));

        if (StringUtils.isEmpty(TechMan)) TechMan = logInfo.getUserId();
        Integer TechManValue = Integer.parseInt(TechMan);
        if (logInfo.getRoleName().equals("外协代理师")) {
            throw new Exception("外协代理师只能在专利外部协作管理模块接单!");
        }
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                if (sub.getProcessState() == 50) {
                    sub.setTechMan(TechManValue);
                    sub.setTechManager(loginUserMapper.getManager(TechMan));
                    sub.setTechAuditMan(Integer.parseInt(AuditMan));
                    sub.setAcceptTechTime(new Date());
                    sub.setProcessState(51);
                    items.add(sub);

                    casesSubUserService.importOne(sub.getCasesId(), sub.getSubId());
                    casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), TechManValue);
                    casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), Integer.parseInt(AuditMan));
                    casesUserService.AddOne(sub.getCasesId(), TechManValue);
                    /*指定核稿人可能不是技术人员的经理，都给加上。*/
                    casesUserService.AddOne(sub.getCasesId(), Integer.parseInt(AuditMan));
                } else throw new Exception("当前业务:" + sub.getSubNo() + "已被其他人员抢先处理，请刷新后再选择记录进行操作！");
            }
        }
        updateCasesSubList(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectTechTask(String SubIDS) throws Exception {
        LoginUserInfo logInfo = CompanyContext.get();
        List<casesSub> items = new ArrayList<>();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                Integer XUserID = sub.getTechMan();
                if (sub.getProcessState() == 51) {
                    sub.setTechMan(0);
                    sub.setTechManager("");
                    sub.setTechAuditMan(0);
                    sub.setAcceptTechTime(null);
                    sub.setProcessState(50);
                    items.add(sub);

                    casesUserService.RemoveOne(sub.getCasesId(), XUserID);
                    casesSubUserService.removeOne(sub.getCasesId(), XUserID);

                    //casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                    //casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                } else throw new Exception("当前业务:" + sub.getSubNo() + "已被其他人员抢先处理，请刷新后再选择记录进行操作！");
            }
        }
        updateCasesSubList(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void CommitTechFiles(String SubIDS, String Memo) throws Exception {
        List<casesSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                sub.setProcessState(52);
                sub.setWorkStatus(3);
                sub.setCommitTechMemo(Memo);
                sub.setCommitTechTime(new Date());
                sub.setCommitTechMan(logInfo.getUserIdValue());
                items.add(sub);

                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                checkAcceptFile(sub);
            }
        }
        updateCasesSubList(items);
    }

    private void checkAcceptFile(casesSub sub) throws Exception {
        int Num = 0;
        String acceptFiles = sub.getAcceptTechFiles();
        if (StringUtils.isEmpty(acceptFiles)) throw new Exception("必须要上传专利申报文件!");
        String[] IDS = acceptFiles.split(",");
        for (String ID : IDS) {
            Optional<tbAttachment> findAtt = attRep.findAllByGuid(ID);
            if (findAtt.isPresent()) {
                Num++;
            }
        }
        if (Num == 0) throw new Exception("必须要上传专利申报文件!");
    }

    private String unZipFile(String filePath) throws Exception {
        FTPUtil util = new FTPUtil();
        if (util.connect() == true) {
            String downloadPath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
            util.download(filePath, downloadPath);
            String saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            ZipFileUtils.unZip(downloadPath, saveDir);
            FileUtils.deleteQuietly(new File(downloadPath));
            return saveDir;
        } else throw new Exception("下载" + filePath + "失败。");
    }

    private void AddSomeToOutSource(casesSub sub, int Result, String Memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesOutSourceMain> findOneMains = casesOutMainRep.findFirstBySubId(sub.getSubId());
        if (findOneMains.isPresent()) {
            casesOutSourceMain findOneMain = findOneMains.get();
            if (Result == 0) {
                outTechService.AuditTechFiles(Arrays.asList(sub.getSubId()), "技术文件内审驳回,原因:" + Memo, "0");
                List<casesSubFiles> audFiles = subFileRep.getAllBySubId(sub.getSubId())
                        .stream()
                        .filter(f -> f.getType().equals("Aud"))
                        .collect(toList());
                List<String> AS = audFiles.stream().map(f -> f.getAttId()).collect(toList());
                if (audFiles.size() > 0) {
                    for (int i = 0; i < audFiles.size(); i++) {
                        casesSubFiles audFile = audFiles.get(i);
                        String attId = audFile.getAttId();
                        Optional<casesOutSourceFile> findOuts = casesOutFileRep.getFirstByAttId(attId);
                        if (findOuts.isPresent() == false) {
                            casesOutSourceFile newFile = new casesOutSourceFile();
                            newFile.setOutId(findOneMain.getOutId());
                            newFile.setSubId(findOneMain.getSubId());
                            newFile.setType("Aud");
                            newFile.setAttId(attId);
                            newFile.setCreateMan(Info.getUserIdValue());
                            newFile.setCreateTime(new Date());
                            casesOutFileRep.save(newFile);
                            if (AS.contains(attId) == false) AS.add(attId);
                        }
                    }
                    String es = StringUtils.join(AS, ',');
                    findOneMain.setState(4);
                    findOneMain.setAuditTechFiles(es);
                }
            } else {
                findOneMain.setAuditText(Memo);
                findOneMain.setAuditResult(Integer.toString(Result));
                findOneMain.setAuditTime(new Date());
                findOneMain.setState(5);
                outRecordService.AddOne(findOneMain.getSubId(), "技术审核通过");
            }
            casesOutMainRep.save(findOneMain);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AuditTechFiles(int Result, String SubIDS, String Memo) throws Exception {
        List<casesSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                if (Result == 1) {
                    sub.setProcessState(54);
                    sub.setWorkStatus(4);
                }
                else {
                    sub.setProcessState(53);
                    List<casesSubFiles> audFiles = subFileRep.getAllBySubId(sub.getSubId());
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

                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (Result == 0) {
                    changeToHistoryAttachment(sub.getSubId());
                }
                if (sub.getOutTech() == true) AddSomeToOutSource(sub, Result, Memo);
            }
        }
        updateCasesSubList(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SetTechFiles(int Result, String Memo, String SubIDS) throws Exception {
        List<casesSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(56);
                else {
                    sub.setProcessState(55);
                    List<casesSubFiles> expFiles = subFileRep.getAllBySubId(sub.getSubId());
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

                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (Result == 0) {
                    changeToHistoryAttachment(sub.getSubId());
                }
                if (Result == 1) {
                    String X = nbCreator.getContent(sub.getSubId());
                    sub.setNbbh(X);
                }
                if (sub.getOutTech() == true) AddSomeObject(sub, Result, Memo);
            }
        }
        updateCasesSubList(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SBTechFiles(int Result, String Memo, String SubIDS) throws Exception {
        List<casesSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(62);
                else sub.setProcessState(61);
                sub.setTechSbTime(new Date());
                sub.setTechSbMan(logInfo.getUserIdValue());
                sub.setTechSbResult(Result);
                sub.setTechSbMemo(Memo);
                items.add(sub);

                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (sub.getOutTech() == true) AddSomeSettle(ID, Result, Memo);
            }
        }
        updateCasesSubList(items);
    }

    @Transactional(rollbackFor = Exception.class)
    protected void AddSomeSettle(String SubID, int Result, String Memo) {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesOutSourceMain> findOneMains = casesOutMainRep.findFirstBySubId(SubID);
        if (findOneMains.isPresent()) {
            casesOutSourceMain findOneMain = findOneMains.get();
            if (Result == 0) {
                outRecordService.AddOne(SubID, "已提交至暂不报销。");
                findOneMain.setState(8);
            } else {
                outRecordService.AddOne(SubID, "已提交至费用报销。");
                findOneMain.setState(9);
            }
            findOneMain.setAuditResult(Memo);
            findOneMain.setAuditMan(Info.getUserIdValue());
            findOneMain.setAuditTime(new Date());
            casesOutMainRep.save(findOneMain);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AuditSettle(int Result, String Memo, String SubIDS) throws Exception {
        List<casesSub> items = new ArrayList<>();
        LoginUserInfo logInfo = CompanyContext.get();
        List<String> IDS = Arrays.asList(SubIDS.split(","));
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub sub = findOnes.get();
                if (Result == 1) sub.setProcessState(64);
                else sub.setProcessState(63);
                sub.setSettleTime(new Date());
                sub.setSettleMan(logInfo.getUserIdValue());
                sub.setSetttleManager(loginUserMapper.getManager(logInfo.getUserId()));
                sub.setSettleResult(Result);
                sub.setSettleMemo(Memo);
                items.add(sub);
                casesUserService.AddOne(sub.getCasesId(), logInfo.getUserIdValue());
                casesSubUserService.addOne(sub.getCasesId(), sub.getSubId(), logInfo.getUserIdValue());
                if (sub.getOutTech() == true) AddSomeSettle(ID, Result, Memo);
            }
        }
        updateCasesSubList(items);
    }

    @Transactional(rollbackFor = Exception.class)
    protected void AddSomeObject(casesSub sub, int Result, String Memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesOutSourceMain> findOneMains = casesOutMainRep.findFirstBySubId(sub.getSubId());
        if (findOneMains.isPresent()) {
            casesOutSourceMain findOneMain = findOneMains.get();
            if (Result == 0) {
                outTechService.AuditTechFiles(Arrays.asList(sub.getSubId()), "客户定稿驳回,原因:" + Memo, "0");
                List<casesSubFiles> audFiles = subFileRep.getAllBySubId(sub.getSubId())
                        .stream()
                        .filter(f -> f.getType().equals("Exp"))
                        .collect(toList());
                List<String> AS = audFiles.stream().map(f -> f.getAttId()).collect(toList());
                if (audFiles.size() > 0) {
                    for (int i = 0; i < audFiles.size(); i++) {
                        casesSubFiles audFile = audFiles.get(i);
                        Optional<casesOutSourceFile> findOuts = casesOutFileRep.getFirstByAttId(audFile.getAttId());
                        if (findOuts.isPresent() == false) {
                            casesOutSourceFile newFile = new casesOutSourceFile();
                            newFile.setOutId(findOneMain.getOutId());
                            newFile.setSubId(findOneMain.getSubId());
                            newFile.setType("Aud");
                            newFile.setAttId(audFile.getAttId());
                            newFile.setCreateMan(Info.getUserIdValue());
                            newFile.setCreateTime(new Date());
                            casesOutFileRep.save(newFile);
                            if (AS.contains(audFile.getAttId()) == false) AS.add(audFile.getAttId());
                        }
                    }
                    String es = StringUtils.join(AS, ',');
                    findOneMain.setState(6);
                    findOneMain.setAuditTechFiles(es);
                }

            } else {
                findOneMain.setAuditText(Memo);
                findOneMain.setAuditResult(Integer.toString(Result));
                findOneMain.setAuditTime(new Date());
                findOneMain.setState(7);
                outRecordService.AddOne(findOneMain.getSubId(), "技术审核通过");
            }
            casesOutMainRep.save(findOneMain);
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
            if (StateText.equals("All") == false) {
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

    public void ChangeSupportMan(String SubID, String SupportMan) {
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub sub = findSubs.get();
            sub.setSupportMan(SupportMan);
            subRep.save(sub);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void ChangeTechMan(int OldMan, int NewMan, String SubIDS) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Map<String, String> IDNameHash = loginUserService.getAllByIDAndName();
        List<String> SubIDArray = ListUtils.parse(SubIDS, String.class);
        for (int i = 0; i < SubIDArray.size(); i++) {
            String SubID = SubIDArray.get(i);
            Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);

            if (findSubs.isPresent()) {
                casesSub sub = findSubs.get();
                int OldTechMan = sub.getTechMan();
                String OldTechManager = sub.getTechManager();
                String NewTechManager = loginUserMapper.getManager(Integer.toString(NewMan));
                sub.setTechMan(NewMan);
                sub.setTechManager(NewTechManager);
                sub.setAcceptTechTime(new Date());
                subRep.save(sub);

                casesSubUserService.replaceOne(sub.getCasesId(), SubID, OldMan, NewMan, OldTechManager, NewTechManager);
                casesUserService.replaceOne(sub.getCasesId(), OldMan, NewMan, OldTechManager, NewTechManager);

                Optional<casesMain> findMains = mainRep.findFirstByCasesId(sub.getCasesId());
                if (findMains.isPresent()) {
                    casesMain main = findMains.get();
                    String NowTech = main.getTechMan();
                    String NowManager = main.getTechManager();

                    List<Integer> NowTechs = IntegerUtils.parseIntArray(NowTech);
                    List<Integer> NowManagers = IntegerUtils.parseIntArray(NowManager);

                    int X = NowTechs.indexOf(OldMan);
                    if (X > -1) NowTechs.remove(X);
                    NowTechs.add(NewMan);

                    NowManagers.removeAll(IntegerUtils.parseIntArray(OldTechManager));
                    NowManagers.addAll(IntegerUtils.parseIntArray(NewTechManager));

                    main.setTechMan(StringUtils.join(NowTechs, ","));
                    main.setTechManager(StringUtils.join(NowManagers, ","));
                    mainRep.save(main);
                }
                casesChangeRecord record = new casesChangeRecord();
                record.setCasesId(sub.getCasesId());
                record.setCreateTime(new Date());
                record.setMode("修改");
                record.setUserId(Info.getUserIdValue());
                String ChangeText =
                        sub.getSubNo() + "的技术人员由:【" + IDNameHash.get(Integer.toString(OldMan)) + "】,变更为【" + IDNameHash.get(Integer.toString(NewMan)) + "】。";
                record.setChangeText(ChangeText);
                changeRep.save(record);
            } else throw new Exception("要调整的业务交单对象已不存在!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeJGDate(String ID, String Field, Date Value) {
        LoginUserInfo Info = CompanyContext.get();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Optional<casesSub> findOne = subRep.getBySubId(ID);
        casesSub sub = new casesSub();
        if (findOne.isPresent()) {
            sub = findOne.get();
            sub.setJgDate(Value);
        }
        subRep.save(sub);

        Date jgdate = new Date();
        String tiem = sformat.format(jgdate);
        JGDateMemo jgDateMemo = new JGDateMemo();
        jgDateMemo.setCasesSubId(ID);
        if(Value!=null) {
            jgDateMemo.setMemo(Info.getUserName() + "将初稿完成日期设置为:"+sformat.format(Value));
        }  else {
            jgDateMemo.setMemo(Info.getUserName() + "清除了初稿完成日期设置。");
        }
        jgDateMemo.setCreateMan(Info.getUserIdValue());
        jgDateMemo.setCreateTime(jgdate);

        jgDateMemoRepository.save(jgDateMemo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeRequiredDate(String ID, Date Value) {
        LoginUserInfo Info = CompanyContext.get();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Optional<casesSub> findOne = subRep.getBySubId(ID);
        casesSub sub = new casesSub();
        Date oldDate = null;
        if (findOne.isPresent()) {
            sub = findOne.get();
            oldDate = sub.getClientRequiredDate();
            sub.setClientRequiredDate(Value);

            String changeText = "将客户要求申报绝限从【" + sformat.format(oldDate) + "】更改为【" + sformat.format(Value) + "】";
            String proText = sub.getProcessText();
            if (StringUtils.isEmpty(proText)) proText = "[]";
            List<simpleMemo> sms = JSON.parseArray(proText, simpleMemo.class);
            simpleMemo vv = new simpleMemo();
            vv.setCreateTime(new Date());
            vv.setMemo(changeText);
            vv.setCreateManName(Info.getUserName());
            sms.add(vv);
            sub.setProcessText(JSON.toJSONString(sms));
            subRep.save(sub);
        }
    }

    public List<Map<String,Object>>getWorkDays(Map<String,Object> params){
       return  browseTechMapper.getWorkDays(params);
    }

    @Override
    public void CaseSubIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();

        List<casesSub> listCasesSub = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        subRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            casesSub casesSub = new casesSub();
            casesSub = f;

            String Record = "";
            String AssignTechManRecord = "";
            String TechManRecord = "";
            String TechManagerRecord = "";
            String TechAuditManRecord = "";
            String TechAuditManagerRecord = "";
            String CommitTechManRecord = "";
            String AuditTechManRecord = "";
            String AuditManagerRecord = "";
            String ClientSetManRecord = "";
            String TechSBManRecord = "";
            String SettleManRecord = "";
            String SetttleManagerRecord = "";
            String CreateManRecord = "";
            String SupportManRecord = "";
            String CancelManRecord = "";
            String StatusChangeManRecord = "";

            //AssignTechMan 指定技术人员
            if (f.getAssignTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAssignTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                AssignTechManRecord = iar.getRecord();
                casesSub.setAssignTechMan(iar.getId());
            }

            //接单技术人员
            if (f.getTechMan() != null) {
                if (f.getTechMan() == Resignation) {
                    casesSub.setTechMan(Transfer);
                    TechManRecord = "接单技术人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //接单技术人员经理
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                casesSub.setTechManager(iar.getId());
            }

            //指定核稿人
            if (f.getTechAuditMan() != null) {
                if (f.getTechAuditMan() == Resignation) {
                    casesSub.setTechAuditMan(Transfer);
                    TechAuditManRecord = "指定核稿人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //指定核稿人经理
            if (f.getTechAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechAuditManagerRecord = iar.getRecord();
                casesSub.setTechAuditManager(iar.getId());
            }

            //技审提交人
            if (f.getCommitTechMan() != null) {
                if (f.getCommitTechMan() == Resignation) {
                    casesSub.setCommitTechMan(Transfer);
                    CommitTechManRecord = "技审提交人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //实际技审人
            if (f.getAuditTechMan() != null) {
                if (f.getAuditTechMan() == Resignation) {
                    casesSub.setAuditTechMan(Transfer);
                    AuditTechManRecord = "实际技审人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //实际技审经理
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                casesSub.setAuditManager(iar.getId());
            }

            //商务人员
            if (f.getClientSetMan() != null) {
                if (f.getClientSetMan() == Resignation) {
                    casesSub.setClientSetMan(Transfer);
                    ClientSetManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //技术申报人员
            if (f.getTechSbMan() != null) {
                if (f.getTechSbMan() == Resignation) {
                    casesSub.setTechSbMan(Transfer);
                    TechSBManRecord = "技术申报人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //提成报销人
            if (f.getSettleMan() != null) {
                if (f.getSettleMan() == Resignation) {
                    casesSub.setSettleMan(Transfer);
                    SettleManRecord = "提成报销人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //提成报销人经理
            if (f.getSetttleManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSettleManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                SetttleManagerRecord = iar.getRecord();
                casesSub.setSettleMan(Integer.parseInt(iar.getId()));
            }

            //创建人
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    casesSub.setCreateMan(Transfer);
                    CreateManRecord = "创建人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //SupportMan 技术支持人员
            if (f.getSupportMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSupportMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                SupportManRecord = iar.getRecord();
                casesSub.setSupportMan(iar.getId());
            }

            //CannelMan 业务中止操作人员
            if (f.getCancelMan() != null) {
                if (f.getCancelMan() == Resignation) {
                    casesSub.setCancelMan(Transfer);
                    CancelManRecord = "CancelMan由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //StatusChangeMan  业务完成状态操作人员
            if (f.getStatusChangeMan() != null) {
                if (f.getStatusChangeMan() == Resignation) {
                    casesSub.setStatusChangeMan(Transfer);
                    StatusChangeManRecord = "StatusChangeManRecord：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listCasesSub.add(casesSub);
            Record = AssignTechManRecord + TechManRecord + TechManagerRecord + AuditManagerRecord + TechManRecord + TechManagerRecord + TechAuditManRecord + TechAuditManagerRecord +
                    CommitTechManRecord + AuditTechManRecord + AuditManagerRecord + ClientSetManRecord + TechSBManRecord + SettleManRecord + SetttleManagerRecord + CreateManRecord +
                    SupportManRecord + CancelManRecord + StatusChangeManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseSubChange");
                crecord.setChangeText(Record);
                crecord.setUserId(Info.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });

        if (listCasesChangeRecord.size() > 0) {
            changeRep.saveAll(listCasesChangeRecord);
        }

        if (listCasesSub.size() > 0) {
            subRep.saveAll(listCasesSub);
        }
    }

    private IdAndRecord GetAssignTechMan(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String assignTechMan = f.getAssignTechMan();
        String result = "";
        String[] assignTechMans = assignTechMan.split(",");
        for (String strAssignTechMan : assignTechMans) {
            if (strAssignTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("AssignTechMan由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAssignTechMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManagers : techManagers) {
            if (strTechManagers.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("接单技术人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechManagers + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechAuditManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techAuditManager = f.getTechAuditManager();
        String result = "";
        String[] techAuditManagers = techAuditManager.split(",");
        for (String strTechAuditManager : techAuditManagers) {
            if (strTechAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("指定核稿人经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("流程审核人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSettleManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String settleManager = f.getSetttleManager();
        String result = "";
        String[] settleManagers = settleManager.split(",");
        for (String strSettleManager : settleManagers) {
            if (strSettleManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("提成报销人经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strSettleManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSupportMan(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String supportMan = f.getSupportMan();
        String result = "";
        String[] supportMans = supportMan.split(",");
        for (String strSupportMan : supportMans) {
            if (strSupportMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("SupportMan由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strSupportMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    public class IdAndRecord {
        private String Id;
        private String Record;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getRecord() {
            return Record;
        }

        public void setRecord(String record) {
            Record = record;
        }
    }
}
