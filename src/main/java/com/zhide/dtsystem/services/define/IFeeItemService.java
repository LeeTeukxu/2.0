package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.ComboboxItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFeeItemService {
    pageObject getData(HttpServletRequest request) throws Exception;

    List<ComboboxItem> getFeeItems();

    List<ComboboxItem> getZLItems();

    boolean ChangePayMark(List<Integer> IDS, int PayState);

    boolean AddToFeeList(List<Integer> IDS, String JFQD);

    boolean ChangeJiaoDuiMoney(List<Integer> IDS, String Money);
}
