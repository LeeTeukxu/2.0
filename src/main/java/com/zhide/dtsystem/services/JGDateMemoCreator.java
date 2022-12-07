package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.JGDateMemo;

import java.util.ArrayList;
import java.util.List;

public class JGDateMemoCreator {
    List<JGDateMemo> rows;

    public JGDateMemoCreator(List<JGDateMemo> ms){
        rows = ms;
    }

    public List<String> Build(String CasesSubID){
        List<String> SS = new ArrayList<String>();
        if (rows.size() == 0) return SS;
        for (int i=0;i<rows.size();i++){
            JGDateMemo row = rows.get(i);
            if (row.getCasesSubId().equals(CasesSubID)){
                String VK = createSingle(i + 1, row);
                SS.add(VK);
            }
        }
        return SS;
    }

    private String createSingle(int RowNum, JGDateMemo memo){
        String All = memo.getMemo();
        return All;
    }

}
