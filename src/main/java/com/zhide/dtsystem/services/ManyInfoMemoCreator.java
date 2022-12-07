package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.v_PantentInfoMemo;
import org.apache.logging.log4j.util.Strings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ManyInfoMemoCreator {
    List<List<v_PantentInfoMemo>> rows;
    Integer count = 0;

    public ManyInfoMemoCreator(List<List<v_PantentInfoMemo>> ms) {
        rows = ms;
    }

    public List<String> Build(String shenqingh) {
        List<String> SS = new ArrayList<String>();
        for (List<v_PantentInfoMemo> rw : rows){
            if (rows.size() == 0) return SS;
            for (v_PantentInfoMemo row : rw){
                if (row.getShenqingh().equals(shenqingh)) {
                    count++;
                    String VK = createSingle(count, row);
                    SS.add(VK);
                }
            }
        }
        return SS;
    }

    private String createSingle(int RowNum, v_PantentInfoMemo row) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String Action = "添加";
        String ActionMan = row.getCreateManName();
        String ActionDate = simple.format(row.getCreateDate());
        if (row.getUpdateDate() != null) {
            ActionDate = simple.format(row.getUpdateDate());
            Action = "更新";
            ActionMan = row.getUpdateManName();
        }
        String Part = "";
        String All = "";
        String Memo = row.getMemo();
        String MenuName = row.getMenuName();
        if (Strings.isEmpty(MenuName)) {
            Part = "%s:%s%s备注:【%s】";
            All = Integer.toString(RowNum) + "、" + String.format(Part, ActionDate, ActionMan, Action, Memo);
        } else {
            Part = "%s:%s通过【%s】模块%s备注:【%s】";
            All = Integer.toString(RowNum) + "、" + String.format(Part, ActionDate, ActionMan, MenuName, Action, Memo);
        }
        return All;
    }
}
