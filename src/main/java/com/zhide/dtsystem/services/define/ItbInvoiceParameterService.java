package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TreeListItem;

import java.util.List;

public interface ItbInvoiceParameterService {
    List<TreeListItem> getAllByDtID(int dtId);

    List<ComboboxItem> getByDtId(int dtId);
}
