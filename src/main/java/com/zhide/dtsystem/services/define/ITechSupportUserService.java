package com.zhide.dtsystem.services.define;

public interface ITechSupportUserService {
    void AddOne(String CasesID, int UserID);
    void AddAll(String CasesID,String Users);
    void RemoveOne(String CasesID, int UserID);
    void replaceOne(String CasesID,Integer OldMan, Integer NewMan,String OldManager, String NewManager);
}
