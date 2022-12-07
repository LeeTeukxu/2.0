package com.zhide.dtsystem.services.implement;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.DirectoryUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TZS;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.models.tbFileUpload;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbFileUploadRepository;
import com.zhide.dtsystem.services.define.IFileUploadService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

/**
 * @ClassName: FileUploadServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月17日 11:00
 **/
@Service
public class FileUploadServiceImpl implements IFileUploadService {
    @Autowired
    tbFileUploadRepository uploadRep;
    @Autowired
    tbAttachmentRepository attRep;
    Logger logger= LoggerFactory.getLogger(FileUploadServiceImpl.class);
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void DeleteAll(List<Integer> IDS) throws Exception {
        IDS.forEach(f->uploadRep.deleteById(f));
    }

    @Override
    @Transactional
    public void SaveAll(List<tbFileUpload> files) throws Exception {
        LoginUserInfo Info= CompanyContext.get();
        for(int i=0;i<files.size();i++){
            tbFileUpload file=files.get(i);
            Optional<tbFileUpload> findFiles=uploadRep.findById(file.getId());
            if(findFiles.isPresent()){
                tbFileUpload upFile= findFiles.get();
                upFile.setMemo1(file.getMemo1());
                upFile.setMemo2(file.getMemo2());
                upFile.setRequired(file.getRequired());
                upFile.setName(file.getName());
                upFile.setTypeId(file.getTypeId());
                upFile.setName(file.getName());
                upFile.setAttId(file.getAttId());
                uploadRep.save(upFile);
            } else {
                file.setUploadTime(new Date());
                file.setUploadMan(Info.getUserIdValue());
                uploadRep.save(file);
            }
        }
    }

    @Override
    public File download(String[] IDS, Boolean zipFile) throws Exception {
        List<tbAttachment> tzss = attRep.findAllByGuidIn(Arrays.asList(IDS));
        FTPUtil Ftp = new FTPUtil();
        String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        File fDir = new File(dirName);
        if (fDir.exists() == false) {
            fDir.mkdirs();
        }
        Integer Total = 0;
        if (tzss.size() >= 1) {
            if (Ftp.connect() == true) {
                for (int i = 0; i < tzss.size(); i++) {
                    tbAttachment tzs = tzss.get(i);
                    String tzsPath = tzs.getPath();
                    if (StringUtils.isEmpty(tzsPath) == false) {
                        tzsPath = tzsPath.replace('\\', '/');
                        String createFile = Paths.get(dirName, tzs.getName().replace("/", "")).toString();
                        logger.info(createFile);
                        Ftp.download("/" + tzsPath, createFile);
                        if(new File(createFile).exists()==false){
                            logger.info(createFile+"没有下载成功!");
                        }
                    } else  logger.info(tzs.getGuid()+"附件路径为空!");
                }
            } else logger.info("连接FTP服务未成功!");
        } else logger.info("没有可以下载文件，tzss的size为0");
        File[] Fs=new File(dirName).listFiles();
        if(Fs.length>0 ) {
            if(zipFile==true){
                String ZipFilePath = Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() + ".zip").toString();
                ZipFileUtils.zip(dirName + "\\", ZipFilePath);
                return new File(ZipFilePath);
            } else {
                return Fs[0];
            }
        } else throw new Exception("从FTP服务器下载不正确。无法进行压缩并下载!");
    }
}
