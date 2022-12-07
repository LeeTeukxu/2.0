package com.zhide.dtsystem.common;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @ClassName: DirectoryUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年09月26日 22:28
 **/
public class DirectoryUtils {
    public static void createNotExists(String allPath) {
        File fx = new File(allPath);
        if (fx.exists() == false) {
            fx.mkdirs();
        }
    }
    public static void  deleteAll(String allPath) throws Exception{
        FileUtils.deleteDirectory(new File(allPath));
    }
}
