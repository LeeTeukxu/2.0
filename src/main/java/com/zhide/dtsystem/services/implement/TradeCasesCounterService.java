package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.tradeCasesCounterMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.casesSubUser;
import com.zhide.dtsystem.models.casesUser;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesSubUserRepository;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.services.define.ITradeCasesCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CasesCounterService
 * @Author xiao
 * @CreateTime 2020-07-23 17:15
 **/
@Service
public class TradeCasesCounterService implements ITradeCasesCounterService {
    @Autowired
    tradeCasesCounterMapper mapper;
    @Autowired
    casesUserRepository userRep;
    @Autowired
    casesSubUserRepository subRep;
    @Autowired
    StringRedisTemplate redisRep;
    Logger logger = LoggerFactory.getLogger(TradeCasesCounterService.class);

    @Override
    public List<caseCounterInfo> getMain() {
        LoginUserInfo Info = CompanyContext.get();
        if (Info == null) return mapper.getMain();
        else {
            String Key = "getAllCasesMain::" + Info.getCompanyId();
            if (redisRep.hasKey(Key)) {
                String X = redisRep.opsForValue().get(Key);
                return JSON.parseArray(X, caseCounterInfo.class);
            } else {
                List<caseCounterInfo> Cs = mapper.getMain();
                redisRep.opsForValue().set(Key, JSON.toJSONString(Cs));
                return Cs;
            }
        }
    }

    @Override
    public List<caseCounterInfo> getSub() {
        Map<String, Object> Param = getParams();
        return mapper.getSub(Param);
//        LoginUserInfo Info= CompanyContext.get();
//        if(Info==null) return counterMapper.getSub();
//        else {
//            String Key="getAllCasesSub::"+Info.getCompanyId();
//            if(redisRep.hasKey(Key)){
//                String X=redisRep.opsForValue().get(Key);
//                return JSON.parseArray(X,caseCounterInfo.class);
//            } else {
//                List<caseCounterInfo> Cs=counterMapper.getSub();
//                redisRep.opsForValue().set(Key,JSON.toJSONString(Cs));
//                return Cs;
//            }
//        }
    }

    @Override
    public List<caseCounterInfo> getTradeCases() {
        Map<String,Object> Param = getParams();
        return mapper.getTradeCases(Param);
    }

    private Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }

    @Override
    public List<casesUser> getAllUser() {
        return userRep.findAll();
    }

    public List<casesSubUser> getAllSubUser() {
        return subRep.findAll();
    }
}
