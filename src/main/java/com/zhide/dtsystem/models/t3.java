package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_3")
public class t3 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "shenqingh")
    private String shenqingh;
    @Column(name = "yingjiaofydm")
    private String yingjiaofydm;
    @Column(name = "yingjiaof_shijiyjje")
    private double yingjiaofShijiyjje;
    @Column(name = "yingjiaof_jiaofeijzr")
    private String yingjiaofJiaofeijzr;
    @Column(name = "rengong_jdje")
    private String rengongJdje;
    @Column(name = "pstate")
    private int pstate;
    @Column(name = "fstate")
    private boolean fstate;
    @Column(name = "zfsj")
    private String zfsj;
    @Column(name = "zfje")
    private String zfje;
    @Column(name = "jfqd_id")
    private String jfqdId;
    @Column(name = "f_1")
    private boolean f1;
    @Column(name = "shoujuh")
    private String shoujuh;
    @Column(name = "jiaofeije")
    private String jiaofeije;
    @Column(name = "jiaofeisj")
    private Date jiaofeisj;
    @Column(name = "jiaofeirxm")
    private String jiaofeirxm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public String getYingjiaofydm() {
        return yingjiaofydm;
    }

    public void setYingjiaofydm(String yingjiaofydm) {
        this.yingjiaofydm = yingjiaofydm;
    }


    public double getYingjiaofShijiyjje() {
        return yingjiaofShijiyjje;
    }

    public void setYingjiaofShijiyjje(double yingjiaofShijiyjje) {
        this.yingjiaofShijiyjje = yingjiaofShijiyjje;
    }


    public String getYingjiaofJiaofeijzr() {
        return yingjiaofJiaofeijzr;
    }

    public void setYingjiaofJiaofeijzr(String yingjiaofJiaofeijzr) {
        this.yingjiaofJiaofeijzr = yingjiaofJiaofeijzr;
    }


    public String getRengongJdje() {
        return rengongJdje;
    }

    public void setRengongJdje(String rengongJdje) {
        this.rengongJdje = rengongJdje;
    }


    public int getPstate() {
        return pstate;
    }

    public void setPstate(int pstate) {
        this.pstate = pstate;
    }


    public boolean getFstate() {
        return fstate;
    }

    public void setFstate(boolean fstate) {
        this.fstate = fstate;
    }


    public String getZfsj() {
        return zfsj;
    }

    public void setZfsj(String zfsj) {
        this.zfsj = zfsj;
    }


    public String getZfje() {
        return zfje;
    }

    public void setZfje(String zfje) {
        this.zfje = zfje;
    }


    public String getJfqdId() {
        return jfqdId;
    }

    public void setJfqdId(String jfqdId) {
        this.jfqdId = jfqdId;
    }


    public boolean getF1() {
        return f1;
    }

    public void setF1(boolean f1) {
        this.f1 = f1;
    }


    public String getShoujuh() {
        return shoujuh;
    }

    public void setShoujuh(String shoujuh) {
        this.shoujuh = shoujuh;
    }


    public String getJiaofeije() {
        return jiaofeije;
    }

    public void setJiaofeije(String jiaofeije) {
        this.jiaofeije = jiaofeije;
    }


    public Date getJiaofeisj() {
        return jiaofeisj;
    }

    public void setJiaofeisj(Date jiaofeisj) {
        this.jiaofeisj = jiaofeisj;
    }


    public String getJiaofeirxm() {
        return jiaofeirxm;
    }

    public void setJiaofeirxm(String jiaofeirxm) {
        this.jiaofeirxm = jiaofeirxm;
    }

}
