package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.ExceptionUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.decodeTaskErrorRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.define.INeiBuBHErrorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: NeiBuBHFixedTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月02日 16:18
 **/
@Component
public class NeiBuBHFixedTask {
    @Autowired
    pantentInfoRepository shenRep;
    @Autowired
    patentInfoPermissionRepository pInfo;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    com.zhide.dtsystem.services.NBBHCode NBBHCode;
    @Autowired
    decodeTaskErrorRepository decodeErrRep;
    @Autowired
    INeiBuBHErrorService nbErrService;
    Logger logger = LoggerFactory.getLogger(NeiBuBHFixedTask.class);


    @Scheduled(cron = "0 0 3 * * ?")
    public void process() {
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            changeContext(companyId);
            List<pantentInfo> finds = shenRep.getErrorNeiBuBHItems();
            if (finds.size() > 0) {
                logger.info("开始在Client_" + companyId + "上执行修复内部编号分配权限重复的任务!");
            }
            for (int n = 0; n < finds.size(); n++) {
                pantentInfo one = finds.get(n);
                try {
                    String XCode = one.getNeibubh();
                    if (StringUtils.isEmpty(XCode)) continue;
                    if (StringUtils.trim(XCode).isEmpty()) continue;
                    DecodeOne(one.getShenqingh(), XCode);
                } catch (Exception ax) {
                    String K = ExceptionUtils.getStrackTrace(ax);
                    logger.info("解析内部编号出错:" + K);
                }
            }
            CompanyContext.set(null);
        }
    }
    private  NBBHInfo DecodeOne(String shenqingh, String XCode) {
        LoginUserInfo info = CompanyContext.get();
        NBBHInfo getInfo = NBBHCode.Parse(XCode);
        nbErrService.SaveAll(shenqingh,getInfo);
        logger.info("开始解析:" + info.getCompanyId() + "上的:" + XCode+"，并重新应用权限。");
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
