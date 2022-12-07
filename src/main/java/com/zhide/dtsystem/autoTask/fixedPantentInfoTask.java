package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.instance.CPCPackageParsor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class fixedPantentInfoTask {
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    pantentInfoRepository patentRep;

    Logger logger = LoggerFactory.getLogger(fixedPantentInfoTask.class);

    @Scheduled(cron = "0 0 0/4 * * ?")
    public void process() {
        List<String> companyIDS = userMapper.getCompanyList();
        FTPUtil util = new FTPUtil();
        if (util.connect() == true) {
            for (int i = 0; i < companyIDS.size(); i++) {
                String companyId = companyIDS.get(i);
                LoginUserInfo info = new LoginUserInfo();
                info.setUserName("aa");
                info.setUserId("aa");
                info.setCompanyId(companyId);
                CompanyContext.set(info);
                List<pantentInfo> Finds = patentRep.findAllNeedFixedPantentInfo();
                List<pantentInfo> Errors=patentRep.findAllByShenqinglxIsNull();
                Finds.addAll(Errors);
                if (Finds.size() > 0) {
                    logger.info("------开始从Client_" + companyId + "库中解析没成功的专利信息------");
                }

                for (int n = 0; n < Finds.size(); n++) {
                    pantentInfo Info = Finds.get(n);
                    String uploadPath = Info.getUploadPath();
                    try {
                        String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
                        util.download(uploadPath, savePath);
                        String saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
                        ZipFileUtils.unZip(savePath, saveDir);
                        FileUtils.deleteQuietly(new File(savePath));
                        CPCPackageParsor packageParsor = new CPCPackageParsor(saveDir);
                        if (packageParsor.canWork()) {
                            pantentInfo parseObj = packageParsor.getPatentInfo();
                            Info.setFamingrxm(parseObj.getFamingrxm());
                            Info.setShenqingrxm(parseObj.getShenqingrxm());
                            Info.setDailijgmc(parseObj.getDailijgmc());
                            Info.setDiyidlrxm(parseObj.getDiyidlrxm());
                            Info.setLianxirxm(parseObj.getLianxirxm());
                            Info.setLianxiryb(parseObj.getLianxiryb());
                            Info.setLianxirdz(parseObj.getLianxirdz());
                            if(Info.getShenqinglx()==null){
                                Info.setShenqinglx(parseObj.getShenqinglx());
                            }
                            Info.setCanFixed(true);
                            Info.setFixedTime(new Date());
                            if (StringUtils.isEmpty(Info.getNeibubh())) {
                                String X = parseObj.getNeibubh();
                                if (StringUtils.isEmpty(X)) {
                                    if (X.indexOf(":") > -1) {
                                        Info.setNeibubh(X);
                                    }
                                }
                            }
                            logger.info(Info.getFamingmc() + "信息已被自动任务重新找回。");
                        } else {
                            Info.setCanFixed(false);
                        }
                        patentRep.save(Info);
                        FileUtils.deleteQuietly(new File(saveDir));
                    } catch (Exception ax) {
                        ax.printStackTrace();
                    }

                }
                CompanyContext.set(null);
            }
        } else {
            logger.info("FTP登录失败，自动解析CPC包中发明人任务中止!");
        }
    }
}
