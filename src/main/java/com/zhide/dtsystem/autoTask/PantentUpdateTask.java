package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
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
 * @ClassName: PantentUpdateTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月19日 22:03
 **/
@Component
public class PantentUpdateTask {
    @Autowired
    StringRedisTemplate redisUtils;

    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    AllUserListMapper userMapper;
    Logger logger= LoggerFactory.getLogger(PantentUpdateTask.class);

    @Scheduled(cron = "0 0 7,12 * * ?")
    public void Process(){
        List<String> companyIDS = userMapper.getCompanyList();
        for (String CompanyID : companyIDS) {
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(CompanyID);
            CompanyContext.set(info);

            try {
                Long T1=System.currentTimeMillis();
                String Key = "AllPantentInfo::" + CompanyID;
                List<String> Ps = pantentRep.getAllCodes();
                Map<String, String> OS = new HashMap<>();
                for (String  P : Ps) {
                    OS.put(P,Long.toString(System.currentTimeMillis()));
                }
                redisUtils.delete(Key);
                redisUtils.opsForHash().putAll(Key,OS);
                Long T2=System.currentTimeMillis();
                logger.info("同步"+CompanyID+":"+Integer.toString(Ps.size())+"条专利，用时:"+Long.toString(T2-T1)+"毫秒。");
            }catch(Exception ax){
                logger.info("同步:"+CompanyID+"的专利号时发生了错误:"+ax.getMessage());
            }
        }
    }
}
