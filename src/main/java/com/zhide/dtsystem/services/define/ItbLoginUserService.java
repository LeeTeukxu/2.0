package com.zhide.dtsystem.services.define;


import com.zhide.dtsystem.models.tbLoginUser;

import java.util.List;

public interface ItbLoginUserService {
    tbLoginUser save(tbLoginUser user) throws Exception;

    tbLoginUser getById(int userId);

    List<tbLoginUser> getAll();
}
