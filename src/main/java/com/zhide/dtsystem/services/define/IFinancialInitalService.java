package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.FinancialInitial;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFinancialInitalService {
    pageObject getData(HttpServletRequest request) throws Exception;

    FinancialInitial Save(FinancialInitial financialInitial, String Text, String CommitType) throws Exception;

    boolean remove(List<Integer> ids);
}
