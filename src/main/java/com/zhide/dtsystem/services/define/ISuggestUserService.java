package com.zhide.dtsystem.services.define;

/**
 * @ClassName: ISuggestUserService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月27日 13:52
 **/
public interface ISuggestUserService {
    void AddOne(Integer MainID,Integer UserID);
    void AddAll(Integer MainID,String IDS);
}
