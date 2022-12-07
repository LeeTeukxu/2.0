package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbInvoiceApplication;
import com.zhide.dtsystem.models.tbInvoiceParameter;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ItbInvoiceApplicationService {
    pageObject getData(HttpServletRequest request) throws Exception;

    Page<tbInvoiceParameter> getParameter(int dtId, int pageIndex, int pageSize);

    tbInvoiceParameter saveParameter(tbInvoiceParameter invoiceParameterData, String Name) throws Exception;

    tbInvoiceApplication save(tbInvoiceApplication invoiceApplication, String Text, String CommitType) throws Exception;

    int update(tbInvoiceApplication invoiceApplication, String Text, String CommitType) throws Exception;

    boolean remove(List<Integer> ids) throws Exception;

    int updateExpressInfo(tbInvoiceApplication invoiceApplication) throws Exception;

    boolean removeParameter(List<Integer> ids);

    void InvoiceApplicationIDChange(Integer Transfer, Integer Resignation) throws Exception;
    List<Map<String,Object>> getInvoiceTotal();
}
