package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbTZSSendType")
public class tbTZSSendType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "TZSPeriodID")
    private String tzsPeriodId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTzsPeriodId() {
        return tzsPeriodId;
    }

    public void setTzsPeriodId(String tzsPeriodId) {
        this.tzsPeriodId = tzsPeriodId;
    }
}
