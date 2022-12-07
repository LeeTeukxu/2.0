package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.simpleMemo;

import java.util.List;

public interface IAddSingleMemoService {
    void SaveImage(simpleMemo memo) throws Exception;
    void SaveMemo(String SubID,List<simpleMemo> postMemos) throws Exception;
    List<simpleMemo> getData(String SubID) throws Exception;
    void RemoveMemo(String SubID,List<String> IDS) throws Exception;
}
