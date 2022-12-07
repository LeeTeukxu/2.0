package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.caseHighCounterMapper;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.caseHighSubUser;
import com.zhide.dtsystem.models.caseHighUser;
import com.zhide.dtsystem.repositorys.caseHighSubUserRepository;
import com.zhide.dtsystem.repositorys.caseHighUserRepository;
import com.zhide.dtsystem.services.define.ICaseHighCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CasesCounterService
 * @Author xiao
 * @CreateTime 2020-07-23 17:15
 **/
@Service
public class CaseHighCounterService implements ICaseHighCounterService {
    @Autowired
    caseHighCounterMapper counterMapper;
    @Autowired
    caseHighUserRepository userRep;
    @Autowired
    caseHighSubUserRepository subRep;

    @Override
    /*@Cacheable(value="getAllCasesMain", keyGenerator = "CompanyKeyGenerator")*/
    public List<caseCounterInfo> getMain() {
        return counterMapper.getMain();
    }

    @Override
    /*@Cacheable(value="getAllCasesSub",keyGenerator = "CompanyKeyGenerator")*/
    public List<caseCounterInfo> getSub() {
        return counterMapper.getSub();
    }

    @Override
    public List<caseHighUser> getAllUser() {
        return userRep.findAll();
    }

    public List<caseHighSubUser> getAllSubUser() {
        return subRep.findAll();
    }
}
