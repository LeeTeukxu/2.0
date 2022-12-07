package com.zhide.dtsystem.controllers.InvoiceApplication;

import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbInvoiceParameter;
import com.zhide.dtsystem.repositorys.tbInvoiceParameterRepository;
import com.zhide.dtsystem.services.define.ItbInvoiceParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/InvoiceApplication/invoiceparameter")
public class tbInvoiceParameterController {
    @Autowired
    ItbInvoiceParameterService itbInvoiceParameterService;
    @Autowired
    tbInvoiceParameterRepository tbInvoiceParameterRepository;

    @ResponseBody
    @RequestMapping("/getAllByDtId")
    public List<TreeListItem> getAllByDtId(int dtId) {
        return itbInvoiceParameterService.getAllByDtID(dtId);
    }

    @RequestMapping("/getByDtId")
    @ResponseBody
    public List<ComboboxItem> getByDtId(int dtId) {
        return itbInvoiceParameterService.getByDtId(dtId);
    }

    @ResponseBody
    @RequestMapping("/getNameByFIdAndId")
    public successResult GetNameByFIdAndId(Integer FId, Integer Id) {
        successResult result = new successResult();
        tbInvoiceParameter invoiceParameter = null;
        try {
            invoiceParameter = tbInvoiceParameterRepository.findAllByFid(FId);
            result.setData(invoiceParameter);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
