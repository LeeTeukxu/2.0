package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CaseReqFile")
public class caseReqFile implements Serializable {
    @Id
    @Column(name = "FileID")
    private String fileId;
    @Column(name = "CaseID")
    private String caseId;
    @Column(name = "SavePath")
    private String savePath;
    @Column(name = "UploadMan")
    private int uploadMan;
    @Column(name = "UploadTime")
    private Date uploadTime;
    @Column(name = "IsArchive")
    private boolean isArchive;
    @Column(name = "Size")
    private int size;
    @Column(name = "ArchiveMan")
    private int archiveMan;
    @Column(name = "ArchiveTime")
    private Date archiveTime;
    @Column(name = "FileName")
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public int getUploadMan() {
        return uploadMan;
    }

    public void setUploadMan(int uploadMan) {
        this.uploadMan = uploadMan;
    }


    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }


    public boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public int getArchiveMan() {
        return archiveMan;
    }

    public void setArchiveMan(int archiveMan) {
        this.archiveMan = archiveMan;
    }


    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
