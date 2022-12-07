package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.AllCasesStateViewMapper;
import com.zhide.dtsystem.services.define.IAllCasesStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllCasesStateServiceImpl implements IAllCasesStateService {
    @Autowired
    AllCasesStateViewMapper allMapper;

    @Override
    public Map<String, String> getAll() {
        List<Map<String, String>> result = allMapper.getAllCasesState();
        Map<String, String> oo = new HashMap<>();
        for (int i = 0; i < result.size(); i++) {
            Map<String, String> X = result.get(i);
            oo.put(X.get("State"), X.get("Num"));
        }
        return oo;
    }
}
