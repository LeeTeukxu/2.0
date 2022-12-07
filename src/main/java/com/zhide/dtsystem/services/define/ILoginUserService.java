package com.zhide.dtsystem.services.define;

import java.util.Map;

public interface ILoginUserService {
    Map<String, String> getAllByNameAndID();

    Map<String, String> getAllByIDAndName();
}
