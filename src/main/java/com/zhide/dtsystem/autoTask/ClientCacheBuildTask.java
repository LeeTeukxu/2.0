package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.models.ClientInfo;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ClientCacheBuildTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年12月15日 22:01
 **/
@Component
public class ClientCacheBuildTask {

    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    ClientInfoMapper clientMapper;
    @Autowired
    StringRedisTemplate redisRep;

    Logger logger = LoggerFactory.getLogger(ClientCacheBuildTask.class);

    @Scheduled(cron = "0 0 0/3 * * ?")
    public void process() {
        String ClientKey = "KeHuLoginInfo::Cache";
        String UserKey = "YuLoginInfo::Cache";
        Map<String, String> caches = new HashMap<>();
        Long T1 = System.currentTimeMillis();
        List<String> companyIDS = userMapper.getCompanyList();
        Map<String, LoginUserInfo> allUsers = new HashMap<>();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);

            List<tbClient> Clients = clientMapper.getCanLoginClient();
            Clients.forEach(f -> {
                String orgCode = f.getOrgCode();
                if (caches.containsKey(orgCode) == false) {
                    ClientInfo cf = new ClientInfo();
                    cf.setClientID(f.getClientID());
                    cf.setName(f.getName());
                    cf.setPassWord(f.getPassword());
                    cf.setCompanyID(companyId);
                    cf.setType(Integer.toString(f.getType()));
                    cf.setOrgCode(f.getOrgCode());
                    caches.put(orgCode, JSON.toJSONString(cf));
                } else {
                    ClientInfo cc = JSON.parseObject(caches.get(orgCode), ClientInfo.class);
                    logger.info(cc.getName() + "在" + cc.getCompanyID() + "已存在!");
                }
            });

            List<LoginUserInfo> Users = userMapper.findAllUserInfo();
            Users.forEach(f -> {
                String account = f.getAccount();
                if (allUsers.containsKey(account) == false) {
                    f.setCompanyId(companyId);
                    allUsers.put(account, f);
                }
            });
            CompanyContext.set(null);
        }
        if (caches.size() > 0) {
            for (String OrgCode : caches.keySet()) {
                if (redisRep.opsForHash().hasKey(ClientKey, OrgCode)) {
                    redisRep.opsForHash().delete(ClientKey, OrgCode);
                }
                redisRep.opsForHash().put(ClientKey, OrgCode, caches.get(OrgCode));
            }
            Long T2 = System.currentTimeMillis();
            logger.info("一共插入公司缓存:" + Integer.toString(caches.size()) + "条,耗时:" + Long.toString(T2 - T1));
        }
        if (allUsers.size() > 0) {
            for (String account : allUsers.keySet()) {
                if (redisRep.opsForHash().hasKey(UserKey, account)) {
                    redisRep.opsForHash().delete(UserKey, account);
                }
                LoginUserInfo Info = allUsers.get(account);
                redisRep.opsForHash().put(UserKey, account, JSON.toJSONString(Info));
            }
        }
    }
}
