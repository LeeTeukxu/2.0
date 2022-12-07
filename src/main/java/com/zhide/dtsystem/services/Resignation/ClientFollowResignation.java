package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.followRecord;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.followRecordRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.services.define.IAllUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientFollowResignation {
    @Autowired
    private followRecordRepository followRecordRepository;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    IAllUserListService allMapper;
    @Autowired
    tbClientRepository clientRepository;
    @Autowired
    followRecordRepository followRep;

    public void FollowRecordUserIDChange(Integer Transfer, Integer Resgination) throws Exception {
        List<followRecord> listFollowRecod = new ArrayList<>();
        followRecordRepository.findAll().stream().forEach(f -> {
            if (f.getUserId() == Resgination) {
                followRecord followRecord = new followRecord();
                followRecord = f ;
                followRecord.setUserId(Transfer);
            }
        });
        followRecordRepository.saveAll(listFollowRecod);
    }

    public void FollowRecordAddInChange(Integer Transfer, Integer Resgination) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String LoginCode = loginUserMapper.getLoginCodeById(Transfer);
        LoginUserInfo login = allMapper.findUserInfoByAccountAndCompanyID("Client_" + Info.getCompanyId(),LoginCode);
        String ResginationName = loginUserMapper.getLoginUserNameById(Resgination);
        List<tbClient> listResgination = clientRepository.findAll().stream().filter(f -> f.getSignMan() == Resgination).collect(Collectors.toList());
        List<followRecord> listFollowRecord = new ArrayList<>();
        for (tbClient client : listResgination) {
            followRecord record = new followRecord();
            record.setRecord("登记人由：" + ResginationName + "变更为：" + login.getUserName());
            record.setFollowUserName(login.getUserName());
            record.setCreateTime(new Date());
            record.setClientId(client.getClientID());
            record.setUserName(login.getUserName());
            record.setUserId(Transfer);
            record.setRoleId(Integer.parseInt(login.getRoleId()));
            record.setRoleName(login.getRoleName());
            record.setDepId(login.getDepIdValue());
            listFollowRecord.add(record);
        }
        followRep.saveAll(listFollowRecord);
    }
}
