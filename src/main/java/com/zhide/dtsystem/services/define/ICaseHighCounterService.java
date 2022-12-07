package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.caseHighSubUser;
import com.zhide.dtsystem.models.caseHighUser;

import java.util.List;

public interface ICaseHighCounterService {
    List<caseCounterInfo> getMain();

    List<caseCounterInfo> getSub();

    List<caseHighUser> getAllUser();

    List<caseHighSubUser> getAllSubUser();
}
