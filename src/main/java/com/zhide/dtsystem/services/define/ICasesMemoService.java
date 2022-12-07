package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.casesMemo;

import java.util.List;

public interface ICasesMemoService {
    boolean SaveAll(List<casesMemo> items);

    boolean Remove(String CasesID, Integer ID);
}
