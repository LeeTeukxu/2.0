package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.viewModel.ExportFeeItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFeeWaitListService {
    public pageObject getData(HttpServletRequest request) throws Exception;

    boolean confirm(int ID, String SHENQINGH, String OtherOfficeID, String auditText) throws Exception;

    boolean reject(int ID) throws Exception;
    List<ExportFeeItem> getSelectedDatas(List<Integer> IDS);
}
