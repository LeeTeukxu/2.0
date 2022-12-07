package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbInvoiceParameter;
import com.zhide.dtsystem.repositorys.tbInvoiceParameterRepository;
import com.zhide.dtsystem.services.define.ItbInvoiceParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class tbInvoiceParameterServiceImpl implements ItbInvoiceParameterService {
    @Autowired
    tbInvoiceParameterRepository tbInvoiceParameterRepository;

    @Override
    public List<TreeListItem> getAllByDtID(int dtId) {
        List<TreeListItem> results = new ArrayList<>();
        List<tbInvoiceParameter> items = tbInvoiceParameterRepository.findAllByDtIdAndCanUseTrueOrderBySn(dtId);
        items.stream().forEach(f -> {
            TreeListItem item = new TreeListItem();
            item.setPid(Integer.toString(f.getPid()));
            item.setId(Integer.toString(f.getFid()));
            item.setText(f.getName());
            results.add(item);
        });
        return results;
    }

    public List<ComboboxItem> getByDtId(int dtId) {
        List<ComboboxItem> results = new ArrayList<>();
        List<tbInvoiceParameter> items = tbInvoiceParameterRepository.findAllByDtIdAndCanUseTrueOrderBySn(dtId);
        items.stream().forEach(f -> {
            ComboboxItem item = new ComboboxItem();
            item.setId(Integer.toString(f.getFid()));
            item.setText(f.getName());
            results.add(item);
        });
        return results;
    }
}
