package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.cases;

import java.util.Map;

public interface ICaseService {
    cases SaveAll(Map<String, Object> Data) throws Exception;

    boolean Commit(int ID, String Result, String ResultText) throws Exception;

    boolean Remove(String CasesID) throws Exception;
}
