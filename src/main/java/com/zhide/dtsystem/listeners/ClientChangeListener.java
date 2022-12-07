package com.zhide.dtsystem.listeners;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.models.ClientInfo;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.Date;

/**
 * @ClassName: ClientChangeListener
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月09日 15:08
 **/
@Component
public class ClientChangeListener {
    @Autowired
    RabbitMessageUtils messageUtils;
    @Autowired
    AllMessageCollections allMessageCollections;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    StringRedisTemplate redisRep;

    String Key = "KeHuLoginInfo::Cache";

    @PostPersist
    public void OnAdd(tbClient obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            ClientInfo client = new ClientInfo();
            client.setClientID(obj.getClientID());
            client.setName(obj.getName());
            client.setOrgCode(obj.getOrgCode());
            client.setPassWord(obj.getPassword());
            client.setCanUse(obj.getCanUse());
            client.setType("Add");
            client.setCompanyID(Info.getCompanyId());
            client.setCompanyName(Info.getCompanyName());
            client.setCreateTime(new Date());
            messageUtils.send(allMessageCollections.clientChagne(), client);
            UpdateKeHu(client);
            clearClientCache();
        }
    }

    @PostUpdate
    public void OnUpdate(tbClient obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            ClientInfo client=new ClientInfo();
            client.setClientID(obj.getClientID());
            client.setName(obj.getName());
            client.setOrgCode(obj.getOrgCode());
            client.setPassWord(obj.getPassword());
            client.setCanUse(obj.getCanUse());
            client.setType("Update");
            client.setCompanyID(Info.getCompanyId());
            client.setCompanyName(Info.getCompanyName());
            client.setCreateTime(new Date());
            messageUtils.send(allMessageCollections.clientChagne(), client);
            UpdateKeHu(client);
            clearClientCache();
        }
    }

    @PostRemove
    public void OnRemove(tbClient obj) {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            ClientInfo client=new ClientInfo();
            client.setClientID(obj.getClientID());
            client.setName(obj.getName());
            client.setOrgCode(obj.getOrgCode());
            client.setPassWord(obj.getPassword());
            client.setCanUse(obj.getCanUse());
            client.setType("Remove");
            client.setCompanyID(Info.getCompanyId());
            client.setCompanyName(Info.getCompanyName());
            client.setCreateTime(new Date());
            messageUtils.send(allMessageCollections.clientChagne(), client);

            clearClientCache();
        }
    }

    private void clearClientCache() {
        redisUtils.clearAll("Client");
    }

    private void UpdateKeHu(ClientInfo info) {
        String orgCode = info.getOrgCode();
        if(StringUtils.isEmpty(orgCode)==false) {
            if (redisRep.opsForHash().hasKey(Key, orgCode)) {
                redisRep.opsForHash().delete(Key, orgCode);
            }
            redisRep.opsForHash().put(Key, orgCode, JSON.toJSONString(info));
        }
    }
}
