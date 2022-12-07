package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbArrivalRegistration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ItbArrivalService {
    pageObject getData(HttpServletRequest request) throws Exception;
    pageObject getWorkData(HttpServletRequest request) throws Exception;
    tbArrivalRegistration Save(tbArrivalRegistration tbArrivalRegistration, String Text, String CommitType,
            String Sub) throws Exception;

    int update(tbArrivalRegistration tbArrivalRegistration, String Text, String CommitType) throws Exception;

    tbArrivalRegistration SaveRenLin(tbArrivalRegistration tbArrivalRegistration, String Text, String CommitType,
            String Sub) throws Exception;

    tbArrivalRegistration CommitRenLin(tbArrivalRegistration tbArrivalRegistration, String Text, String CommitType,
            String Sub) throws Exception;

    tbArrivalRegistration SaveFuHe(tbArrivalRegistration tbArrivalRegistration, String Text, String CommitType) throws Exception;

    boolean remove(List<Integer> ids) throws  Exception;
    void cancelAudit(Integer ID) throws Exception;

    void FinanceIDChange(Integer Transfer, Integer Resignation) throws Exception;
}
