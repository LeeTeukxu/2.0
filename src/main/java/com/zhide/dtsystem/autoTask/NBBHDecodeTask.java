package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExceptionUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.ajRepository;
import com.zhide.dtsystem.repositorys.decodeTaskErrorRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.NBBHCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NBBHDecodeTask {
    @Autowired
    ajRepository ajRep;
    @Autowired
    pantentInfoRepository shenRep;
    @Autowired
    patentInfoPermissionRepository pInfo;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    decodeTaskErrorRepository decodeErrRep;
    Logger logger = LoggerFactory.getLogger(NBBHDecodeTask.class);

    @Scheduled(cron = "0 0/30 * * * ?")
    public void process() {
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            Date Tx = new Date();

            changeContext(companyId);
            try {
                List<aj> findAjs = ajRep.findAllNeedCopyToPantentInfos();
                int Num = 0;
                for (int a = 0; a < findAjs.size(); a++) {
                    aj aj = findAjs.get(a);
                    List<pantentInfo> findPantents = shenRep.findAllByShenqingbh(aj.getShenqingbh());
                    if (findPantents.size() == 1) {
                        pantentInfo fs = findPantents.get(0);
                        fs.setNeibubh(aj.getNeibubh());
                        fs.setNbFixed(false);
                        shenRep.save(fs);
                        Num++;
                    } else {
                        logger.info("----------" + aj.getShenqingbh() + "查找出两条专利记录-----------");
                    }
                }
                if (Num > 0) {
                    logger.info("一共从AJ中复制了" + Integer.toString(Num) + "条信息到专利信息中!");
                }
            }catch(Exception ax){

            }

            List<pantentInfo> finds = shenRep.findNeedDecodeNBBHPantents();
            if (finds.size() > 0) {
                logger.info("开始在Client_" + companyId + "上执行内部编号解析任务!");
            }
            List<decodeTaskError> errorList = new ArrayList<>();
            for (int n = 0; n < finds.size(); n++) {
                pantentInfo one = finds.get(n);
                String errorMsg = "";
                try {
                    String XCode = one.getNeibubh();
                    if (StringUtils.isEmpty(XCode)) continue;
                    if (StringUtils.trim(XCode).isEmpty()) continue;
                    NBBHInfo getInfo = DecodeOne(one.getShenqingh(), XCode);
                    if (getInfo.isDecodeAll()) {
                        one.setNbFixed(true);
                        one.setNbFixedTime(new Date());
                        shenRep.save(one);
                        logger.info(XCode + "解析成功!");
                    } else {
                        errorMsg = (XCode + "未完全解析!");
                    }
                } catch (Exception ax) {
                    String K = ExceptionUtils.getStrackTrace(ax);
                    logger.info("解析内部编号出错:" + K);
                    errorMsg = "解析：" + one.getNeibubh() + "出错:" + K;
                }

                if (StringUtils.isEmpty(errorMsg) == false) {
                    List<decodeTaskError> findOnes=decodeErrRep.findAllByShenqingh(one.getShenqingh());
                    decodeTaskError error=null;
                    if(findOnes.size()==0) {
                        error = new decodeTaskError();
                        error.setError(errorMsg);
                        error.setErrorTime(new Date());
                        error.setShenqingh(one.getShenqingh());
                    } else {
                        error=findOnes.get(0);
                        error.setErrorTime(new Date());
                    }
                    errorList.add(error);
                }
            }
            if (errorList.size() > 0) decodeErrRep.saveAll(errorList);
            CompanyContext.set(null);
        }
    }

    private NBBHInfo DecodeOne(String shenqingh, String XCode) {
        LoginUserInfo info = CompanyContext.get();
        logger.info("开始解析:" + info.getCompanyId() + "上的:" + XCode);
        NBBHInfo getInfo = NBBHCode.Parse(XCode);
        //if (getInfo.isDecodeAll() == false) return getInfo;
        getInfo.foreach((type, items) -> {
            for (int n = 0; n < items.size(); n++) {
                UInfo item = items.get(n);
                List<patentInfoPermission> all = pInfo.findAllByShenqinghAndUsertypeAndUserid(shenqingh, type, item.getID());
                if (all.size() == 0) {
                    logger.info(shenqingh + "的内部编号解析成功,准备写入数据:" + JSON.toJSONString(item));
                    patentInfoPermission px = new patentInfoPermission();
                    px.setUsertype(type);
                    px.setUserid(item.getID());
                    px.setShenqingh(shenqingh);
                    pInfo.save(px);
                }
            }
        });
        return getInfo;
    }

    private void changeContext(String companyId) {
        LoginUserInfo info = new LoginUserInfo();
        info.setUserName("aa");
        info.setUserId("aa");
        info.setCompanyId(companyId);
        CompanyContext.set(info);
    }
}
