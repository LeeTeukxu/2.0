package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.companyConfig;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.repositorys.companyConfigRepository;
import com.zhide.dtsystem.services.define.ICasesCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ClearCaseMainCacheTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年10月18日 10:53
 **/
@Component
public class CaseMainCacheClearTask {
    @Autowired
    ICasesCounterService counterService;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    companyConfigRepository companyRep;
    Logger logger= LoggerFactory.getLogger(CaseMainCacheClearTask.class);
    @Scheduled(cron = "0 0 7,13,23 * * ?")
    public void process() {
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);


            String Key1="getAllCasesMain::" + companyId;
            redisTemplate.delete(Key1);
            counterService.getMain();

            String Key2="getAllCasesSub::"+companyId;
            redisTemplate.delete(Key2);
            counterService.getSub();

            logger.info("清除并重新生成了{}的{}和{}的缓存数据。",companyId,Key1,Key2);

            List<companyConfig> configs=companyRep.findAll();
            if(configs.size()>0){
                companyConfig config=configs.get(0);
                if(redisTemplate.opsForHash().hasKey("AllCompanyList",config.getCompanyCode())==false){
                    redisTemplate.opsForHash().put("AllCompanyList",config.getCompanyCode(),config.getName());
                }

            }
        }
       Set<String> allKeys= redisTemplate.keys("Login_Error_*");
        for(String key:allKeys){
            redisTemplate.delete(key);
        }
    }
}
