package com.zhide.dtsystem.common;

public class uploadFileResult {
    private boolean success;
    private String fullPath;
    private String message;
    private String attId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }
}
