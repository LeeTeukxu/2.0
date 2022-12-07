package com.zhide.dtsystem.common;

import org.springframework.util.DigestUtils;

/**
 * MD5加密，加上公司标识，使每个公司生成的密码都是不一样的。
 */
public class MD5Util {
    String mySeed;

    public MD5Util(String seed) {
        mySeed = "$(1)$" + seed + "\\^(2)??^";
    }

    public static String EnCode(String Content) {
        return DigestUtils.md5DigestAsHex(Content.getBytes());
    }
}
