package com.zhide.dtsystem.services.define;

public interface ICasesSubUserService {
    void importOne(String CasesID, String SubID);

    void addOne(String CasesID, String SubID, Integer UserID);

    void removeOne(String SubID, Integer UserID);

    void replaceOne(String CasesID,String SubID, Integer OldMan, Integer NewMan,String OldManager,
            String NewManager);
}
