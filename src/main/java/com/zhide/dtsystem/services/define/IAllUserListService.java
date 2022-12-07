package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.LoginUserInfo;

public interface IAllUserListService {
    String findCompanyIdByAccount(String Account);

    LoginUserInfo findUserInfoByAccountAndCompanyID(String CompanyID, String Account);


}
