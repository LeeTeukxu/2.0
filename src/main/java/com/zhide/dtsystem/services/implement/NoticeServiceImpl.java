package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.FeeItemMemoMapper;
import com.zhide.dtsystem.mapper.NoticeMiddleFileMapper;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.noticeMiddleFileRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.ManyInfoMemoCreator;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.INoticeService;
import com.zhide.dtsystem.services.define.ITZSEmailService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    ViewTZSMapper tzsMapper;

    @Autowired
    ITZSEmailService tzsEmail;

    @Autowired
    NoticeMiddleFileMapper middleFileMapper;
    @Autowired
    noticeMiddleFileRepository middleRep;

    @Autowired
    PantentInfoMemoMapper infoMemoMapper;
    @Autowired
    FeeItemMemoMapper feeItemMemoMapper;

    @Autowired
    TZSRepository tzsRep;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    tbAttachmentRepository attRep;

    Logger logger= LoggerFactory.getLogger(INoticeService.class);
    @Override
    public pageObject getData(String Type, Map<String, Object> params) {
        pageObject object = new pageObject();
        LoginUserInfo Info=CompanyContext.get();
        //List<Map<String, Object>> datas = tzsMapper.getData(params);
        Long T=System.currentTimeMillis();
        logger.info("查询开始.....");
        List<Map<String,Object>> datas= tzsMapper.getQuickData(params);
        int Total = tzsMapper.getQuickCount(params);
        Long T1=System.currentTimeMillis();
        logger.info("查询结束,当前角色:"+Info.getCompanyName()+"的"+Info.getRoleName()+",用时:"+Long.toString(T1-T)+"毫秒.");
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            //Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> IDS = datas.stream().map(f -> f.get("TONGZHISBH").toString()).collect(toList());
            List<String> SIDS = datas.stream().map(f -> f.get("SHENQINGH").toString()).collect(toList());
            Map<String, Object> tzsEmailRecord =new HashMap<>();// tzsEmail.getAll(IDS);
            List<String> middleFiles = middleFileMapper.getAllByType(Type, IDS);
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            List<v_PantentInfoMemo> yearfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Year", SIDS);
            List<v_PantentInfoMemo> applyfeememos = feeItemMemoMapper.getAllBySHENQINGHs("Apply", SIDS);
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, tzsEmailRecord, middleFiles, memos, yearfeememos,applyfeememos);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    public List<Map<String, Object>> getReportData(HttpServletRequest request) throws Exception {
        Map<String, Object> params = getParams1(request);
        List<Map<String, Object>> Datas = tzsMapper.getReportData(params);
        return Datas;
    }

    public Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "QX";
        String Type = request.getParameter("Type");
        String Status = request.getParameter("ZHUANGTAI");
        if (Status == null) Status = "";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin",pageIndex * pageSize);
        params.put("End",  pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        params.put("Type", "%" + Type + "%");
        if (Strings.isNotEmpty(Status)) params.put("ZHUANGTAI", Status);
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

    public Map<String, Object> getParams1(HttpServletRequest request) throws Exception {
        String Begin = request.getParameter("Begin");
        String End = request.getParameter("End");
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "QX";
        String Type = request.getParameter("Type");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", Begin);
        params.put("End", End);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        params.put("Type", "%" + Type + "%");

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
            List<String> middleFiles,
            List<v_PantentInfoMemo> memos, List<v_PantentInfoMemo> yearfeememos,
            List<v_PantentInfoMemo> applyfeememos) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("SHENQINGH").toString();
        String TONGZHISBH = row.get("TONGZHISBH").toString();
        String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));
        //row.put("SENDMAIL", emailRecord.containsKey(TONGZHISBH) ? 1 : 0);
        row.put("MIDDLEFILE", middleFiles.contains(TONGZHISBH) ? 1 : 0);

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
            List<String> names = ids.stream().map(f -> f.getName()).collect(toList());
            if (names.size() > 0) {
                row.put(type, Strings.join(names, ','));
                if (type == "KH") row.put("KHID", ids.get(0).getID());
            }
        });
        return row;
    }

    @Cacheable(value = "getTZSStatus", keyGenerator = "CompanyKeyGenerator")
    public List<ComboboxItem> getTZSStatus() {
        List<String> all = tzsMapper.getTZSStatus();
        List<ComboboxItem> result = new ArrayList<>();
        all.stream().forEach(f -> {
            ComboboxItem item = new ComboboxItem();
            item.setId(f);
            item.setText(f);
            result.add(item);
        });
        return result;
    }

    @Override
    public File download(String[] Codes) throws Exception {
        List<TZS> tzss = tzsRep.findAllByTongzhisbhIn(Codes);
        FTPUtil Ftp = new FTPUtil();
        String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        String zipDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + "\\");
        File fDir = new File(dirName);
        if (fDir.exists() == false) {
            fDir.mkdirs();
        }
        File zDir = new File(zipDir);
        if (zDir.exists() == false) {
            zDir.mkdirs();
        }
        Integer Total = 0;
        if (tzss.size() > 1) {
            if (Ftp.connect() == true) {
                for (int i = 0; i < tzss.size(); i++) {
                    TZS tzs = tzss.get(i);
                    String tzsPath = tzs.getTzspath();
                    if (StringUtils.isEmpty(tzsPath) == false) {
                        tzsPath = tzsPath.replace('\\', '/');
                        String famingmc = tzs.getFamingmc();
                        String tzsName = tzs.getTongzhismc();
                        String baseName = clearFileName(famingmc + "-" + tzsName);
                        String fileName = clearFileName(baseName + ".zip");
                        String createFile = Paths.get(dirName, fileName.replace("/", "")).toString();
                        Ftp.download("/" + tzsPath, createFile);
                        Total += getTargetFile(zipDir, dirName, i, createFile, baseName);
                    }
                }
            }
        } else {
            if (Ftp.connect() == true) {
                TZS tzs = tzss.get(0);
                String tzsPath = tzs.getTzspath();
                if (StringUtils.isEmpty(tzsPath) == false) {
                    tzsPath = tzsPath.replace('\\', '/');
                    String famingmc = tzs.getFamingmc();
                    String tzsName = tzs.getTongzhismc();
                    String baseName = clearFileName(famingmc + "-" + tzsName);
                    String fileName = clearFileName(baseName + ".zip");

                    String createFile = Paths.get(dirName, fileName).toString();
                    Ftp.download("/" + tzss.get(0).getTzspath(), createFile);
                    Total = getTargetFile(zipDir, dirName, 0, createFile, baseName);
                }
            }
        }

        String ZipFilePath =
                Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() + ".zip").toString();
        ZipFileUtils.zip(zipDir + "\\", ZipFilePath);
        FileUtils.deleteDirectory(fDir);
        return new File(ZipFilePath);
    }

    private int getTargetFile(String zipDir, String dirName, Integer i, String createFile, String baseName) throws
            Exception {
        String unZipDir = Paths.get(dirName, Integer.toString(i + 1)).toString();
        ZipFileUtils.unZip(createFile, unZipDir);
        File Find = new File(unZipDir);
        List<File> findFiles = FileUtils.listFiles(Find, new String[]{"tif"}, true).stream().collect(toList());
        for (int n = 0; n < findFiles.size(); n++) {
            File f = findFiles.get(n);
            String tPath = Paths.get(zipDir, baseName + "(" + Integer.toString(n + 1) + ").tif").toString();
            File target = new File(tPath);
            FileUtils.copyFile(f, target);
        }
        if (findFiles.size() == 0) {
            findFiles = FileUtils.listFiles(Find, new String[]{"pdf"}, true).stream().collect(toList());
            for (int n = 0; n < findFiles.size(); n++) {
                File f = findFiles.get(n);
                String tPath = Paths.get(zipDir, baseName + "(" + Integer.toString(n + 1) + ").pdf").toString();
                File target = new File(tPath);
                FileUtils.copyFile(f, target);
            }
        }
        FileUtils.deleteDirectory(new File(unZipDir));
        return findFiles.size();
    }

    @Override
    public File downloadOriginal(String[] Codes) throws Exception {
        List<TZS> tzss = tzsRep.findAllByTongzhisbhIn(Codes);
        FTPUtil Ftp = new FTPUtil();
        String tempZip = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
        if (tzss.size() > 1) {
            String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            File fDir = new File(dirName);
            if (fDir.exists() == false) {
                fDir.mkdirs();
            }
            if (Ftp.connect() == true) {
                for (int i = 0; i < tzss.size(); i++) {
                    TZS tzs = tzss.get(i);
                    String tzsPath = tzs.getTzspath();
                    String famingmc = tzs.getFamingmc();
                    String tzsName = tzs.getTongzhismc();
                    String fileName = clearFileName(famingmc + "-" + famingmc + ".zip");
                    String createFile = Paths.get(dirName, fileName).toString();
                    Ftp.download("//" + tzsPath, createFile);
                }
                ZipFileUtils.zip(dirName, tempZip);
            }
        } else {
            if (Ftp.connect() == true) {
                Ftp.download("//" + tzss.get(0).getTzspath(), tempZip);
            }
        }
        return new File(tempZip);
    }

    @Override
    public File downloadDBFile(String tzsbh) throws Exception {
        List<noticeMiddleFile> tzss = middleRep.findAllByTzsbh(tzsbh);
        List<String> AttIDS = tzss.stream().map(f -> f.getAttid()).collect(toList());
        List<tbAttachment> atts = attRep.findAllByGuidIn(AttIDS);
        FTPUtil Ftp = new FTPUtil();
        String tempZip = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
        if (tzss.size() > 1) {
            String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            File fDir = new File(dirName);
            if (fDir.exists() == false) {
                fDir.mkdirs();
            }
            if (Ftp.connect() == true) {
                for (int i = 0; i < atts.size(); i++) {
                    tbAttachment tzs = atts.get(i);
                    String fileName = clearFileName(tzs.getName());
                    String createFile = Paths.get(dirName, fileName).toString();
                    if (FileUtils.getFile(createFile).exists()) {
                        fileName = Integer.toString(i) + "_" + fileName;
                        createFile = Paths.get(dirName, fileName).toString();
                    }
                    Ftp.download("//" + tzs.getPath(), createFile);
                }
                ZipFileUtils.zip(dirName, tempZip);
            }
        } else {
            if (Ftp.connect() == true) {
                Ftp.download("//" + atts.get(0).getPath(), tempZip);
            }
        }
        return new File(tempZip);
    }

    @Override
    public byte[] downloadPdf(String[] Codes) throws Exception {
        List<TZS> tzss = tzsRep.findAllByTongzhisbhIn(Codes);
        FTPUtil Ftp = new FTPUtil();
        if (tzss.size() >0) {
            String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            File fDir = new File(dirName);
            if (fDir.exists() == false) {
                fDir.mkdirs();
            }
            if (Ftp.connect() == true) {
                List<File>  fs=new ArrayList<>();
                for (int i = 0; i < tzss.size(); i++) {
                    TZS tzs = tzss.get(i);
                    String tzsPath = tzs.getTzspath();
                    String createFile=Paths.get(dirName,UUID.randomUUID().toString()+".zip").toString();
                    Ftp.download("//" + tzsPath, createFile);
                    String newDir=Paths.get(dirName,UUID.randomUUID().toString()).toString();
                    ZipFileUtils.unZip(createFile,newDir);
                    File f=PdfUtils.toFile(newDir,tzs.getFamingmc()+"("+tzs.getTongzhismc()+")");
                    fs.add(f);
                }
                byte[] BB=ZipFileUtils.toBytes(fs.toArray(new File[fs.size()]),true);
                FileUtils.deleteDirectory(fDir);
                return BB;
            } else throw new Exception("无法下载文件，FTP连接失败!");
        } else {
           throw new Exception("可下载的文件列表为空!");
        }
    }


    private String clearFileName(String FileName) {
        FileName = FileName.replaceAll("<.*?>", "");
        FileName = FileName.replaceAll("/", "-");
        FileName = FileName.replaceAll("\\\\", "-");
        return FileName;
    }
}
