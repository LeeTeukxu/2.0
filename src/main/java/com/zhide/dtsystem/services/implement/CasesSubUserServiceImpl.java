package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.models.casesSubUser;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesSubUserRepository;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.services.define.ICasesSubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: CasesSubUserServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年08月22日 9:46
 **/
@Service
public class CasesSubUserServiceImpl implements ICasesSubUserService {

    @Autowired
    casesMainRepository mainRep;

    @Autowired
    casesUserRepository cUserRep;
    @Autowired
    casesSubUserRepository cSubUserRep;
    @Autowired
    SysLoginUserMapper sysMapper;

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-22 9:46
     * @Params:[CasesID] Description: 交单审核的时候把当前的人员全部插到CasesSubUser中。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importOne(String CasesID, String SubID) {
        Optional<casesMain> findMains = mainRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            casesMain main = findMains.get();
            List<String> nowUsers = new ArrayList<>();
            nowUsers.add(main.getCreateMan().toString());
            //nowUsers.addAll(Arrays.asList(main.getCreateManager().split(",")));
            nowUsers.add(main.getAuditMan().toString());
            //nowUsers.addAll(Arrays.asList(main.getAuditManager().split(",")));
            for (int i = 0; i < nowUsers.size(); i++) {
                int UserID = Integer.parseInt(nowUsers.get(i));
                Optional<casesSubUser> finds = cSubUserRep.findFirstBySubIdAndUserId(SubID, UserID);
                if (finds.isPresent() == false) {
                    casesSubUser newOne = new casesSubUser();
                    newOne.setCasesId(CasesID);
                    newOne.setSubId(SubID);
                    newOne.setUserId(UserID);
                    newOne.setCreateTime(new Date());
                    cSubUserRep.save(newOne);
                }
            }
        }
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-22 9:47
     * @Params:[CasesID, SubID, UserID]
     * Description:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOne(String CasesID, String SubID, Integer UserID) {
        List<Integer> Us = new ArrayList<>();
        if (Us.contains(UserID) == false) Us.add(UserID);

        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            Optional<casesSubUser> finds = cSubUserRep.findFirstBySubIdAndUserId(SubID, U);
            if (finds.isPresent() == false) {
                casesSubUser newOne = new casesSubUser();
                newOne.setCasesId(CasesID);
                newOne.setSubId(SubID);
                newOne.setUserId(U);
                newOne.setCreateTime(new Date());
                cSubUserRep.save(newOne);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(String SubID, Integer UserID) {
        Optional<casesSubUser> findUsers = cSubUserRep.findFirstBySubIdAndUserId(SubID, UserID);
        if (findUsers.isPresent()) cSubUserRep.delete(findUsers.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceOne(String CasesID,String SubID, Integer OldMan, Integer NewMan,String OldManager, String NewManager) {
        List<Integer> RIDS=new ArrayList<>();
        RIDS.add(OldMan);
        List<Integer> OldManagerIDS= IntegerUtils.parseIntArray(OldManager);
        RIDS.addAll(OldManagerIDS);

        cSubUserRep.deleteAllBySubIdAndUserIdIn(SubID,RIDS);

        List<Integer>AIDS=new ArrayList<>();
        AIDS.add(NewMan);
        List<Integer> NewManagerIDS=IntegerUtils.parseIntArray(NewManager);
        AIDS.addAll(NewManagerIDS);
        for(Integer UserID:AIDS){
            casesSubUser subUser=new casesSubUser();
            subUser.setUserId(UserID);
            subUser.setSubId(SubID);
            subUser.setCasesId(CasesID);
            subUser.setCreateTime(new Date());
            cSubUserRep.save(subUser);
        }
    }
}
