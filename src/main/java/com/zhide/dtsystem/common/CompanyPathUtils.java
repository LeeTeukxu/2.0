package com.zhide.dtsystem.common;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;

import java.io.File;
import java.util.UUID;

public class CompanyPathUtils {
    private static String baseDir = "D:\\Upload\\";

    public static String getFullPath(String... paths) throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) {
            throw new Exception("登录信息异常，请重新登录 。");
        }
        String companyId = info.getCompanyId();
        String fullPath = baseDir + companyId;
        createPath(fullPath);
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            path = path.replace('\\', ' ').trim();
            fullPath += "\\" + path;
            createPath(fullPath);
        }
        return fullPath;
    }
    private static  void createPath(String sPath){
        File f = new File(sPath);
        if (f.exists() == false) {
            if (FileNameUtils.isDirectory(sPath) == true) f.mkdirs();
        }
    }

    public static String getTempPath(String... paths) throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) {
            throw new Exception("登录信息异常，请重新登录。");
        }
        String TempPath = baseDir + "Temp\\";
        createPath(TempPath);
        for (int i = 0; i < paths.length; i ++) {
            String path = paths[i];
            path = path.replace('\\', ' ').trim();
            TempPath += "\\" + path;
            createPath(TempPath);
        }
        return TempPath;
    }
    public static String getTempPath() throws Exception{
        return getTempPath(UUID.randomUUID().toString());
    }

    public static String getImages() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) {
            throw new Exception("登录信息异常，请重新登录 。");
        }
        String companyId = info.getCompanyId();
        String fullPath = baseDir + "\\Images\\";
        File f = new File(fullPath);
        if (f.isDirectory()) {
            if (f.exists() == false) {
                f.mkdirs();
            }
        }
        return fullPath;
    }
}
