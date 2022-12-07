package com.zhide.dtsystem.common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: AESUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年05月30日 22:21
 **/
public class AESUtils {
    private static final String AES="AES";
    private static final String CHAR_SET_NAME1="UTF-8";
    private static final String CHAR_SET_NAME2="ASCII";
    private static final String CIPHER_KEY="AES/CBC/PKCS5Padding";

    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static final String IV_PARAMETER="af.l980c_108x96O";
    /**
     * 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，需要为16位。
     */
    private static final String S_KEY="cy7x98.6z_20li5x";

    public static String encrypt(String param) throws Exception {
        Cipher cipher= Cipher.getInstance(CIPHER_KEY);
        SecretKeySpec skeySpec = new SecretKeySpec(S_KEY.getBytes(), AES);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        // 此处使用BASE64做转码。
        return new BASE64Encoder().encode(cipher.doFinal(param.getBytes(CHAR_SET_NAME1)));
    }
    public static String decrypt(String value) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(S_KEY.getBytes(CHAR_SET_NAME2), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_KEY);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // 先用base64解密
        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(value)), CHAR_SET_NAME1);
    }
}
