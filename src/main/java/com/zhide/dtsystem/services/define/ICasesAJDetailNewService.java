package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.casesSub;

import java.util.List;

public interface ICasesAJDetailNewService {
    boolean SaveSubs(List<casesSub> rows);

    boolean Remove(String AJID) throws Exception;

    boolean RemoveSubFiles(String CasesID, String AttID);

    List<String> getSubFiles(String SubID, String Type);

    boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type);
}
