package com.zhide.dtsystem.models;

import java.io.Serializable;

public class ChangeTechManResult implements Serializable {
    private String SHENQINGH;
    private String OLDXS;
    private String NOWXS;

    public String getSHENQINGH() {
        return SHENQINGH;
    }

    public void setSHENQINGH(String SHENQINGH) {
        this.SHENQINGH = SHENQINGH;
    }

    public String getOLDXS() {
        return OLDXS;
    }

    public void setOLDXS(String OLDXS) {
        this.OLDXS = OLDXS;
    }

    public String getNOWXS() {
        return NOWXS;
    }

    public void setNOWXS(String NOWXS) {
        this.NOWXS = NOWXS;
    }
}
