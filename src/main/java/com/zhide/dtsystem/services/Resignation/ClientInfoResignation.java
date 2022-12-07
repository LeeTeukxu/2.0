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
public class ClientInfoResignation {
    @Autowired
    tbClientRepository clientRepository;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    IAllUserListService allMapper;

    public void ChangeSignMan(Integer Transfer, Integer Resgination) throws Exception {
        List<tbClient> listClient = new ArrayList<>();
        clientRepository.findAll().stream().forEach(f -> {
            if (f.getSignMan() == Resgination) {
                tbClient client = new tbClient();
                client = f;
                client.setSignMan(Transfer);
                listClient.add(client);

                getFollowRecord(Transfer, Resgination, client);

            }
        });
        clientRepository.saveAll(listClient);
    }

    private followRecord getFollowRecord(Integer Transfer, Integer Resgination, tbClient client) {
        LoginUserInfo Info = CompanyContext.get();
        String LoginCode = loginUserMapper.getLoginCodeById(Transfer);
        LoginUserInfo login = allMapper.findUserInfoByAccountAndCompanyID("Client_" + Info.getCompanyId(),LoginCode);
        String ResginationName = loginUserMapper.getLoginUserNameById(Resgination);

        followRecord record = new followRecord();
        record.setRecord("客户登记人由：" + ResginationName + "变更为：" + login.getUserName() + "；");
        record.setFollowUserName(login.getUserName());
        record.setCreateTime(new Date());
        record.setClientId(client.getClientID());
        record.setUserName(login.getUserName());
        record.setUserId(Transfer);
        record.setRoleId(Integer.parseInt(login.getRoleId()));
        record.setRoleName(login.getRoleName());
        record.setDepId(login.getDepIdValue());

        return record;
    }
}
