package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.casesCounterMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.casesSubUser;
import com.zhide.dtsystem.models.casesUser;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesSubUserRepository;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.services.define.ICasesCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CasesCounterService
 * @Author xiao
 * @CreateTime 2020-07-23 17:15
 **/
@Service
public class CasesCounterService implements ICasesCounterService {
    @Autowired
    casesCounterMapper counterMapper;
    @Autowired
    casesUserRepository userRep;
    @Autowired
    casesSubUserRepository subRep;
    @Autowired
    StringRedisTemplate redisRep;
    Logger logger = LoggerFactory.getLogger(CasesCounterService.class);

    @Override
    public List<caseCounterInfo> getMain() {
        LoginUserInfo Info = CompanyContext.get();
        if (Info == null) return counterMapper.getMain();
        else {
            String Key = "getAllCasesMain::" + Info.getCompanyId();
            if (redisRep.hasKey(Key)) {
                String X = redisRep.opsForValue().get(Key);
                return JSON.parseArray(X, caseCounterInfo.class);
            } else {
                List<caseCounterInfo> Cs = counterMapper.getMain();
                redisRep.opsForValue().set(Key, JSON.toJSONString(Cs));
                return Cs;
            }
        }
    }

    @Override
    public List<caseCounterInfo> getSub() {
        //return counterMapper.getSub();
        LoginUserInfo Info= CompanyContext.get();
        if(Info==null) return counterMapper.getSub();
        else {
            String Key="getAllCasesSub::"+Info.getCompanyId();
            if(redisRep.hasKey(Key)){
                String X=redisRep.opsForValue().get(Key);
                return JSON.parseArray(X,caseCounterInfo.class);
            } else {
                List<caseCounterInfo> Cs=counterMapper.getSub();
                redisRep.opsForValue().set(Key,JSON.toJSONString(Cs));
                return Cs;
            }
        }

    }

    @Override
    public List<casesUser> getAllUser() {
        return userRep.findAll();
    }

    public List<casesSubUser> getAllSubUser() {
        return subRep.findAll();
    }
}
