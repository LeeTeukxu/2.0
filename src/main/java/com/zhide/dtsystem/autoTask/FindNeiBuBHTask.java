package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.xml.QINGQIUXX;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.instance.XmlParsor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: FindNeiBuBHTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月28日 16:57
 **/
@Component
public class FindNeiBuBHTask {
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    pantentInfoRepository patentRep;

    Logger logger = LoggerFactory.getLogger(FindNeiBuBHTask.class);

    @Scheduled(cron = "0 0 0/6 * * ?")
    public void Process() {
        FTPUtil util = new FTPUtil();
        List<String> companyIDS = userMapper.getCompanyList();
        if (util.connect()) {
            for (int i = 0; i < companyIDS.size(); i++) {
                String companyId = companyIDS.get(i);
                changeContext(companyId);
                List<pantentInfo> emptys = patentRep.findAllEmptyNeiBubh();
                if (emptys.size() > 0) {
                    logger.info("开始处理" + companyId + "内部编号为空的专利,一共:" + Integer.toString(emptys.size()));
                } else logger.info(companyId + "没有需要处理的内部编号为空的记录!");
                for (int n = 0; n < emptys.size(); n++) {
                    pantentInfo pt = emptys.get(n);
                    String nbCode = pt.getNeibubh();
                    //logger.info("Neibubh:"+nbCode);
                    if (StringUtils.isEmpty(nbCode) == false) continue;
                    String uploadPath = pt.getUploadPath();
                    //logger.info("uploadPath:"+uploadPath);
                    if (StringUtils.isEmpty(uploadPath)) continue;
                    String saveDir = "";
                    try {
                        logger.info("开始下载专利:{}的CPC包文件:{}", pt.getShenqingh(), uploadPath);
                        String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
                        util.download(uploadPath, savePath);
                        if (new File(savePath).exists() == false) {
                            logger.info("专利:{}的CPC包文件:{}下载下载到目录{}失败!", pt.getShenqingh(), uploadPath, savePath);
                            ///pt.setParseError("CPC包文件文件下载失败");
                            //pt.setCanParse(false);
                            ///patentRep.save(pt);
                            continue;
                        }
                        saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
                        ZipFileUtils.unZip(savePath, saveDir);
                        FileUtils.deleteQuietly(new File(savePath));
                        File listFile = new File(Paths.get(saveDir, "\\new\\list.xml").toString());
                        if (listFile.exists()) {
                            logger.info("专利:{}的CPC包文件:{}下载成功!", pt.getShenqingh(), uploadPath);
                            QINGQIUXX obj = XmlParsor.getSingleByChildNode(listFile, "//data-bus/QINGQIUXX",
                                    QINGQIUXX.class);
                            if (obj != null) {
                                String xCode = obj.getNEIBUBH();
                                if (StringUtils.isEmpty(xCode)) {
                                    pt.setParseError("内部编号为空!");
                                    pt.setCanParse(false);
                                    patentRep.save(pt);
                                    continue;
                                }
                                logger.info("解析专利:{}的内部编号为{}", pt.getShenqingh(), xCode);
                                if (StringUtils.indexOf(xCode, ":") == -1) {
                                    logger.info("专利:{}的内部编号为{}内容不合法。", pt.getShenqingh(), xCode);
                                    pt.setParseError("内部编号:" + StringUtils.trim(xCode) + "不合法。");
                                    pt.setCanParse(false);
                                    patentRep.save(pt);
                                    continue;
                                }
                                pt.setNeibubh(xCode);
                                pt.setCanParse(true);
                                patentRep.save(pt);
                                logger.info("将:" + companyId + "的:" + pt.getShenqingbh() + "【" + pt.getFamingmc() +
                                        "】的内部编号更正为:" + xCode);
                            } else {
                                logger.info("{}的CPC中list.xml中//data-bus/QINGQIUXX取值为空!", pt.getShenqingh());
                                pt.setParseError("CPC包中list.xml内容不合法。");
                                pt.setCanParse(false);
                                patentRep.save(pt);
                            }
                        } else {
                            logger.info(listFile.getPath() + "不存在!");
                            pt.setParseError("CPC包文件在FTP服务器上不存在!");
                            pt.setCanParse(false);
                            patentRep.save(pt);
                        }
                    } catch (Exception ax) {
                        logger.info("解析:" + pt.getShenqingbh() + "[" + pt.getFamingmc() + "]的内部编号时发生错误:" + ax.getMessage());
                        logger.info("文件路径为:" + pt.getUploadPath());
                        ax.printStackTrace();
                    } finally {
                        FileUtils.deleteQuietly(new File(saveDir));
                    }
                }
                CompanyContext.set(null);
            }
        }
    }

    private void changeContext(String companyId) {
        LoginUserInfo info = new LoginUserInfo();
        info.setUserName("aa");
        info.setUserId("aa");
        info.setCompanyId(companyId);
        CompanyContext.set(info);
    }
}
