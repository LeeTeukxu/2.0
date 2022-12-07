package com.zhide.dtsystem.services.define;

public interface ICaseHighUserService {
    void AddOne(String CasesID, int UserID);
    void RemoveOne(String CasesID,int UserID);
    void replaceOne(String CasesID, Integer OldMan, Integer NewMan, String OldManager, String NewManager);
}
