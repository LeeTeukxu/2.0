package com.zhide.dtsystem.services.define;

public interface ICaseHighSubUserService {
    void importOne(String CasesID, String SubID);
    void removeOne(String CasesID,String SubID,Integer UserID);
    void addOne(String CasesID, String SubID, Integer UserID);
    void replaceOne(String CasesID,String SubID, Integer OldMan, Integer NewMan,String OldManager, String NewManager);
}
