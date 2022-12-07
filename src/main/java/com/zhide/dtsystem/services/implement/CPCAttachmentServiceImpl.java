package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.services.define.ICPCAttachmentService;
import com.zhide.dtsystem.services.instance.CPCPackageParsor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CPCAttachmentServiceImpl implements ICPCAttachmentService {

    org.slf4j.Logger logger = LoggerFactory.getLogger(CPCAttachmentServiceImpl.class);
    @Autowired
    com.zhide.dtsystem.repositorys.pantentInfoRepository pantentInfoRepository;

    @Override
    public void UpdateByPackage(String fullName, String fullPath, String SHENQINGBH, String unZipDir) {
        List<pantentInfo> infos = pantentInfoRepository.findAllByShenqingbh(SHENQINGBH);
        if (infos.size() > 0) {
            pantentInfo info = infos.get(0);
            String OldPath=info.getUploadPath();
            List<String> X=new ArrayList<>();
            if(StringUtils.isEmpty(OldPath)==false){
                X= Arrays.stream(OldPath.trim().split(",")).collect(Collectors.toList());
            }
            X.add(info.getUploadPath());
            info.setUploadPath(fullPath);
            info.setOldFilePath(StringUtils.join(X,","));
            info.setUploadTime(new Date());
            CPCPackageParsor packageParsor = new CPCPackageParsor(unZipDir);
            if (packageParsor.canWork()) {
                pantentInfo parseObj = packageParsor.getPatentInfo();
                if (parseObj != null && SHENQINGBH.equals(parseObj.getShenqingbh())) {
                    if (Strings.isNotEmpty(parseObj.getLianxirdz())) info.setLianxirdz(parseObj.getLianxirdz());
                    if (Strings.isNotEmpty(parseObj.getLianxirxm())) info.setLianxirxm(parseObj.getLianxirxm());
                    if (Strings.isNotEmpty(parseObj.getLianxiryb())) info.setLianxiryb(parseObj.getLianxiryb());
                    if (Strings.isNotEmpty(parseObj.getFamingrxm())) info.setFamingrxm(parseObj.getFamingrxm());
                    if (Strings.isNotEmpty(parseObj.getShenqingrxm())) info.setShenqingrxm(parseObj.getShenqingrxm());
                    if (Strings.isNotEmpty(parseObj.getDailijgmc())) info.setDailijgmc(parseObj.getDailijgmc());
                    if (Strings.isNotEmpty(parseObj.getDiyidlrxm())) info.setDiyidlrxm(parseObj.getDiyidlrxm());
                    if (Strings.isNotEmpty(parseObj.getNeibubh()))info.setNeibubh(parseObj.getNeibubh());
                    logger.info("已从CPC案卷包中更新了" + SHENQINGBH + "的专利请人、代理、代理人等信息。");
                }
            } else {
                logger.info(unZipDir + "中不包含list.xml文件，本次请求只上传了CPC文件，没有对专利信息进行更新。");
            }
            pantentInfoRepository.saveAndFlush(info);
        }
    }
}
