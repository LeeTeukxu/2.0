package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ComboboxItem;

import java.util.List;

public interface ICLevelService {
    List<ComboboxItem> getItems();
    int RemoveOne(int ID) throws Exception;
    void Save(String Data);
}
