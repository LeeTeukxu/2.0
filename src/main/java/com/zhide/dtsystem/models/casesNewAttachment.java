package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CasesNewAttachment")
public class casesNewAttachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "AttID")
    private String attId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

}
