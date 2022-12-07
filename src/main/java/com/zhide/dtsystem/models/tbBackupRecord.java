package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbBackupRecord")
public class tbBackupRecord implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "BackupFile")
    private String backupFile;
    @Column(name = "BackupTime")
    private Date backupTime;
    @Column(name = "BackupMan")
    private int backupMan;
    @Column(name = "BackDataBase")
    private String backDataBase;
    @Column(name = "DownloadCount")
    private int downloadCount;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public String getBackupFile() {
        return backupFile;
    }

    public void setBackupFile(String backupFile) {
        this.backupFile = backupFile;
    }


    public Date getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }


    public int getBackupMan() {
        return backupMan;
    }

    public void setBackupMan(int backupMan) {
        this.backupMan = backupMan;
    }


    public String getBackDataBase() {
        return backDataBase;
    }

    public void setBackDataBase(String backDataBase) {
        this.backDataBase = backDataBase;
    }


    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

}
