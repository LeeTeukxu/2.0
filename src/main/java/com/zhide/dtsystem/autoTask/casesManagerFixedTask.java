package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.models.casesUser;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: casesManagerFixedTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月09日 22:20
 **/
@Component
public class casesManagerFixedTask {
    @Autowired
    casesMainRepository caseMainRep;
    @Autowired
    casesUserRepository caseUserRep;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    ClientInfoMapper clientInfoMapper;
    @Autowired
    tbClientRepository clientRep;
    Logger logger = LoggerFactory.getLogger(CasesStateFixed.class);

    @Scheduled(cron = "0 0 0/6 * * ?")
    public void process() {
        LocalTime now = LocalTime.now();
        if (now.getHour() > 6) return;
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);
            casesMain target = null;
            try {
                List<casesMain> mains = caseMainRep.findAllByStateEquals(4);
                for (int n = 0; n < mains.size(); n++) {
                    casesMain main = mains.get(n);
                    String X = main.getAuditManager();
                    if (StringUtils.isEmpty(X) == false) {
                        target = main;
                        String[] Mans = X.split(",");
                        for (int m = 0; m < Mans.length; m++) {
                            Integer ID = Integer.parseInt(Mans[m]);
                            List<casesUser> users = caseUserRep.findAllByCasesIdAndUserId(main.getCasesId(), ID);
                            if (users.size() == 0) {
                                casesUser newOne = new casesUser();
                                newOne.setCasesId(main.getCasesId());
                                newOne.setUserId(ID);
                                newOne.setCreateTime(new Date());
                                caseUserRep.save(newOne);
                            }
                        }
                    }
                }

                List<Integer>Clients=clientInfoMapper.getAllNotUpdateStatus();
                for(Integer ClientID: Clients){
                    Optional<tbClient> findClient=clientRep.findById(ClientID);
                    if(findClient.isPresent()){
                        tbClient client=findClient.get();
                        client.setCootype(1);
                        clientRep.save(client);
                        logger.info("发生了交单业务的客户:"+client.getName()+"已更新为合作客户。");
                    }
                }
            } catch (Exception ax) {
                if (target != null) logger.info("修复出错:" + JSON.toJSONString(target));
                ax.printStackTrace();
            } finally {
                CompanyContext.set(null);
            }
        }
    }
}
