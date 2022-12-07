package com.zhide.dtsystem.common;

import org.springframework.stereotype.Component;

@Component
public class FTPConfig {
    private String password;
    private String username;
    private String port;
    private String host;

    public FTPConfig() {
        password = ApplicationPropertyUtils.getProperty("Ftp.password");
        username = ApplicationPropertyUtils.getProperty("Ftp.username");
        port = ApplicationPropertyUtils.getProperty("Ftp.port");
        host = ApplicationPropertyUtils.getProperty("Ftp.host");
    }

    public String getLocalFilePath() {
        return "";
    }

    public String getFTPEncode() {
        return "utf-8";
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return username;
    }

    public Integer getFTPPort() {
        return Integer.parseInt(port);
    }

    public String getFTPHost() {
        return host;
    }
}
