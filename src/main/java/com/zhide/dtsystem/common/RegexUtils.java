package com.zhide.dtsystem.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RegexUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月24日 14:02
 **/
public class RegexUtils {
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 是个合格的邮件地址吗？

     * @return
     */
    public static  boolean IsEmail(String Code){
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(Code);
        return  matcher.matches();
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 是个合格的手机号码吗？

     * @return
     */
    public static  boolean IsMobile(String Code){
        String check = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(Code);
        return  matcher.matches();
    }
    public static boolean IsPhone(String Code){
        String check="^(0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8})";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(Code);
        return  matcher.matches();
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 是个合格的身份证号码吗？

     * @return
     */
    public static  boolean IsIDCard(String Code){
        String check = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(Code);
        return  matcher.matches();
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     社会信用代码校验。
     * @return
     */
    public static boolean IsCompanyCard(String Code){
        String  check = "^[0-9A-Z]{18}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(Code);
        return  matcher.matches();
    }
}
