package com.zhide.dtsystem.common;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class FtpPath {
    String companyId;

    public FtpPath(String companyId) {
        this.companyId = companyId;
    }

    public String getCpcPath() {
        return companyId + "/CPC/";
    }

    public String getNoticePath() {
        return companyId + "/Notice/";
    }

    public String getUpdatePath() {
        return companyId + "/Update/";
    }

    public String getTempPath() {
        return companyId + "/Temp/";
    }

    public String getCpcPath(String sub) {
        return companyId + "/CPC/" + sub;
    }

    public String getNoticePath(String sub) {
        return companyId + "/Notice/" + sub;
    }

    public String getUpdatePath(String sub) {
        return companyId + "/Update/" + sub;
    }

    public String getTempPath(String sub) {
        return companyId + "/Temp/" + sub;
    }

    public String getAttachment() {
        return companyId + "/Attachment/";
    }

    public String getRoot() {
        return companyId;
    }

    public String combine(String... paths) {
        List<String> Fs = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            String pp = paths[i].trim();
            String[] VK = pp.split("/");
            for (int n = 0; n < VK.length; n++) {
                String V = VK[i];
                if (Strings.isEmpty(V) == false) {
                    Fs.add(V);
                }
            }
        }
        return "/" + String.join("/", Fs);
    }
}
