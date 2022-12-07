package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.autoTask.CaseMainCacheClearTask;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.FeeItemMemoMapper;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.ViewPatentInfoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.ManyInfoMemoCreator;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.IAllUserListService;
import com.zhide.dtsystem.services.define.IPatentInfoService;
import com.zhide.dtsystem.services.define.ITZSEmailService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatentInfoServiceImpl implements IPatentInfoService {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ViewPatentInfoMapper patentInfoMapper;
    @Autowired
    ITZSEmailService tzsEmail;

    @Autowired
    pantentInfoRepository pantentInfoRep;
    @Autowired
    PantentInfoMemoMapper infoMemoMapper;
    @Autowired
    FeeItemMemoMapper feeItemMemoMapper;
    @Autowired
    patentInfoPermissionRepository pInfo;
    @Autowired
    casesSubRepository caseSubRep;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    casesSubFilesRepository casesSubFilesRepository;
    @Autowired
    tbAttachmentRepository attachmentRepository;
    @Autowired
    IAllUserListService allMapper;

    Logger logger= LoggerFactory.getLogger(CaseMainCacheClearTask.class);

    @Override
    @Cacheable(value="GetLoginUserHash",keyGenerator = "CompanyKeyGenerator")
    public Map<String, String> GetLoginUserHash() {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = loginUserMapper.getAllByIDAndName();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            result.put(row.get("Name").toString(), row.get("ID").toString());
        }
        return result;
    }

    @Override
    public pageObject getData(Map<String, Object> parameters) {
        pageObject object = new pageObject();
        List<Map<String, Object>> datas = patentInfoMapper.getData(parameters);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("SHENQINGH").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            List<v_PantentInfoMemo> yearfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Year", SIDS);
            List<v_PantentInfoMemo> applyfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Apply", SIDS);
            List<casesSub> listCasesSub = caseSubRep.findAll();
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, memos, yearfeememos, applyfeememos, listCasesSub);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
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
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
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
    @Transactional(rollbackFor = Exception.class)
    public void ChangeValue(List<String> IDS, String field, Object value) throws Exception {
        for (int i = 0; i < IDS.size(); i++) {
            String shenqingh = IDS.get(i);
            Optional<pantentInfo> Infos = pantentInfoRep.findById(shenqingh);
            if (Infos.isPresent()) {
                pantentInfo Info = Infos.get();
                EntityHelper.changeValue(Info, field, value);
                pantentInfoRep.save(Info);
                if (field.toLowerCase().equals("neibubh")) {
                    if (ObjectUtils.isEmpty(value) == false) {
                        String XCode = value.toString();
                        NBBHInfo getInfo = NBBHCode.Parse(XCode);
                        pInfo.deleteAllByShenqingh(shenqingh);
                        getInfo.foreach((type, items) -> {
                            if (type.equals("BH") == false) {
                                for (int n = 0; n < items.size(); n++) {
                                    UInfo item = items.get(n);
                                    List<patentInfoPermission> all = pInfo
                                            .findAllByShenqinghAndUsertypeAndUserid(shenqingh, type, item.getID());
                                    if (all.size() == 0) {
                                        patentInfoPermission px = new patentInfoPermission();
                                        px.setUsertype(type);
                                        px.setUserid(item.getID());
                                        px.setShenqingh(shenqingh);
                                        pInfo.save(px);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<v_PantentInfoMemo> memos, List<v_PantentInfoMemo> yearfeememos, List<v_PantentInfoMemo> applyfeememos, List<casesSub> listCaseseSub) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("SHENQINGH").toString();
        String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));
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
                    Optional<casesSub> findCasesSub = listCaseseSub.stream().filter(f -> f.getSubNo().equals(FU.getName())).findFirst();
                    if (findCasesSub.isPresent()) {
                        row.put("Att","1");
                        row.put("SubID",findCasesSub.get().getSubId());
                    }else {
                        //有BH，CasesSub表里SubNo不存在这个BH
                        row.put("Att","0");
                    }
                }
            }
        });
        return row;
    }

    public File download(String[] Codes) throws Exception {
        File res = null;
        List<pantentInfo> pss = pantentInfoRep.findAllByShenqinghIn(Codes);
        FTPUtil Ftp = new FTPUtil();
        String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        File fDir = new File(dirName);
        if (fDir.exists() == false) {
            fDir.mkdirs();
        }
        if (pss.size() > 1) {
            if (Ftp.connect() == true) {
                for (int i = 0; i < pss.size(); i++) {
                    pantentInfo tzs = pss.get(i);
                    String tzsPath = tzs.getUploadPath();
                    String famingmc = tzs.getFamingmc();
                    String baseName = famingmc;
                    String fileName = FileNameUtils.clear(baseName) + ".zip";
                    String createFile = Paths.get(dirName, fileName).toString();
                    Ftp.download("//" + tzsPath, createFile);
                }
                String ZipFilePath = Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() +
                        ".zip").toString();
                ZipFileUtils.zip(dirName, ZipFilePath);
                FileUtils.deleteDirectory(fDir);
                res = new File(ZipFilePath);
            }
        } else {
            if (Ftp.connect() == true) {
                pantentInfo tzs = pss.get(0);
                String tzsPath = tzs.getUploadPath();
                String famingmc = tzs.getFamingmc();
                String baseName = famingmc;
                String fileName = FileNameUtils.clear(baseName) + ".zip";
                String createFile = Paths.get(dirName, fileName).toString();
                Ftp.download("//" + tzs.getUploadPath(), createFile);
                res = new File(createFile);
            }
        }
        return res;
    }

    public File alldownload(String[] Codes) throws Exception {
        File res = null;
        List<String> listCodes = Arrays.asList(Codes);
        Map<String, Object> param = new HashMap<>();
        param.put("ids", listCodes);
        List<Map<String,Object>> css = patentInfoMapper.getSubFileAndOutFile(param);
        FTPUtil Ftp = new FTPUtil();
        String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        File fDir = new File(dirName);
        if (fDir.exists() == false){
            fDir.mkdirs();
        }
        if (Ftp.connect() == true){
            if (css.size() > 0) {
                List<String> listSubID = new ArrayList<>();
                List<String> listType = new ArrayList<>();
                for (int i = 0; i < css.size(); i++) {
                    listSubID.add(css.get(i).get("AttID").toString());
                    listType.add(css.get(i).get("Type").toString());
                }
                if (listSubID.size() > 0) {
                    List<tbAttachment> listAtt = attachmentRepository.findAllByGuidIn(listSubID);
                    if (listAtt.size() > 0) {
                        for (int x = 0; x < listAtt.size(); x++) {
                            tbAttachment attachment = listAtt.get(x);
                            String Type = listType.get(x);
                            String CHN = getTypeCHN(Type);
                            String path = attachment.getPath();
                            String famingmc = attachment.getName();
                            String[] exts = famingmc.split("\\.");
                            String extName = exts[exts.length - 1];
                            String createFile = Paths.get(dirName, exts[0] + "-" + CHN + "." + extName).toString();
                            Ftp.download("//" + path, createFile);
                        }
                    }
                }
            }
            String ZipFilePath = Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() + ".zip").toString();
            ZipFileUtils.zip(dirName, ZipFilePath);
            FileUtils.deleteDirectory(fDir);
            res = new File(ZipFilePath);
        }

        return res;
    }
    private String getTypeCHN(String Type){
        String CHN = "";
        if (!Type.equals("")) {
            switch (Type) {
                case "Zl":
                    CHN = "著录信息资料";
                    break;
                case "Accept":
                    CHN = "专利申报文件";
                    break;
                case "Exp":
                    CHN = "情况说明文件";
                    break;
                case "Aud":
                    CHN = "内审驳回说明文件";
                    break;
                case "Out":
                    CHN = "专利协作文件";
                    break;
                case "Tech":
                    CHN = "技术文档";
                    break;
                default:
                    CHN = "";
                    break;
            }
        }
        return CHN;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean ImportData(Map<String, Object> row) throws Exception {
        pantentInfo p = EntityHelper.copyFrom(row, pantentInfo.class);
        String pId =StringUtils.trim(p.getShenqingh());
        String NBBH="";
        if (StringUtils.isEmpty(pId) == false) {
            Optional<pantentInfo> fxx = pRep.findByShenqingh(pId);
            if (fxx.isPresent() == false) {
                p.setShenqingh(pId);
                p.setShenqingbh(UUID.randomUUID().toString());
                NBBH=p.getNeibubh();
                pRep.save(p);
            } else {
                pantentInfo px = fxx.get();
                px.setShenqingrxm(p.getShenqingrxm());
                px.setShenqinglx(p.getShenqinglx());
                px.setFamingmc(p.getFamingmc());
                px.setNeibubh(p.getNeibubh());
                px.setShenqingr(p.getShenqingr());
                px.setFamingrxm(p.getFamingrxm());
                if (StringUtils.isEmpty(p.getAnjianywzt()) == false) px.setAnjianywzt(p.getAnjianywzt());
                if (StringUtils.isEmpty(p.getDailijgmc()) == false) px.setDailijgmc(p.getDailijgmc());
                pRep.save(px);
                NBBH=px.getNeibubh();
            }
            if(StringUtils.isEmpty(NBBH)==false){
                NBBHInfo getInfo = NBBHCode.Parse(NBBH);
                pInfo.deleteAllByShenqingh(p.getShenqingh());
                String shenqingh=p.getShenqingh();
                getInfo.foreach((type, items) -> {
                    if (type.equals("BH") == false) {
                        for (int n = 0; n < items.size(); n++) {
                            UInfo item = items.get(n);
                            patentInfoPermission px = new patentInfoPermission();
                            px.setUsertype(type);
                            px.setUserid(item.getID());
                            px.setShenqingh(shenqingh);
                            pInfo.save(px);
                        }
                    }
                });
            }
        }
        return true;
    }

    @Override
    @CacheableTtl(value = "getPantentTotal", ttl = 300)
    public List<Map<String, Object>> getPantentTotal(int DepID, int UserID, String RoleName) {
        return patentInfoMapper.getPantentTotal(DepID,UserID,RoleName);
    }

    @Override
    public void ChangeNBBH(Integer Transfer, Integer Resgination) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String loginResignation = loginUserMapper.getLoginUserNameById(Resgination);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        String resignationXS = "XS:" + loginResignation;
        String resignationLC = "LC:" + loginResignation;
        String resginationJS = "JS:" + loginResignation;

        String transferXS = "XS:" + loginTransfer;
        String transferLC = "LC:" + loginTransfer;
        String trnasferJS = "JS:" + loginTransfer;

        List<pantentInfo> listPantentInfo = new ArrayList<>();

        pantentInfoRep.findAll().stream().forEach(f -> {
            pantentInfo pantentInfo= new pantentInfo();
            if (f.getNeibubh() != null) {
                String NEIBBH = f.getNeibubh();
                NEIBBH = NEIBBH.replace("XS-","XS:").replace("LC-","LC:").replace("JS-","JS:");
                if (NEIBBH.indexOf(resignationXS) > -1) {
                    NEIBBH = NEIBBH.replace(resignationXS, transferXS);
                }
                if (NEIBBH.indexOf(resignationLC) > -1) {
                    NEIBBH = NEIBBH.replace(resignationLC, transferLC);
                }
                if (NEIBBH.indexOf(resginationJS) > -1) {
                    NEIBBH = NEIBBH.replace(resginationJS, trnasferJS);
                }
                pantentInfo = f;
                pantentInfo.setNeibubh(NEIBBH);
                listPantentInfo.add(pantentInfo);
            }
        });
        pantentInfoRep.saveAll(listPantentInfo);
    }

    @Override
    public void PatentInfoPermissionChangeUserID(Integer Transfer, Integer Resgination) throws Exception {
        List<patentInfoPermission> listPatentInfoPermission = new ArrayList<>();
        pInfo.findAll().stream().forEach(f -> {
            if (!f.getUsertype().equals("KH") && f.getUserid() == Resgination) {
                patentInfoPermission patentInfoPermission = new patentInfoPermission();
                patentInfoPermission = f;
                patentInfoPermission.setUserid(Transfer);
                listPatentInfoPermission.add(patentInfoPermission);
            }
        });
        pInfo.saveAll(listPatentInfoPermission);
    }
}
