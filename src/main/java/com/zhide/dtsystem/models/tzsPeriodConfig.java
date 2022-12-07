package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TZSPeriodConfig")
public class tzsPeriodConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TypeName")
    private String typeName;
    @Column(name = "Months")
    private int months;
    @Column(name = "Days")
    private int days;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "JMonths")
    private int jMonths;
    @Column(name = "JDays")
    private int jDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }


    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public int getJMonths() {
        return jMonths;
    }

    public void setJMonths(int jMonths) {
        this.jMonths = jMonths;
    }


    public int getJDays() {
        return jDays;
    }

    public void setJDays(int jDays) {
        this.jDays = jDays;
    }

}
