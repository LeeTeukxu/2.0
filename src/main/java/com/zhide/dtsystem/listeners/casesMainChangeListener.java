package com.zhide.dtsystem.listeners;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.viewModel.casesMainChangedInfo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: casesMainChangeListener
 * @Author: 肖新民
 * @*TODO:动态处理getStateNumber的缓存数据。
 * @CreateTime: 2020年12月05日 21:05
 **/
@Component
public class casesMainChangeListener {
    @Autowired
    StringRedisTemplate redisRep;

    @Autowired
    RabbitMessageUtils rabbitUtils;
    @Autowired
    AllMessageCollections allMessage;
    org.slf4j.Logger logger = LoggerFactory.getLogger(casesMainChangeListener.class);
    String Key = "getAllCasesMain";

    @PostPersist
    public void OnAdd(casesMain obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesMainChanged(), casesMainChangedInfo.parseFrom(obj,"Add"));
        }
    }

    @PostUpdate
    public void OnUpdate(casesMain obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesMainChanged(), casesMainChangedInfo.parseFrom(obj,"Update"));
        }
    }

    @PostRemove
    public void OnRemove(casesMain obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesMainChanged(), casesMainChangedInfo.parseFrom(obj,"Remove"));
        }
    }
}

