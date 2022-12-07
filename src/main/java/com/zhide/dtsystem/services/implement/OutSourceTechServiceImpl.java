package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.OutTechMainBrowseMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.simpleMemoCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: OutSourceTechServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年10月21日 23:01
 **/
@Service
public class OutSourceTechServiceImpl implements IOutSourceTechService {
    @Autowired
    casesMainRepository casesMainRep;
    @Autowired
    casesSubRepository casesSubRep;
    @Autowired
    casesSubFilesRepository casesFileRep;
    @Autowired
    casesOutSourceMainRepository casesOutMainRep;
    @Autowired
    casesOutSourceFileRepository casesOutFileRep;
    @Autowired
    OutTechMainBrowseMapper outTechMapper;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    IOutTechRecordService outRecordService;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    ICasesSubUserService casesSubUserService;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ICasesTechBrowseNewService caseTechService;


    Logger logger= LoggerFactory.getLogger(OutSourceTechServiceImpl.class);
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 新增一个专利外协记录。
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean AddOutSourceTask(List<String> IDS, Integer OutTechMan) {
        LoginUserInfo info = CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String SubID = IDS.get(i);
            Optional<casesSub> subOne = casesSubRep.findFirstBySubId(SubID);
            if (subOne.isPresent()) {
                casesSub mainSub = subOne.get();
                AddSingleOutSource(mainSub, OutTechMan);
                mainSub.setOutTech(true);
                mainSub.setOutTechMan(info.getUserIdValue());
                mainSub.setOutTechTime(new Date());
                casesSubRep.save(mainSub);
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean CancelOutTech(List<String> IDS) {
        LoginUserInfo Info = CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<casesSub> findOnes = casesSubRep.findFirstBySubId(ID);
            if (findOnes.isPresent()) {
                casesSub main = findOnes.get();
                main.setOutTech(false);
                main.setOutTechMan(0);
                main.setCancelTechTime(new Date());
                casesSubRep.save(main);
                casesOutMainRep.deleteAllBySubId(ID);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public void ChangeJS(List<String> SubIDs, Integer NewMan) throws Exception {
        for(String SubID:SubIDs){
            Optional<casesOutSourceMain> findMains=casesOutMainRep.findFirstBySubId(SubID);
            if(findMains.isPresent()) {
                casesOutSourceMain main=findMains.get();
                int state=main.getState();
                if(state!=3) throw new Exception("当前操作的对象状态已发生了变化，请刷新后再重试!");
                Optional<casesSub> findSubs=casesSubRep.findFirstBySubId(SubID);
                if(findSubs.isPresent()){
                    casesSub sub=findSubs.get();
                    Integer OldMan=sub.getTechMan();
                    caseTechService.ChangeTechMan(OldMan, NewMan, SubID);
                } else throw new Exception("当前操作的业务对象"+SubID+"已不存在,请刷新后再进行操作!");
            } else throw new Exception("当前操作的业务对象"+SubID+"已不存在,请刷新后再进行操作!");
        }
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = outTechMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            List<String> SubIDS = datas.stream().map(f -> f.get("SubID").toString()).collect(toList());
            Map<String, String> TechFileNames = getSubFileName(SubIDS, "Tech");
            Map<String, String> OutFileNames = getSubFileName(SubIDS, "Out");
            Map<String, String> AudFileNames = getSubFileName(SubIDS, "Aud");
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> data = datas.get(i);
                data.remove("_TotalNum");
                String SubID = data.get("SubID").toString();
                if (TechFileNames.containsKey(SubID)) {
                    String TechFileName = TechFileNames.get(SubID);
                    if (StringUtils.isEmpty(TechFileName) == false) {
                        data.put("TechFileName", TechFileName);
                    }
                }
                if (OutFileNames.containsKey(SubID)) {
                    String OutFileName = OutFileNames.get(SubID);
                    if (StringUtils.isEmpty(OutFileName) == false) {
                        data.put("OutTechFileName", OutFileName);
                    }
                }

                if (AudFileNames.containsKey(SubID)) {
                    String AudFileName = AudFileNames.get(SubID);
                    if (StringUtils.isEmpty(AudFileName) == false) {
                        data.put("AuditTechFileName", AudFileName);
                    }
                }
                Object OP = data.get("ProcessText");
                if (ObjectUtils.isEmpty(OP) == false) {
                    String ProcessText = OP.toString();
                    List<simpleMemo> memos = JSON.parseArray(ProcessText, simpleMemo.class);
                    List<String> TT = new ArrayList<>();
                    for (int n = 0; n < memos.size(); n++) {
                        simpleMemo memo = memos.get(n);
                        String V = simpleMemoCreator.createSingle(n + 1, memo);
                        TT.add(V);
                    }

                    if (TT.size() > 0) {
                        data.put("EDITMODE", 1);
                        data.put("ProcessMemo", StringUtils.join(TT, "<br/><br/>"));
                    }
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

            }
            object.setData(datas);
        }
        return object;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void AcceptTech(List<String> SubIDS) {
        LoginUserInfo info = CompanyContext.get();
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            Optional<casesOutSourceMain> findOnes = casesOutMainRep.findFirstBySubId(SubID);
            if (findOnes.isPresent()) {
                casesOutSourceMain main = findOnes.get();
                main.setState(2);
                main.setTechMan(info.getUserIdValue());
                main.setTechTime(new Date());
                main.setCanUse(true);
                casesOutMainRep.save(main);
                outRecordService.AddOne(SubID, "认领了专利外协:" + main.getOutId());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void RejectTech(List<String> SubIDS) {
        LoginUserInfo info = CompanyContext.get();
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            Optional<casesOutSourceMain> findOnes = casesOutMainRep.findFirstBySubId(SubID);
            if (findOnes.isPresent()) {
                casesOutSourceMain main = findOnes.get();
                main.setState(1);
                main.setTechMan(null);
                main.setTechTime(null);
                main.setRejectMan(info.getUserIdValue());
                main.setRejectTime(new Date());
                main.setCanUse(false);
                casesOutMainRep.save(main);
                outRecordService.AddOne(SubID, "取消认领专利外协:" + main.getOutId());
            }
        }
    }

    @Override
    public List<casesOutSourceFile> GetSubFile(String SubID, String Type) {
        List<String> IDS = Arrays.asList(SubID);
        List<casesOutSourceFile> Files = casesOutFileRep.getAllBySubIdInAndType(IDS, Type);
        return Files;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveSubFiles(String SubID, String OutID, String AttID, String Type) throws  Exception {
        LoginUserInfo info = CompanyContext.get();
        if(StringUtils.isEmpty(AttID)) throw new Exception(AttID+"不能为空!");
        Optional<casesOutSourceFile> findOnes = casesOutFileRep.getFirstByAttId(AttID);
        casesOutSourceFile subMain = null;
        if (findOnes.isPresent() == false) {
            subMain = new casesOutSourceFile();
            subMain.setCreateTime(new Date());
            subMain.setCreateMan(info.getUserIdValue());
        } else subMain = findOnes.get();
        subMain.setAttId(AttID);
        subMain.setSubId(SubID);
        subMain.setOutId(OutID);
        subMain.setType(Type);
        casesOutFileRep.save(subMain);

        Optional<casesOutSourceMain> findMains = casesOutMainRep.findFirstBySubId(SubID);
        if (findMains.isPresent()) {
            casesOutSourceMain main = findMains.get();
            if(Type.equals("Out")) {
                List<casesOutSourceFile> files = casesOutFileRep.getAllBySubIdInAndType(Arrays.asList(SubID), "Out")
                        .stream()
                        .sorted(Comparator.comparing(casesOutSourceFile::getCreateTime).reversed())
                        .collect(toList());
                if (files.size() > 0) {
                    List<String> Files = files.stream().map(f -> f.getAttId()).collect(toList());
                    main.setOutTechFiles(StringUtils.join(Files, ","));
                    main.setOutTechFileUploadTime(files.get(0).getCreateTime());
                    outRecordService.AddOne(SubID, "上传了初稿文件:" + StringUtils.join(Files, ","));
                }
            }
            if(Type.equals("Aud")){
                main.setAuditTechFiles(AttID);
                outRecordService.AddOne(SubID, "上传了审核不通过说明文件:" + AttID);
            }
            casesOutMainRep.save(main);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean RemoveSubFile(String AttID) throws Exception {
        Optional<casesOutSourceFile> findOnes = casesOutFileRep.getFirstByAttId(AttID);
        if(findOnes.isPresent()){
            casesOutSourceFile file=findOnes.get();
            String Type=file.getType();
            String SubID=file.getSubId();
            Optional<casesOutSourceMain> findMains=casesOutMainRep.findFirstBySubId(SubID);
            if(findMains.isPresent()){
                casesOutSourceMain main=findMains.get();
                if(Type.equals("Out")){
                    String OutFile=main.getOutTechFiles();
                    if(StringUtils.isEmpty(OutFile))OutFile="";
                    OutFile=OutFile.replace(AttID,"");
                    main.setOutTechFiles(OutFile);
                }
                else if(Type.equals("Aud")){
                    String AudFile=main.getAuditTechFiles();
                    if(StringUtils.isEmpty(AudFile))AudFile="";
                    AudFile=AudFile.replace(AttID,"");
                    main.setAuditTechFiles(AudFile);
                }
                casesOutMainRep.save(main);
            }
            casesOutFileRep.delete(file);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean CommitTech(String SubID) {
        LoginUserInfo info = CompanyContext.get();
        Optional<casesOutSourceMain> findOnes = casesOutMainRep.findFirstBySubId(SubID);
        casesOutSourceMain main = null;
        if (findOnes.isPresent()) {
            main = findOnes.get();
            main.setState(3);
            main.setCommitMan(info.getUserIdValue());
            main.setCommitTime(new Date());

            List<casesOutSourceFile> files = casesOutFileRep.getAllBySubIdInAndType(Arrays.asList(SubID), "Out")
                    .stream()
                    .sorted(Comparator.comparing(casesOutSourceFile::getCreateTime).reversed())
                    .collect(toList());
            if (files.size() > 0) {
                List<String> Files = files.stream().map(f -> f.getAttId()).collect(toList());
                main.setOutTechFiles(StringUtils.join(Files, ","));
                main.setOutTechFileUploadTime(files.get(0).getCreateTime());
            }
            casesOutMainRep.save(main);
            outRecordService.AddOne(SubID, "将初稿文件提交审核");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean AuditTechFiles(List<String> SubIDS, String Text, String Result) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            Optional<casesOutSourceMain> findOnes = casesOutMainRep.findFirstBySubId(SubID);
            if (findOnes.isPresent()) {
                casesOutSourceMain main = findOnes.get();
                if(main.getTechMan()==null) throw new Exception("该业务没有指定处理的业务人员，无法审核!");
                main.setAuditMan(Info.getUserIdValue());
                main.setAuditTime(new Date());
                main.setAuditResult(Result);
                main.setAuditText(Text);
                if (Result.equals("1")) {
                    main.setState(5);
                    if (Text.indexOf(":") > -1) {
                        outRecordService.AddOne(SubID, Text);
                    } else outRecordService.AddOne(SubID, "初稿文件被审核通过,审核说明:" + Text);

                } else {
                    main.setState(4);
                    if (Text.indexOf(":") > -1) {
                        outRecordService.AddOne(SubID, Text);
                    } else outRecordService.AddOne(SubID, "初稿文件被驳回,审核说明:" + Text);
                }

                if (Result.equals("1")) {
                    List<casesOutSourceFile> ownerFiles = casesOutFileRep.getAllBySubIdInAndType(Arrays.asList(SubID), "Out");
                    if (ownerFiles.size() == 0) {
                        throw new Exception("提交的业务没有初稿文件，不能审核通过!");
                    } else {
                        Optional<casesSub> findMains = casesSubRep.findFirstBySubId(main.getSubId());
                        if (findMains.isPresent()) {
                            casesSub onlyOne = findMains.get();
                            String AcFile = onlyOne.getAcceptTechFiles();
                            List<String> Ms = new ArrayList<>();

                            if (StringUtils.isEmpty(AcFile) == false) {
                                Ms = Arrays.stream(AcFile.split(",")).collect(toList());
                            }
                            for (int a = 0; a < ownerFiles.size(); a++) {
                                casesOutSourceFile file = ownerFiles.get(a);
                                String AttID = file.getAttId();
                                Optional<casesSubFiles> findFiles = casesFileRep.findFirstByAttId(AttID);
                                if (findFiles.isPresent() == false) {
                                    casesSubFiles newFile = new casesSubFiles();
                                    newFile.setCasesId(main.getCasesId());
                                    newFile.setSubId(main.getSubId());
                                    newFile.setAttId(AttID);
                                    newFile.setType("Accept");
                                    newFile.setCreateMan(Info.getUserIdValue());
                                    newFile.setCreateTime(new Date());
                                    casesFileRep.save(newFile);
                                }
                                if (Ms.contains(AttID) == false) Ms.add(AttID);
                            }
                            onlyOne.setAcceptTechFiles(StringUtils.join(Ms, ","));
                            onlyOne.setAuditTechTime(new Date());
                            onlyOne.setAuditTechMemo("自动审核通过");
                            onlyOne.setAuditTechMan(Info.getUserIdValue());
                            onlyOne.setProcessState(54);
                            casesSubRep.save(onlyOne);

                            List<Integer>Managers=
                                    IntegerUtils.parseIntArray(loginUserMapper.getManager(Info.getUserId()));
                            for(Integer Manager:Managers){
                                casesUserService.AddOne(onlyOne.getCasesId(),Manager);
                                casesSubUserService.addOne(onlyOne.getCasesId(),onlyOne.getSubId(),Manager);
                            }
                        }
                    }
                } else {
                    if(i>0){
                        String firstSubID=SubIDS.get(0);
                        Optional<casesOutSourceMain> firstOnes = casesOutMainRep.findFirstBySubId(firstSubID);
                        if (findOnes.isPresent()) {
                            casesOutSourceMain firstOne= firstOnes.get();
                            main.setAuditTechFiles(firstOne.getAuditTechFiles());
                        }
                    }
                    //审核不通过时，将技术文件设置为历史记录。
                    List<casesOutSourceFile> outFiles=casesOutFileRep.getAllBySubIdAndType(SubID,"Out");
                    List<casesOutSourceFile> audFiles=casesOutFileRep.getAllBySubIdAndType(SubID,"Aud");

                    outFiles.addAll(audFiles);
                    for(casesOutSourceFile file:outFiles){
                        String AttID=file.getAttId();
                        Optional<tbAttachment> findFiles=attRep.findAllByGuid(AttID);
                        if(findFiles.isPresent()){
                            tbAttachment findFile=findFiles.get();
                            findFile.setType(2);
                            attRep.save(findFile);
                        }
                    }
                }
                casesOutMainRep.save(main);
            } else throw new Exception("审核的业务已不存在!");
        }
        return true;
    }

    @Override
    public Map<Integer, Integer> GetStateNumber() {
        Map<Integer, Integer> X = new HashMap<>();
        X.put(0, 0);
        X.put(1, 0);
        X.put(2, 0);
        X.put(3, 0);
        X.put(4, 0);
        X.put(5, 0);
        X.put(6, 0);
        X.put(7, 0);
        X.put(8, 0);
        X.put(9, 0);
        LoginUserInfo Info = CompanyContext.get();
        List<Map<Integer, Integer>> Nums = outTechMapper.getStateNumbers(Info.getRoleName(),Info.getUserIdValue());
        int Total = 0;
        for (int i = 0; i < Nums.size(); i++) {
            Map<Integer, Integer> SingleNum = Nums.get(i);
            int State = SingleNum.get("State");
            int Num = SingleNum.get("Num");
            if (X.containsKey(State)) {
                int NN = X.get(State);
                int NowNum = NN + Num;
                X.replace(State, NowNum);
                Total += Num;
            }
        }
        X.replace(0, Total);
        return X;
    }

    private Map<String, String> getSubFileName(List<String> SubIDS, String FType) {
        Map<String, String> Res = new HashMap<>();
        List<casesOutSourceFile> SubFiles = casesOutFileRep.getAllBySubIdInAndType(SubIDS, FType);
        List<String> AttIDS = SubFiles.stream().map(f -> f.getAttId()).collect(toList());
        List<tbAttachment> AttFiles = attRep.findAllByGuidIn(AttIDS);
        for (int i = 0; i < SubIDS.size(); i++) {
            String SubID = SubIDS.get(i);
            List<casesOutSourceFile> sFiles =
                    SubFiles.stream().filter(f -> f.getSubId().equals(SubID)).collect(toList());
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

    private void AddSingleOutSource(casesSub subOne, Integer OutTechMan) {
        String CasesID = subOne.getCasesId();
        String SubID = subOne.getSubId();
        LoginUserInfo info = CompanyContext.get();
        casesOutSourceMain main = new casesOutSourceMain();
        main.setOutId(UUID.randomUUID().toString());
        main.setCreateMan(info.getUserIdValue());
        main.setCreateTime(new Date());
        main.setSubId(SubID);
        main.setProcessText(subOne.getProcessText());
        if (OutTechMan > 0) main.setTechMan(OutTechMan);
        main.setCasesId(CasesID);
        main.setCanUse(true);
        main.setState(1);

        casesOutMainRep.save(main);
        AddOutSourceFiles(SubID, main.getOutId());
    }

    private void AddOutSourceFiles(String SubID, String OutID) {
        LoginUserInfo info = CompanyContext.get();
        List<casesSubFiles> Files = casesFileRep.getAllBySubIdAndType(SubID, "Tech");
        for (int i = 0; i < Files.size(); i++) {
            casesSubFiles file = Files.get(i);
            casesOutSourceFile newFile = new casesOutSourceFile();
            newFile.setAttId(file.getAttId());
            newFile.setOutId(OutID);
            newFile.setSubId(SubID);
            newFile.setType("Tech");
            newFile.setCreateMan(info.getUserIdValue());
            newFile.setCreateTime(new Date());
            casesOutFileRep.save(newFile);
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
            if (StateText.equals("0") == false) {
                params.put("State", StateText);
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
}
