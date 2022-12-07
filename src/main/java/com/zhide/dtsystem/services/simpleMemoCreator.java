package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.simpleMemo;

import java.text.SimpleDateFormat;

/**
 * @ClassName: simpleMemoCreator
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月22日 21:23
 **/
public class simpleMemoCreator {
    public static String createSingle(int RowNum, simpleMemo row) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String Action = "添加";
        String ActionMan = row.getCreateManName();
        String ActionDate ="";
        if(row.getCreateTime()!=null) ActionDate=simple.format(row.getCreateTime());
        if (row.getUpdateTime() != null) {
            ActionDate = simple.format(row.getUpdateTime());
            Action = "更新";
            ActionMan = row.getUpdateManName();
        }
        String Memo = row.getMemo();
        String Part = "%s:%s%s备注:【%s】";
        String All = Integer.toString(RowNum) + "、" + String.format(Part, ActionDate, ActionMan, Action, Memo);
        return All;
    }

    public static String createSingleEx(int RowNum, simpleMemo row) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String ActionMan = row.getCreateManName();
        String ActionDate = simple.format(row.getCreateTime());
        String Memo = row.getMemo();
        String Part = "%s:%s将专利初步名称由【%s]修改为【%s】。";
        String All = Integer.toString(RowNum) + "、" + String.format(Part, ActionDate, ActionMan, row.getSource(), row.getChange());
        return All;
    }
}
