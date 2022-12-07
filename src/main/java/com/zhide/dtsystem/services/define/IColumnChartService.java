package com.zhide.dtsystem.services.define;

import java.util.List;
import java.util.Map;

public interface IColumnChartService {
    List<String> getName(String Type, int State, String Dates) throws Exception;
    List<List<Integer>> getNameOfNum(String Type, int State, List<String> NewFields, List<String> Names, String Dates) throws Exception;
    List<String> getDynamicColumns(String Type, int State);
}
