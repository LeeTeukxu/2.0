package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.services.define.IAllUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllUserListServiceImpl implements IAllUserListService {
    @Autowired
    AllUserListMapper allUserMapper;

    @Override
    public String findCompanyIdByAccount(String Account) {
        return allUserMapper.findCompanyIdByAccount(Account);
    }

    @Override
    public LoginUserInfo findUserInfoByAccountAndCompanyID(String CompanyID, String Account) {
        return allUserMapper.findUserInfoByAccountAndCompanyID(CompanyID, Account);
    }
}
