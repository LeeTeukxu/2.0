package com.zhide.dtsystem.listeners;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

/**
 * @ClassName: PantentChangeListener
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月19日 21:25
 **/
@Component
public class PantentChangeListener {
    @Autowired
    StringRedisTemplate redisUtils;

    Logger logger= LoggerFactory.getLogger(PantentChangeListener.class);
    @PostRemove
    public void OnRemove(pantentInfo obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            String Key="AllPantentInfo::"+Info.getCompanyId();
            redisUtils.opsForHash().delete(Key,obj.getShenqingh());
        }
    }
    @PostPersist
    public void OnAdd(pantentInfo obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            try {
                String Key = "AllPantentInfo::" + Info.getCompanyId();
                redisUtils.opsForHash().put(Key, obj.getShenqingh(), Long.toString(System.currentTimeMillis()));
            }catch(Exception ax){
                logger.info("添加:"+obj.getShenqingh()+"时发生错误:"+ax.getMessage());
            }
        }
    }
}
