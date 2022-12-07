package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.DirectoryUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesSubFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CasesSubFileServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年09月26日 21:09
 **/
@Service
public class CasesSubFileServiceImpl implements ICasesSubFileService {

    @Autowired
    casesSubFilesRepository subFileRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    casesSubRepository casesSubRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    tradeCasesRepository casesRep;

    Logger logger= LoggerFactory.getLogger(ICasesSubFileService.class);

    @Override
    public File DownloadFiles(String Type,String SubIDS) throws Exception {
        String[] Subs=StringUtils.split(SubIDS, ",");
        List<String> Subss = Arrays.stream(StringUtils.split(SubIDS, ",")).collect(toList());
        List<casesSub> casesSubs = casesSubRep.getAllBySubIdIn(Subs);
        List<casesSubFiles> files = subFileRep.getAllBySubIdIn(Subs);
        logger.info("files Length:{}",files.size());
        List<String> FileIDS =new ArrayList<>();
        if(Type.equals("All")){
           FileIDS= files.stream().map(f -> f.getAttId()).collect(toList());
        } else FileIDS=files.stream().filter(f->f.getType().equals(Type)).map(f->f.getAttId()).collect(toList());
        if(FileIDS.size()==0) throw new Exception("没有这种类型的文件可供下载!");
        List<tbAttachment> allFiles = attRep.findAllByGuidInAndType(FileIDS, 1);

        if(allFiles.size()!=Subs.length){
            List<String>XID=files.stream().map(f->f.getAttId()).filter(f->Subss.contains(f)==false).collect(toList());
            List<tbAttachment> alFiles=attRep.findAllByGuidInAndType(XID,2);
            allFiles.addAll(alFiles);
        }
        //logger.info("allFiles's Length:{},Subs's Length:{}",allFiles.size(),Subs.length);
        FTPUtil Ftp = new FTPUtil();
        int FileCount = 0;
        SimpleDateFormat fft = new SimpleDateFormat("yyyyMMddHHmmss");
        if (Ftp.connect() == true) {
            String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString(), fft.format(new Date()));
            DirectoryUtils.createNotExists(dirName);
            for (int a = 0; a < casesSubs.size(); a++) {
                casesSub sub = casesSubs.get(a);
                String SubID = sub.getSubId();
                String CasesID = sub.getCasesId();
                casesMain main = mainRep.findFirstByCasesId(CasesID).get();
                String ClientName = clientRep.findAllByClientID(main.getClientId()).getName();
                List<casesSubFiles> realFiles =
                        files.stream().filter(f -> f.getSubId().equals(SubID)).collect(toList());
                if (realFiles.size() == 0) continue;
                else {
                    String yName = sub.getyName().trim();
                    String SubDir =
                            Paths.get(dirName, sub.getSubNo() + "_" + yName + "_" + StringUtils.trim(ClientName)).toString();
                    DirectoryUtils.createNotExists(SubDir);
                    for (int b = 0; b < realFiles.size(); b++) {
                        casesSubFiles tFile = realFiles.get(b);
                        String AttID = tFile.getAttId();
                        String type = tFile.getType();
                        String typeName = getTypeName(type);
                        Optional<tbAttachment> findOnes =
                                allFiles.stream().filter(f -> f.getGuid().equals(AttID)).findFirst();
                        if (findOnes.isPresent()) {
                            tbAttachment attInfo = findOnes.get();
                            String FileName = attInfo.getName();
                            String filePath = attInfo.getPath();
                            String SaveFilePath = Paths.get(SubDir, typeName + "_" + FileName).toString();
                            Ftp.download(filePath, SaveFilePath);
                            File fff=new File(SaveFilePath);
                            if(fff.exists())FileCount++; else {
                                logger.info(filePath+"下载失败！");
                            }
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
            } else throw new Exception("没有可以下载的文件!");
        } else throw new Exception("登录FTP服务器失败，请联系系统管理员解决!");
    }

    public File DownloadTradeFiles(String SubIDS) throws Exception{
        String[] Subs = StringUtils.split(SubIDS,",");
        List<casesYwItems> casesYws = casesYwRep.getAllBySubIdIn(Subs);
        List<casesSubFiles> files = subFileRep.getAllBySubIdIn(Subs);
        List<String> FileIDS = files.stream().map(f -> f.getAttId()).collect(toList());
        List<tbAttachment> allFiles = attRep.findAllByGuidInAndType(FileIDS,1);
        FTPUtil Ftp = new FTPUtil();
        int FileCount = 0;
        SimpleDateFormat fft = new SimpleDateFormat("yyyyMMddHHmmss");
        if (Ftp.connect() == true){
            String dirName = CompanyPathUtils.getFullPath("Temp",UUID.randomUUID().toString(),fft.format(new Date()));
            DirectoryUtils.createNotExists(dirName);

            for (int a=0;a<casesYws.size();a++){
                casesYwItems yw = casesYws.get(a);
                String SubID = yw.getSubId();
                String CasesID = yw.getCasesId();
                tradeCases cases = casesRep.findFirstByCasesid(CasesID).get();
                String ClientName = clientRep.findAllByClientID(cases.getClientId()).getName();
                List<casesSubFiles> realFiles = files.stream().filter(f -> f.getSubId().equals(SubID)).collect(toList());
                if (realFiles.size() == 0) continue;
                else {
                    String yName = yw.getYName().trim();
                    String SubDir = Paths.get(dirName,yw.getSubNo() + "_" + yName + "_" + ClientName).toString();
                    DirectoryUtils.createNotExists(SubDir);
                    for (int b=0;b<realFiles.size();b++){
                        casesSubFiles tFile = realFiles.get(b);
                        String AttID = tFile.getAttId();
                        String type = tFile.getType();
                        String typeName = "申报资料";
                        Optional<tbAttachment> findOnes = allFiles.stream().filter(f -> f.getGuid().equals(AttID)).findFirst();
                        if (findOnes.isPresent()){
                            tbAttachment attInfo = findOnes.get();
                            String FileName = attInfo.getName();
                            String filePath = attInfo.getPath();
                            String SaveFilePath = Paths.get(SubDir,typeName + "_" + FileName).toString();
                            Ftp.download(filePath,SaveFilePath);
                            FileCount++;
                        }
                    }
                }
            }
            if (FileCount > 0){
                String ZipFilePath = Paths.get(CompanyPathUtils.getFullPath("Temp"),UUID.randomUUID().toString() + ".zip").toString();
                ZipFileUtils.zip(dirName,ZipFilePath,true);
                FileUtils.deleteDirectory(new File(dirName).getParentFile());
                return new File(ZipFilePath);
            }else throw new Exception("没有可以下载的申报资料");
        }else throw new Exception("登录FTP服务器失败,请联系管理员解决");
    }

    private String getTypeName(String Type) {
        String Res = "";
        switch (Type) {
            case "Tech": {
                Res = "技术交底资料";
                break;
            }
            case "Zl": {
                Res = "著录信息资料";
                break;
            }
            case "Accept": {
                Res = "专利申报文件";
                break;
            }
            case "Exp": {
                Res = "情况说明文件";
                break;
            }
            case "Aud": {
                Res = "内审驳回说明文件";
                break;
            }
        }
        return Res;
    }
}
