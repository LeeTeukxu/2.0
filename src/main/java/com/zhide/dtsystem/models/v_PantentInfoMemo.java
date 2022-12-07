package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "v_PantentInfoMemo")
public class v_PantentInfoMemo implements Serializable {
    @Id
    @Column(name = "mid")
    private String mid;
    @Column(name = "shenqingh")
    private String shenqingh;
    @Column(name = "memo")
    private String memo;
    @Column(name = "createManName")
    private String createManName;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "updateManName")
    private String updateManName;
    @Column(name = "updateDate")
    private Date updateDate;
    @Column(name = "menuName")
    private String menuName;
    @Column(name = "Edit")
    private Integer edit;
    @Column(name="ImageData")
    private String imageData;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateManName() {
        return updateManName;
    }

    public void setUpdateManName(String updateManName) {
        this.updateManName = updateManName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getEdit() {
        return edit;
    }

    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
