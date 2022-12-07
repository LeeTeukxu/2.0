package com.zhide.dtsystem.common;

import java.util.HashMap;
import java.util.Map;

public class integerNumber {
    static Map<Integer, String> nums = null;

    static {
        nums = new HashMap<Integer, String>();
        nums.put(1, "一");
        nums.put(2, "二");
        nums.put(3, "三");
        nums.put(4, "四");
        nums.put(5, "五");
        nums.put(6, "六");
        nums.put(7, "七");
        nums.put(8, "八");
        nums.put(9, "九");
        nums.put(10, "十");
        nums.put(11, "十一");
        nums.put(12, "十二");
        nums.put(13, "十三");
        nums.put(14, "十四");
        nums.put(15, "十五");
        nums.put(16, "十六");
        nums.put(17, "十七");
        nums.put(18, "十八");
        nums.put(19, "十九");
        nums.put(20, "二十");
    }

    public static String toChinese(int num) {
        return nums.get(num);
    }
}
