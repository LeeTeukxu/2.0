package com.zhide.dtsystem.controllers.cpc;

import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TZS;
import com.zhide.dtsystem.models.clientUpdateRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.clientUpdateRecordRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.define.ICPCAttachmentService;
import com.zhide.dtsystem.services.define.ICPCUpdateService;
import com.zhide.dtsystem.services.instance.ZlUpdaterFactory;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/CPCUpload")
public class CPCAttachmentController {

    final static Logger logger = LoggerFactory.getLogger(CPCAttachmentController.class);
    @Autowired
    clientUpdateRecordRepository clientUpdateRecordRepository;

    @Autowired
    TZSRepository tzsRepository;

    @Autowired
    pantentInfoRepository pantentInfoRepository;

    @Autowired
    ZlUpdaterFactory zlFactory;

    @Autowired
    ICPCAttachmentService cpcAttachmentService;
    @Autowired
    ICPCUpdateService cpcService;

    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @RequestMapping("/UpdateFourFile")
    @ResponseBody
    public successResult UpdateFourFile(MultipartFile file) {
        successResult result = new successResult(false);
        FileInputStream inputStream = null;
        String simpleName = "";
        uploadFileResult res = null;
        try {
            simpleName = file.getOriginalFilename();
            String targetFilePath = CompanyPathUtils.getFullPath("Updater", "Temp", simpleName);
            File targetFile = new File(targetFilePath);
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
            if (targetFile.exists()) {
                inputStream = new FileInputStream(targetFile);
                UploadUtils uploadUtils = getUtils();
                res = uploadUtils.uploadUpdateFile(simpleName, inputStream);
                if (res.isSuccess()) {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    clientUpdateRecord record = new clientUpdateRecord();
                    record.setCreateTime(new Date());

                    zlFactory.execute(targetFilePath);
                    try {
                        FileUtils.forceDelete(targetFile);
                    } catch (Exception ax) {
                        ax.printStackTrace();
                    }
                    record.setDealTime(new Date());
                    record.setDeal(true);
                    record.setTimes(zlFactory.getExecuteNum());
                    record.setPath(res.getFullPath());
                    clientUpdateRecordRepository.save(record);
                }
            }
        } catch (Exception ax) {
            clientUpdateRecord record = new clientUpdateRecord();
            record.setCreateTime(new Date());
            record.setDeal(false);
            record.setError(ax.getMessage());
            record.setPath(res.getFullPath());
            clientUpdateRecordRepository.save(record);
            result.raiseException(ax);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/UpdateTZS")
    public successResult UpdateTZS(@RequestParam List<String> IDS, String webToken, MultipartFile file) {
        successResult result = new successResult(false);
        String unZipDir = "";
        String fullName = "";
        try {
            String fileName = file.getOriginalFilename();
            fullName = CompanyPathUtils.getFullPath("Notice", "Temp", fileName);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(fullName), file.getBytes());
            unZipDir = CompanyPathUtils.getFullPath(UUID.randomUUID().toString());
            ZipFileUtils.unZip(fullName, unZipDir);
            UploadUtils uploadUtils = getUtils();
            for (int i = 0; i < IDS.size(); i++) {
                String ID = IDS.get(i);
                String SourceFilePath = unZipDir + "\\" + ID + ".zip";
                File FD = new File(SourceFilePath);
                if (FD.exists() == true) {
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(SourceFilePath);
                        uploadFileResult res = uploadUtils.uploadNoticeFile(FD.getName(), fileInputStream);
                        FileUtils.forceDeleteOnExit(FD);
                        if (res.isSuccess()) {
                            Optional<TZS> findTzs = tzsRepository.findById(ID);
                            if (findTzs.isPresent()) {
                                TZS tzs = findTzs.get();
                                tzs.setTzspath(res.getFullPath());
                                tzs.setOriginal(res.getFullPath());
                                tzs.setTUploadTime(new Date());
                                tzs.setOUploadTime(new Date());
                                tzsRepository.save(tzs);
                            } else throw new Exception(ID + "在TZS中不存在!");

                            clientUpdateRecord record = new clientUpdateRecord();
                            record.setCreateTime(new Date());
                            record.setDealTime(new Date());
                            record.setDeal(true);
                            record.setTimes(1);
                            record.setPath(res.getFullPath());
                            clientUpdateRecordRepository.save(record);
                        }
                    } catch (Exception ax) {
                        ax.printStackTrace();
                        logger.info("上传通知书附件时发生错误:" + ax.getMessage());
                        throw ax;
                    } finally {
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception ax) {
                                ax.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        } finally {
            try {
                if (StringUtil.isNullOrEmpty(unZipDir) == false) {
                    FileUtils.deleteDirectory(new File(unZipDir));
                }
                if (StringUtil.isNullOrEmpty(fullName) == false) {
                    FileUtils.forceDelete(new File(fullName));
                }
            } catch (Exception ax) {
                logger.info("删除通知书附件临时文件出错:" + ax.getMessage());
            }
        }
        return result;
    }

    @RequestMapping("/UpdateCPC")
    @ResponseBody
    public successResult UpdateCPC(String SHENQINGBH, MultipartFile file) {
        successResult result = new successResult(false);
        FileInputStream fxx = null;
        String unZipDir = "";
        File fullFile = null;
        try {
            UploadUtils uploadUtils = getUtils();
            String simpleName = file.getOriginalFilename();
            String fullName = CompanyPathUtils.getFullPath("CPC", simpleName);
            fullFile = new File(fullName);
            unZipDir = CompanyPathUtils.getFullPath("Temp", "CPC", SHENQINGBH);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(fullFile, file.getBytes());
            if (fullFile.exists()) {
                logger.info("上传文件:" + fullFile + "已保存到磁盘.");
                fxx = new FileInputStream(fullFile);
                uploadFileResult res = uploadUtils.uploadCPCFile(SHENQINGBH + ".zip", fxx);
                if (res.isSuccess()) {
                    //解析包的信息，并更新。
                    ZipFileUtils.unZip(fullName, unZipDir);
                    cpcService.SaveAll(SHENQINGBH,fullName,unZipDir,res);
                } else {
                    logger.info(simpleName + "上传至Ftp服务器失败。错误信息为:" + res.getMessage());
                    result.setMessage(res.getMessage());
                    result.setSuccess(false);
                }

            }
        } catch (Exception ax) {
            result.raiseException(ax);
        } finally {
            if (fxx != null) {
                try {
                    fxx.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fullFile != null) FileUtils.deleteQuietly(fullFile);
                }
            }
            if (Strings.isNotEmpty(unZipDir)) {
                try {
                    FileUtils.deleteDirectory(new File(unZipDir));
                } catch (Exception ax) {

                }
            }
        }
        return result;
    }
}
