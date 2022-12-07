package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.otherOfficeFee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOtherOfficeFeeListService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<ComboboxItem> getFeeItems();

    List<ComboboxItem> getZLItems();

    Map<String, Object> getParameters(HttpServletRequest request) throws Exception;

    boolean SaveAll(List<otherOfficeFee> items);

    boolean remove(List<Integer> ids);

    void ChangePayForStatus(List<Integer> IDS, String Status);

    void ChangeXMoney(List<Integer> IDS, double Money);

    void ChangeSXMoney(List<Integer> IDS, double Money);

    boolean Remove(String ID);

    List<Map<String, Object>> getLinkMan(int ClientID);

    List<Map<String, Object>> getLinkManInfo(int LinkID);
}
