package com.zhide.dtsystem.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;
import java.util.Date;

public class FTPUtil {
    FTPConfig FtpConfig;
    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    private FTPClient ftpClient;
    //从本地文件获取各种属性
    private String ftpIP;
    private Integer ftpPort;
    private String ftpUserName;
    private String ftpPassword;
    private String ftpEncode;

    public FTPUtil() {
        FtpConfig = new FTPConfig();
        ftpIP = FtpConfig.getFTPHost();
        ftpPort = FtpConfig.getFTPPort();
        ftpUserName = FtpConfig.getUserName();
        ftpPassword = FtpConfig.getPassword();
        ftpEncode = FtpConfig.getFTPEncode();
    }

    public synchronized boolean connect() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpEncode);//解决上传文件时文件名乱码
        int reply = 0;
        try {
            // 连接至服务器
            ftpClient.connect(ftpIP, ftpPort);
            // 登录服务器
            ftpClient.login(ftpUserName, ftpPassword);
            //登陆成功，返回码是230
            reply = ftpClient.getReplyCode();
            // 判断返回码是否合法
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.info("登录FTP失败，用户名:" + ftpUserName + ",密码:" + ftpPassword);
                return false;
            }
            ftpClient.setControlKeepAliveTimeout(3000L);
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //判断ftp服务器文件是否存在
    public synchronized boolean existFile(String path) {
        boolean flag = false;
        FTPFile[] ftpFileArr;
        try {
            ftpFileArr = ftpClient.listFiles(path);
            if (ftpFileArr.length > 0) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public synchronized boolean existDirectory(String folderName) {
        boolean flag = false;
        try {
            flag = ftpClient.changeWorkingDirectory(folderName);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return flag;
    }

    //删除ftp文件
    public synchronized boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            connect();
            ftpClient.changeWorkingDirectory(pathname);
            flag = ftpClient.deleteFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
        return flag;
    }

    //从FTP server下载到本地文件夹
    public synchronized void download(String filePath, String saveFilePath) throws Exception {
        FTPFile[] fs = null;
        byte[] bs = null;
        OutputStream outputStream = null;
        try {
            fs = ftpClient.listFiles(filePath);
            if (fs.length <= 0) {
                throw new Exception(filePath + "在FTP服务器中不存在!");
            }
            FTPFile ftpFile = fs[0];
            File NewFile = new File(saveFilePath);
            outputStream = new FileOutputStream(NewFile);
            ftpClient.setBufferSize(1024);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(filePath, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
    }
    public synchronized byte[] download(String filePath) throws Exception {
        FTPFile[] fs = null;
        byte[] bs = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fs = ftpClient.listFiles(filePath);
            if (fs.length <= 0) {
                throw new Exception(filePath + "在FTP服务器中不存在!");
            }
            FTPFile ftpFile = fs[0];
            outputStream = new ByteArrayOutputStream();
            ftpClient.setBufferSize(1024);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(filePath, outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
        return new byte[0];
    }
    private void makeDirectorys(String path) throws Exception {
        String[] dirs = path.split("/");
        for (int i = 0; i < dirs.length; i++) {
            String p = dirs[i];
            if (p.isEmpty()) continue;
            boolean changeOK = ftpClient.changeWorkingDirectory(p);
            if (changeOK == false) {
                boolean createOK = ftpClient.makeDirectory(p);
                logger.info("创建" + p + (createOK == true ? "成功" : "失败"));
                if (createOK == true) ftpClient.changeWorkingDirectory(p);
            }
        }
    }

    public synchronized boolean upload(InputStream inputStream, String fileName, String path) {
        boolean result = false;
        long begin = new Date().getTime();
        if (ftpClient == null) connect();
        if (ftpClient.isConnected() == false) connect();
        try {
            if (ftpClient.changeWorkingDirectory(path) == false) {
                makeDirectorys(path);
            }
            ftpClient.setBufferSize(1024);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (StringUtils.startsWith(path, "/") == false) path = "/" + path;
            result = ftpClient.storeFile(path + fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end = new Date().getTime();
            //logger.info("上传"+fileName+"共耗时:"+Long.toString(end-begin)+"毫秒.");
        }
        return result;
    }

    public boolean checkSubfolder(String path, String subfolderName) {
        try {
            //切换到FTP根目录
            ftpClient.changeWorkingDirectory(path);
            //查看根目录下是否存在该文件夹
            InputStream is = ftpClient.retrieveFileStream(new String(subfolderName.getBytes("UTF-8")));
            if (is == null || ftpClient.getReplyCode() == FTPReply.FILE_UNAVAILABLE) {
                //若不存在该文件夹，则创建文件夹
                return createSubfolder(path, subfolderName);
            }
            if (is != null) {
                is.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean createSubfolder(String path, String subfolderName) {
        try {
            ftpClient.changeWorkingDirectory(path);
            ftpClient.makeDirectory(subfolderName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 断开与远程服务器的连接
     */
    public void close() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}