package com.zhide.dtsystem.common;

import org.springframework.util.ResourceUtils;

public class GlobalContext {
    public static final String Default = "DTSystem";
    public static final String Prefix = "Client_";

    public static String getStaticUrl() {
        String path = null;
        try {
            String serverpath = ResourceUtils.getURL("classpath:static").getPath().replace("%20", " ").replace('/',
                    '\\');
            path = serverpath.substring(1);//从路径字符串中取出工程路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getTemplateUrl() {
        String path = null;
        try {
            String serverpath = ResourceUtils.getURL("classpath:templates").getPath().replace("%20", " ").replace
                    ('/', '\\');
            path = serverpath.substring(1);//从路径字符串中取出工程路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
