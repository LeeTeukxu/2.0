package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.allUsersList;

public interface AllUsersListMapper {
    public int getNumByAccount(String account);

    public int addLoginUserInfo(allUsersList Info);

    public int deleteLoginUser(String Account);
}
