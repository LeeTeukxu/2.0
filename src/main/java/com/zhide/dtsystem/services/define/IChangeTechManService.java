package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ChangeTechManResult;

import java.util.List;
import java.util.Map;

public interface IChangeTechManService {
    boolean SaveAll(List<ChangeTechManResult> Datas, Map<Integer, String> Users) throws Exception;
}
