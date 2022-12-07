package com.zhide.dtsystem.common;

import java.util.Base64;

public class Base64Utils {
    public static String decode(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes());
    }
    public static  String decode(byte[] BB){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(BB);
    }
    public static byte[] decodeBuffer(String BB){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encode(BB.getBytes());
    }
}
