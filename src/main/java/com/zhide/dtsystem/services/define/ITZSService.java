package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ComboboxItem;

import java.util.List;

public interface ITZSService {
    List<ComboboxItem> getItemsByCode(String[] codes);
}
