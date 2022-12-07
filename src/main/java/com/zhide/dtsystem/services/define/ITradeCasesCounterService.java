package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.casesSubUser;
import com.zhide.dtsystem.models.casesUser;

import java.util.List;

public interface ITradeCasesCounterService {
    List<caseCounterInfo> getMain();

    List<caseCounterInfo> getSub();

    List<caseCounterInfo> getTradeCases();

    List<casesUser> getAllUser();

    List<casesSubUser> getAllSubUser();
}
