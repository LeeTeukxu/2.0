package com.zhide.dtsystem.common;

import java.io.InputStream;

public class UploadUtils {
    private String companyId;
    FtpPath ftpPath;
    FTPUtil ftpUtil;

    public static UploadUtils getInstance(String companyId) {
        return new UploadUtils(companyId);
    }

    private UploadUtils(String companyId) {
        this.companyId = companyId;
        ftpPath = new FtpPath(companyId);
        ftpUtil = new FTPUtil();
    }

    public uploadFileResult uploadUpdateFile(String simpleName, InputStream stream) {
        uploadFileResult result = new uploadFileResult();
        String path = ftpPath.getUpdatePath();
        boolean oo = false;
        try {
            oo = ftpUtil.upload(stream, simpleName, path);
        } catch (Exception e) {
            e.printStackTrace();
            oo = false;
        }
        result.setFullPath(path + simpleName);
        result.setSuccess(oo);
        return result;
    }

    public uploadFileResult uploadNoticeFile(String simpleName, InputStream stream) {
        uploadFileResult result = new uploadFileResult();
        String path = ftpPath.getNoticePath();
        boolean oo = false;
        try {
            oo = ftpUtil.upload(stream, simpleName, path);
        } catch (Exception e) {
            e.printStackTrace();
            oo = false;
        }
        result.setFullPath(path + simpleName);
        result.setSuccess(oo);
        return result;
    }

    public uploadFileResult uploadCPCFile(String simpleName, InputStream stream) {
        uploadFileResult result = new uploadFileResult();
        String path = ftpPath.getCpcPath();
        boolean oo = false;
        try {
            oo = ftpUtil.upload(stream, simpleName, path);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            oo = false;
        }
        result.setFullPath(path + simpleName);
        result.setSuccess(oo);
        return result;
    }

    public uploadFileResult uploadAttachment(String fileName, InputStream stream) {
        uploadFileResult result = new uploadFileResult();
        String path = ftpPath.getAttachment();
        boolean oo = false;
        try {
            oo = ftpUtil.upload(stream, fileName, path);
        } catch (Exception ax) {
            ax.printStackTrace();
            result.setMessage(ax.getMessage());
            oo = false;
        }
        result.setFullPath(path + fileName);
        result.setSuccess(oo);
        return result;
    }

    public void downloadNoticeFile(String fileName) throws Exception {
        String filePath = ftpPath.getNoticePath();
        filePath += fileName;
        ftpUtil.download(filePath, "");
    }

    public boolean existsNoticeFile(String filePath) {
        return false;
    }
}
