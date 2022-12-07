package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.feeMemo;

import java.util.List;

public interface IFeeMemoService {
    boolean SaveAll(List<feeMemo> memoList);
    List<feeMemo> GetData(int ID, String Type);
    boolean DeleteByID(int ID);
    void SaveImage(feeMemo memo)throws  Exception;
    List<String> getImages(String MID) throws Exception;
}
