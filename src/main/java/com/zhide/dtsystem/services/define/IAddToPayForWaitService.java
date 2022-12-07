package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.allFeePayForResult;

import java.util.List;

public interface IAddToPayForWaitService {
    allFeePayForResult initByFeeItemIDS(String Type, List<Integer> IDS);

    allFeePayForResult OtherOfficeFeeinitByFeeItemIDS(String Type, List<Integer> IDS);

    boolean Save(allFeePayForResult result, String Type) throws Exception;
}
