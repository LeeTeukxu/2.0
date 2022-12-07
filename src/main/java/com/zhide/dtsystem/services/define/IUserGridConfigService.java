package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.userGridConfig;

public interface IUserGridConfigService {
    userGridConfig findOne(Integer UserID,String Url);
}
