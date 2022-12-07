package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.suggestUser;
import com.zhide.dtsystem.models.techSupportUser;
import com.zhide.dtsystem.repositorys.suggestUserRepository;
import com.zhide.dtsystem.repositorys.techSupportUserRepository;
import com.zhide.dtsystem.services.define.ISuggestUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: SuggestUserServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月27日 14:01
 **/
@Service
public class SuggestUserServiceImpl implements ISuggestUserService {
    @Autowired
    SysLoginUserMapper sysMapper;
    @Autowired
    suggestUserRepository userRep;
    Logger logger = LoggerFactory.getLogger(CasesUserServiceImpl.class);

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-06 13:43
     * @Params:[CasesID, UserID]
     * Description:增加一个用户ID，以及经理的ID。
     */
    @Transactional(rollbackFor = Exception.class)
    public void AddOne(Integer  MainID, Integer  UserID) {
        List<Integer> Us = new ArrayList<>();
        if (Us.contains(UserID) == false) Us.add(UserID);
        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            Optional<suggestUser> finds = userRep.findFirstByMainIdAndUserId(MainID, UserID);
            if (finds.isPresent()==false) {
                suggestUser newOne = new suggestUser();
                newOne.setMainId(MainID);
                newOne.setUserId(U);
                newOne.setCreateTime(new Date());
                userRep.save(newOne);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void AddAll(Integer MainID, String Users) {
        List<Integer> Us = ListUtils.parse(Users,Integer.class);
        for (int i = 0; i < Us.size(); i++) {
            Integer UserID = Us.get(i);
            Optional<suggestUser> finds = userRep.findFirstByMainIdAndUserId(MainID, UserID);
            if (finds.isPresent()==false) {
                suggestUser newOne = new suggestUser();
                newOne.setMainId(MainID);
                newOne.setUserId(UserID);
                newOne.setCreateTime(new Date());
                userRep.save(newOne);
            }
        }
    }
}
