package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbArrivalRegistration;
import com.zhide.dtsystem.models.tbCustomerRefund;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ItbCustomerRefundService {
    pageObject getData(HttpServletRequest request) throws Exception;

    tbCustomerRefund Save(tbCustomerRefund customerRefund, tbArrivalRegistration arrivalRegistration, String Text,
            String CommitType) throws Exception;

    int update(tbCustomerRefund customerRefund, tbArrivalRegistration arrivalRegistration, String Text,
            String CommitType) throws Exception;

    boolean remove(List<Integer> ids);

    tbCustomerRefund jlsp(tbCustomerRefund customerRefund) throws Exception;

    tbCustomerRefund cwsp(tbCustomerRefund customerRefund) throws Exception;

    tbCustomerRefund SaveJinLi(tbCustomerRefund customerRefund, String Text, String CommitType) throws Exception;

    tbCustomerRefund SaveCaiWu(tbCustomerRefund customerRefund, String Text, String CommitType) throws Exception;
}
