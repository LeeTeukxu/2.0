package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbMenuList")
public class tbMenuList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FID")
    private Integer fid;
    @Column(name = "PID")
    private Integer pid;
    @Column(name = "SN")
    private String sn;
    @Column(name = "Title")
    private String title;
    @Column(name = "Url")
    private String url;
    @Column(name = "Icon")
    private String icon;
    @Column(name = "ShortCut")
    private Boolean shortCut;
    @Column(name = "CanUse")
    private Boolean canUse;
    @Column(name = "PageName")
    private String pageName;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Boolean getShortCut() {
        return shortCut;
    }

    public void setShortCut(Boolean shortCut) {
        this.shortCut = shortCut;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }


    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


}
