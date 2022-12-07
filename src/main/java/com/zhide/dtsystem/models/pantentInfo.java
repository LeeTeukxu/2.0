package com.zhide.dtsystem.models;

import com.zhide.dtsystem.listeners.PantentChangeListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PantentInfo")
@EntityListeners({PantentChangeListener.class})
public class pantentInfo implements Serializable {
    @Id
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "SHENQINGBH")
    private String shenqingbh;
    @Column(name = "NEIBUBH")
    private String neibubh;
    @Column(name = "FAMINGMC")
    private String famingmc;
    @Column(name = "SHENQINGLX")
    private Integer shenqinglx;
    @Column(name = "ANJIANYWZT")
    private String anjianywzt;
    @Column(name = "SHENQINGR")
    private Date shenqingr;
    @Column(name = "ANJUANH")
    private String anjuanh;
    @Column(name = "FAMINGRXM")
    private String famingrxm;
    @Column(name = "LIANXIRXM")
    private String lianxirxm;
    @Column(name = "LIANXIRYB")
    private String lianxiryb;
    @Column(name = "LIANXIRDZ")
    private String lianxirdz;
    @Column(name = "DAILIJGMC")
    private String dailijgmc;
    @Column(name = "DIYIDLRXM")
    private String diyidlrxm;
    @Column(name = "ANJUANBH")
    private String anjuanbh;
    @Column(name = "JIAJI")
    private Boolean jiaji;
    @Column(name = "CHUANGJIANR")
    private Date chuangjianr;
    @Column(name = "LASTUPDATETIME")
    private Date lastupdatetime;
    @Column(name = "ZHUFENLH")
    private String zhufenlh;
    @Column(name = "SHENQINGRXM")
    private String shenqingrxm;
    @Column(name = "JIEAN")
    private Boolean jiean;
    @Column(name = "LiXiang")
    private Boolean liXiang;
    @Column(name = "UploadPath")
    private String uploadPath;
    @Column(name = "UploadTime")
    private Date uploadTime;
    @Column(name = "CanFixed")
    private Boolean canFixed;
    @Column(name = "FixedTime")
    private Date fixedTime;
    @Column(name = "NBFixed")
    private Boolean nbFixed;
    @Column(name = "NBFixedTime")
    private Date nbFixedTime;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "ApplyWatch")
    private Integer applyWatch;
    @Column(name = "YearWatch")
    private Integer yearWatch;
    @Column(name = "ApplySource")
    private Integer applySource;
    @Column(name = "YearSource")
    private Integer yearSource;
    @Column(name = "OldFilePath")
    private String oldFilePath;
    @Column(name="CanParse")
    Boolean canParse;
    @Column(name="ParseError")
    String parseError;
    @Column(name="SQTYPE")
    String sqType;

    public String getOldFilePath() {
        return oldFilePath;
    }

    public void setOldFilePath(String oldFilePath) {
        this.oldFilePath = oldFilePath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShenqingbh() {
        return shenqingbh;
    }

    public void setShenqingbh(String shenqingbh) {
        this.shenqingbh = shenqingbh;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public String getNeibubh() {
        return neibubh;
    }

    public void setNeibubh(String neibubh) {
        this.neibubh = neibubh;
    }


    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }


    public Integer getShenqinglx() {
        return shenqinglx;
    }

    public void setShenqinglx(Integer shenqinglx) {
        this.shenqinglx = shenqinglx;
    }


    public String getAnjianywzt() {
        return anjianywzt;
    }

    public void setAnjianywzt(String anjianywzt) {
        this.anjianywzt = anjianywzt;
    }


    public Date getShenqingr() {
        return shenqingr;
    }

    public void setShenqingr(Date shenqingr) {
        this.shenqingr = shenqingr;
    }


    public String getAnjuanh() {
        return anjuanh;
    }

    public void setAnjuanh(String anjuanh) {
        this.anjuanh = anjuanh;
    }


    public String getFamingrxm() {
        return famingrxm;
    }

    public void setFamingrxm(String famingrxm) {
        this.famingrxm = famingrxm;
    }


    public String getLianxirxm() {
        return lianxirxm;
    }

    public void setLianxirxm(String lianxirxm) {
        this.lianxirxm = lianxirxm;
    }


    public String getLianxiryb() {
        return lianxiryb;
    }

    public void setLianxiryb(String lianxiryb) {
        this.lianxiryb = lianxiryb;
    }


    public String getLianxirdz() {
        return lianxirdz;
    }

    public void setLianxirdz(String lianxirdz) {
        this.lianxirdz = lianxirdz;
    }


    public String getDailijgmc() {
        return dailijgmc;
    }

    public void setDailijgmc(String dailijgmc) {
        this.dailijgmc = dailijgmc;
    }


    public String getDiyidlrxm() {
        return diyidlrxm;
    }

    public void setDiyidlrxm(String diyidlrxm) {
        this.diyidlrxm = diyidlrxm;
    }


    public String getAnjuanbh() {
        return anjuanbh;
    }

    public void setAnjuanbh(String anjuanbh) {
        this.anjuanbh = anjuanbh;
    }


    public Boolean getJiaji() {
        return jiaji;
    }

    public void setJiaji(Boolean jiaji) {
        this.jiaji = jiaji;
    }


    public Date getChuangjianr() {
        return chuangjianr;
    }

    public void setChuangjianr(Date chuangjianr) {
        this.chuangjianr = chuangjianr;
    }


    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }


    public String getZhufenlh() {
        return zhufenlh;
    }

    public void setZhufenlh(String zhufenlh) {
        this.zhufenlh = zhufenlh;
    }


    public String getShenqingrxm() {
        return shenqingrxm;
    }

    public void setShenqingrxm(String shenqingrxm) {
        this.shenqingrxm = shenqingrxm;
    }


    public Boolean getJiean() {
        return jiean;
    }

    public void setJiean(Boolean jiean) {
        this.jiean = jiean;
    }


    public Boolean getLiXiang() {
        return liXiang;
    }

    public void setLiXiang(Boolean liXiang) {
        this.liXiang = liXiang;
    }


    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }


    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }


    public Boolean getCanFixed() {
        return canFixed;
    }

    public void setCanFixed(Boolean canFixed) {
        this.canFixed = canFixed;
    }


    public Date getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(Date fixedTime) {
        this.fixedTime = fixedTime;
    }


    public Boolean getNbFixed() {
        return nbFixed;
    }

    public void setNbFixed(Boolean nbFixed) {
        this.nbFixed = nbFixed;
    }


    public Date getNbFixedTime() {
        return nbFixedTime;
    }

    public void setNbFixedTime(Date nbFixedTime) {
        this.nbFixedTime = nbFixedTime;
    }

    public Integer getApplyWatch() {
        return applyWatch;
    }

    public void setApplyWatch(Integer applyWatch) {
        this.applyWatch = applyWatch;
    }

    public Integer getYearWatch() {
        return yearWatch;
    }

    public void setYearWatch(Integer yearWatch) {
        this.yearWatch = yearWatch;
    }

    public Integer getApplySource() {
        return applySource;
    }

    public void setApplySource(Integer applySource) {
        this.applySource = applySource;
    }

    public Integer getYearSource() {
        return yearSource;
    }

    public void setYearSource(Integer yearSource) {
        this.yearSource = yearSource;
    }

    public Boolean getCanParse() {
        if(canParse==null)canParse=true;
        return canParse;
    }

    public void setCanParse(Boolean canParse) {
        this.canParse = canParse;
    }

    public String getParseError() {
        return parseError;
    }

    public void setParseError(String parseError) {
        this.parseError = parseError;
    }

    public String getSqType() {
        return sqType;
    }

    public void setSqType(String sqType) {
        this.sqType = sqType;
    }
}
