package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.CostReductionMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.CostReductionRepository;
import com.zhide.dtsystem.repositorys.costReductionAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.define.ICostReductionService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CostReductionServiceImpl implements ICostReductionService {
    @Autowired
    CostReductionMapper costReductionMapper;

    LoginUserInfo loginUserInfo;

    @Autowired
    CostReductionRepository costReductionRepository;

    @Autowired
    costReductionAttachmentRepository costReductionAttachmentRepository;

    @Autowired
    tbAttachmentRepository tbAttachmentRepository;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = costReductionMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
//            List<String> UID = datas.stream().map(f -> f.get("UUID").toString()).collect(toList());
//            Map<String,String> Name = getSubFileName(UID);
//            for (int i=0; i<datas.size(); i++){
//                Map<String,Object> data = datas.get(i);
//                data.remove("_TotalNum");
//                String UUID = data.get("UUID").toString();
//                if (Name.containsKey(UUID)){
//                    String FileName = Name.get(UUID);
//                    if (StringUtils.isEmpty(FileName) == false){
//                        data.put("FileName",FileName);
//                    }
//                }
//            }
            object.setData(datas);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "AddTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
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
    public @Transactional(rollbackFor = Exception.class)
    CostReduction SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        CostReduction costReduction = saveMain(Data);
        String UUID = costReduction.getuUId();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(UUID, atts);
        }
        return costReduction;
    }

    private CostReduction saveMain(Map<String, Object> Data) throws Exception {
        CostReduction costReduction = new CostReduction();
        String UID = "";
        Integer CostReductionID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false) throw new Exception("参数异常！");
        if (Data.containsKey("costReductionId")) {
            UID = Data.get("uUId").toString();
            if (StringUtils.isEmpty(Data.get("costReductionId").toString()) == false) {
                CostReductionID = Integer.parseInt(Data.get("costReductionId").toString());
            }
        }
        Optional<CostReduction> finds = costReductionRepository.findById(CostReductionID);
        if (finds.isPresent()) {
            costReduction = finds.get();
        } else {
            costReduction.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            costReduction.setAddTime(new Date());
            costReduction.setInternalResult(0);
            costReduction.setGzjZt(0);
            costReduction.setTheSuccess(0);
            costReduction.setUUId(UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty(UID) == false) costReduction.setUUId(UID);
        for (String Key : Data.keySet()) {
            Field target = fieldObject.findByName(costReduction, Key);
            if (target != null) {
                Object value = Data.get(Key);
                fieldObject.setValue(costReduction, target, value);
            }
        }
        costReductionRepository.save(costReduction);
        return costReduction;
    }

    private void SaveAttachment(String UUID, List<String> IDS) {
        costReductionAttachmentRepository.deleteAllByUUId(UUID);
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            costReductionAttachment costReductionAttachment = new costReductionAttachment();
            costReductionAttachment.setuUId(UUID);
            costReductionAttachment.setAttId(ID);
            costReductionAttachmentRepository.save(costReductionAttachment);
        }
    }

    private Map<String, String> getSubFileName(List<String> UUIDS) {
        Map<String, String> Res = new HashMap<>();
        List<costReductionAttachment> SubFiles = costReductionAttachmentRepository.getAllByUUIdIn(UUIDS);
        List<String> AttIDS = SubFiles.stream().map(f -> f.getAttId()).collect(toList());
        List<tbAttachment> AttFiles = tbAttachmentRepository.findAllByGuidIn(AttIDS);
        for (int i = 0; i < UUIDS.size(); i++) {
            String UUID = UUIDS.get(i);
            List<costReductionAttachment> sFiles =
                    SubFiles.stream().filter(f -> f.getuUId().equals(UUID)).collect(toList());
            List<String> TinyIDS = sFiles.stream().map(f -> f.getAttId()).collect(toList());
            List<tbAttachment> findAlls = AttFiles.stream()
                    .filter(f -> TinyIDS.contains(f.getGuid()))
                    .sorted(Comparator.comparing(tbAttachment::getUploadTime).reversed())
                    .collect(toList());
            if (findAlls.size() > 0) {
                if (Res.containsKey(UUID) == false) {
                    Res.put(UUID, findAlls.get(0).getName());
                }
            }
        }
        return Res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            List<costReductionAttachment> listCostReduction = costReductionAttachmentRepository.findAllByUUId(id);
            if (listCostReduction.size() > 0) {
                for (int j = 0; j < listCostReduction.size(); j++) {
                    tbAttachmentRepository.deleteAllByGuid(listCostReduction.get(j).getAttId());
                }
            }
            costReductionAttachmentRepository.deleteAllByUUId(id);
            costReductionMapper.DeleteByUUID(id);
        }
        return false;
    }

    public List<String> getAttachmentPaths(String Path, String GUID) throws Exception {
        Date nowTime = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        List<String> Res = new ArrayList<>();
        FTPUtil ftp = new FTPUtil();
        if (ftp.connect() == true) {
            List<costReductionAttachment> listAttID = costReductionAttachmentRepository.findAllByUUId(GUID);
            if (listAttID.size() > 0) {
                for (int i = 0; i < listAttID.size(); i++) {
                    Optional<tbAttachment> cra = tbAttachmentRepository.findAllByGuid(listAttID.get(i).getAttId());
                    tbAttachment att = cra.get();
                    String filePath = att.getPath();
                    String zipFile = Paths.get(CompanyPathUtils.getImages(), att.getGuid() + ".zip").toString();
                    String upZipDir = Paths.get(CompanyPathUtils.getImages(), UUID.randomUUID().toString()).toString();
                    String targetDir = Paths.get(CompanyPathUtils.getImages(), att.getGuid()).toString();
                    ftp.download("//" + filePath, zipFile);
                    ZipFileUtils.unZip(zipFile, upZipDir);
                    File Find = new File(upZipDir);
                    List<File> findFiles =
                            FileUtils.listFiles(Find, new String[]{"jpg"}, true).stream().collect(toList());
                    if (findFiles.size() > 1) {
                        for (int n = 0; n < findFiles.size(); n++) {
                            File f = findFiles.get(n);
                            String VV = att.getGuid() + "(" + Integer.toString(n + 1) + ").jpg";
                            String tPath = Paths.get(targetDir, VV).toString();
                            File target = new File(tPath);
                            ImageUtils.tiftoJpg(f.getPath(), tPath);
                            Res.add("/images" + att.getGuid() + "/" + VV);
                        }
                    } else {
                        File f = findFiles.get(0);
                        String VV = att.getGuid() + ".jpg";
                        String tPath = Paths.get(targetDir, VV).toString();
                        File target = new File(tPath);
                        ImageUtils.tiftoJpg(f.getPath(), tPath);
                        Res.add("/images/" + att.getGuid() + "/" + VV);
                    }
                    FileUtils.forceDeleteOnExit(new File(upZipDir));
                    FileUtils.forceDelete(new File(zipFile));
                }
            }
        } else throw new Exception("连接FTP服务器失败，操作终止。");
        return Res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CostReduction SaveNeiShen(@Param(value = "Data") CostReduction costReduction) throws Exception {
        if (costReduction == null) throw new Exception("数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录!");
        int result = 0;
        if (costReduction.getCostReductionId() != null) {
            result = updateNeiShen(costReduction, Integer.parseInt(loginUserInfo.getUserId()), new Date());
        }
        return costReduction;
    }

    public int updateNeiShen(CostReduction costReduction, int InternalPeople, Date InternalDate) {
        int result = costReductionRepository.updateNeiShen(InternalPeople, costReduction.getInternalResult(),
                InternalDate, costReduction.getCostReductionId());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean GuoZhiJu(List<Integer> CostReductionIDS) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        for (int i = 0; i < CostReductionIDS.size(); i++) {
            Integer CostReductionID = CostReductionIDS.get(i);
            updateGuoZhiJu(Integer.parseInt(loginUserInfo.getUserId()), new Date(), CostReductionID);
        }
        return true;
    }

    public boolean UnGuoZhiJu(List<Integer> CostReductionIDS) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        for (int i = 0; i < CostReductionIDS.size(); i++) {
            Integer CostReductionID = CostReductionIDS.get(i);
            updateUnGuoZhiJu(CostReductionID);
        }
        return true;
    }

    public int updateGuoZhiJu(int Transactor, Date DealTime, int CostReductionID) {
        int result = costReductionRepository.updateGuoZhiJu(Transactor, DealTime, CostReductionID);
        return result;
    }

    public int updateUnGuoZhiJu(int CostReductionID) {
        int result = costReductionRepository.updateUnGuoZhiJu(CostReductionID);
        return result;
    }

    @Override
    public CostReduction SaveFeiJianJieGuo(CostReduction costReduction) throws Exception {
        if (costReduction == null) throw new Exception("数据为空!");
        int result = 0;
        if (costReduction.getCostReductionId() != null) {
            result = updateFeiJianJieguo(costReduction);
        }
        return costReduction;
    }

    public int updateFeiJianJieguo(CostReduction costReduction) {
        int result = costReductionRepository.updateFeiJianJieGuo(costReduction.getTheSuccess(),
                costReduction.getReductionTheYear(), costReduction.getCostReductionId());
        return result;
    }

    @Override
    public File DownloadFiles(String UUIDS) throws Exception {
        String[] UUS = StringUtils.split(UUIDS, ",");
        List<CostReduction> crs = costReductionRepository.getAllByUUIdIn(UUS);
        List<costReductionAttachment> attachments = costReductionAttachmentRepository.getAllByUUIdIn(UUS);
        List<String> FileIDS = attachments.stream().map(f -> f.getAttId()).collect(Collectors.toList());
        List<tbAttachment> allFiles = tbAttachmentRepository.findAllByGuidIn(FileIDS);
        FTPUtil Ftp = new FTPUtil();
        int FileCount = 0;
        SimpleDateFormat fft = new SimpleDateFormat("yyyyMMddHHmmss");
        if (Ftp.connect() == true) {
            String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString(), fft.format(new Date()));
            DirectoryUtils.createNotExists(dirName);

            for (int i = 0; i < crs.size(); i++) {
                CostReduction costReduction = crs.get(i);
                String UUID = costReduction.getuUId();
                List<costReductionAttachment> realFiles =
                        attachments.stream().filter(f -> f.getuUId().equals(UUID)).collect(Collectors.toList());
                if (realFiles.size() == 0) continue;
                else {
                    String CostReductionDir = Paths.get(costReduction.getuUId()).toString();
                    DirectoryUtils.createNotExists(CostReductionDir);
                    for (int j = 0; j < realFiles.size(); j++) {
                        costReductionAttachment cFiles = realFiles.get(j);
                        String AttID = cFiles.getAttId();
                        Optional<tbAttachment> findOnes =
                                allFiles.stream().filter(f -> f.getGuid().equals(AttID)).findFirst();
                        if (findOnes.isPresent()) {
                            tbAttachment attInfo = findOnes.get();
                            String FileName = attInfo.getName();
                            String FilePath = attInfo.getPath();
                            String SaveFilePath = Paths.get(dirName, FileName).toString();
                            Ftp.download("//" + FilePath, SaveFilePath);
                            FileCount++;
                        }
                    }
                }
            }
            if (FileCount > 0) {
                String ZipFilePath = Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() +
                        ".zip").toString();
                ZipFileUtils.zip(dirName, ZipFilePath, true);
                FileUtils.deleteDirectory(new File(dirName).getParentFile());
                return new File(ZipFilePath);
            } else throw new Exception("没有可用下载的文件！");
        } else throw new Exception("登录FTP服务器失败，请联系管理员解决!");
    }
}
