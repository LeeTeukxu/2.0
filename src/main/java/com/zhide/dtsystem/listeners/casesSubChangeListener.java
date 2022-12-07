package com.zhide.dtsystem.listeners;

import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.viewModel.casesSubChangedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * @ClassName: casesSubChangeListener
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月05日 21:05
 **/
@Component
public class casesSubChangeListener {
    @Autowired
    RabbitMessageUtils rabbitUtils;
    @Autowired
    AllMessageCollections allMessage;

    @PostPersist
    public void OnAdd(casesSub obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesSubChanged(), casesSubChangedInfo.parseFrom(obj,"Add"));
        }
    }

    @PostUpdate
    public void OnUpdate(casesSub obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesSubChanged(), casesSubChangedInfo.parseFrom(obj,"Update"));
        }
    }

    @PostRemove
    public void OnRemove(casesSub obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            rabbitUtils.send(allMessage.casesSubChanged(), casesSubChangedInfo.parseFrom(obj,"Remove"));
        }
    }

}

