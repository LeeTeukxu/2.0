package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.AddYearWatchItemMapper;
import com.zhide.dtsystem.mapper.YearWatchMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IYearWatchService;
import com.zhide.dtsystem.services.instance.yearMoneyCreator;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class YearWatchServiceImpl implements IYearWatchService {

    @Autowired
    AddYearWatchItemMapper addMapper;
    @Autowired
    YearWatchMapper watchMapper;
    @Autowired
    TZSRepository tzsRep;
    @Autowired
    yearWatchConfigRepository yearWatchRep;
    @Autowired
    pantentInfoRepository pantentInfoRep;
    @Autowired
    yearFeeListRepository yearFeeListRep;
    @Autowired
    yearMoneyCreator yearMoneyCreator;

    @Autowired
    allFeePayForResultRepository allFeePayRep;

    @Override
    public pageObject getAddYearWatchItem(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> Datas = addMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (Datas.size() > 0) {
            Total = Integer.parseInt(Datas.get(0).get("_TotalNum").toString());
            String[] SIDS = Datas.stream()
                    .map(f -> f.get("SHENQINGH").toString())
                    .collect(Collectors.toList()).toArray(new String[Datas.size()]);
            List<TZS> allTZS = tzsRep.findAllByShenqinghIn(SIDS);
            Datas.stream().forEach(f -> {
                String SHENQINGH = f.get("SHENQINGH").toString();
                Optional<TZS> findJ = allTZS.stream().filter(x -> x.getShenqingh().equals(SHENQINGH)
                        && x.getTongzhismc().equals("办理登记手续通知书")).findFirst();
                if (findJ.isPresent()) {
                    TZS tzs = findJ.get();
                    f.put("JL", tzs.getTongzhisbh());
                    f.put("FAWENRQ", tzs.getFawenrq());
                }
                f.remove("_TotalNum");
                f.remove("RowNum");
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
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
        String queryTexts = request.getParameter("Querys");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        if (Strings.isNotEmpty(queryTexts)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("YearItems", OrItems);
        } else params.put("YearItems", new ArrayList<>());
        return params;
    }

    public List<String> getTZSPaths(String tzsbh) throws Exception {
        List<String> Res = new ArrayList<>();
        Optional<TZS> tzss = tzsRep.findById(tzsbh);
        if (tzss.isPresent()) {
            TZS tzs = tzss.get();
            FTPUtil Ftp = new FTPUtil();
            if (Ftp.connect() == true) {
                String tzsPath = tzs.getTzspath();
                String zipFile = Paths.get(CompanyPathUtils.getImages(), tzsbh + ".zip")
                        .toString();
                String upZipDir = Paths.get(CompanyPathUtils.getImages(), UUID.randomUUID().toString()).toString();
                String targetDir = Paths.get(CompanyPathUtils.getImages(), tzsbh).toString();
                Ftp.download("//" + tzsPath, zipFile);
                ZipFileUtils.unZip(zipFile, upZipDir);
                File Find = new File(upZipDir);
                String ext = "tif";
                List<File> findFiles = FileUtils.listFiles(Find, new String[]{"tif"}, true).stream().collect(toList());
                if (findFiles.size() == 0)
                    findFiles = FileUtils.listFiles(Find, new String[]{"pdf"}, true).stream().collect
                            (toList());
                if (findFiles.size() > 1) {
                    for (int n = 0; n < findFiles.size(); n++) {
                        File f = findFiles.get(n);
                        String VV = tzsbh + "(" + Integer.toString(n + 1) + ").jpg";
                        String tPath = Paths.get(targetDir, VV).toString();
                        File target = new File(tPath);
                        if (ext == "tif") {
                            ImageUtils.tiftoJpg(f.getPath(), tPath);
                            Res.add("/images/" + tzsbh + "/" + VV);
                        } else {
                            List<String> imagePaths = ImageUtils.pdftoJpg(f.getPath(), targetDir);
                            for (int i = 0; i < imagePaths.size(); i++) {
                                File fx = new File(imagePaths.get(i));
                                String fxName = fx.getName();
                                Res.add("/images/" + tzsbh + "/" + fxName);
                            }
                        }
                    }
                } else {
                    File f = findFiles.get(0);
                    if (FilenameUtils.getExtension(f.getPath()).toLowerCase().equals("pdf")) {
                        List<String> imagePaths = ImageUtils.pdftoJpg(f.getPath(), targetDir);
                        for (int i = 0; i < imagePaths.size(); i++) {
                            File fx = new File(imagePaths.get(i));
                            String fxName = fx.getName();
                            Res.add("/images/" + tzsbh + "/" + fxName);
                        }
                    } else {
                        String VV = tzsbh + ".jpg";
                        String tPath = Paths.get(targetDir, VV).toString();
                        File target = new File(tPath);
                        ImageUtils.tiftoJpg(f.getPath(), tPath);
                        Res.add("/images/" + tzsbh + "/" + VV);
                    }
                }
                FileUtils.forceDeleteOnExit(new File(upZipDir));
                FileUtils.forceDelete(new File(zipFile));
            } else throw new Exception("连接FTP服务失败，操作被中止。");
        }
        return Res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(List<Map<String, Object>> Datas) throws Exception {
        LoginUserInfo loginInfo = CompanyContext.get();
        for (int i = 0; i < Datas.size(); i++) {
            Map<String, Object> Data = Datas.get(i);
            String shenqingh = Data.get("SHENQINGH").toString();
            int feeType = Integer.parseInt(Data.get("FeeType").toString());
            int beginTimes = Integer.parseInt(Data.get("BeginTimes").toString());
            Optional<TZS> findTzs = tzsRep.findAllByShenqinghIn(new String[]{shenqingh}).stream().filter(f -> f
                    .getTongzhismc()
                    .equals("办理登记手续通知书")).findFirst();
            if (findTzs.isPresent()) {
                TZS findOne = findTzs.get();
                Date fawenr = findOne.getFawenrq();
                Optional<yearWatchConfig> nowOnes = yearWatchRep.findAllByShenQingh(shenqingh);
                yearWatchConfig config = new yearWatchConfig();
                if (nowOnes.isPresent()) {
                    config = nowOnes.get();
                    config.setUpdateMan(Integer.parseInt(loginInfo.getUserId()));
                    config.setUpdateTime(new Date());
                } else {
                    config.setCreateMan(Integer.parseInt(loginInfo.getUserId()));
                    config.setCreateTime(new Date());
                }
                config.setFaWenDate(fawenr);
                config.setFeeType(feeType);
                config.setShenQingh(shenqingh);
                config.setBeginTimes(beginTimes);
                if (feeType == 1) config.setFeePercent(0.15);
                if (feeType == 2) config.setFeePercent(0.30);
                if (feeType == 3) config.setFeePercent(1.00);
                yearWatchRep.save(config);
                yearMoneyCreator.setShenqingh(shenqingh);
                yearMoneyCreator.saveAllYearFeeItems();
            }
        }

        return true;
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams0(request);
        List<Map<String, Object>> Datas = watchMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (Datas.size() > 0) {
            Total = Integer.parseInt(Datas.get(0).get("_TotalNum").toString());
            Datas.stream().forEach(f -> {
                f.remove("_TotalNum");
                f.remove("RowNum");
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAll(List<String> IDS) throws Exception {
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<allFeePayForResult> finds = allFeePayRep.findFirstByTypeAndShenQinghLike("Year", "%" + ID + "%");
            if (finds.isPresent() == false) {
                //yearFeeListRep.deleteByShenqingh(ID);
                //yearWatchRep.deleteAllByShenQingh(ID);
            } else throw new Exception("在待缴年费清单中还存在引用，请先设置为【不缴费】以后再进行操作。");
        }
        return true;
    }

    private Map<String, Object> getParams0(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "faWenDate";
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
}
