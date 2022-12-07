package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.casesYwItems;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;

import java.util.List;

public interface ICasesYwDetailNewService {
    boolean SaveSubs(List<casesYwItems> rows);

    boolean Remove(String YwID) throws Exception;

    List<String> getSubFiles(String SubID, String Type);

    boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type);
    void SaveSelectMoney(SelectMoneyInfo Info) throws Exception;
}
