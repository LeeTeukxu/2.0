package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbTZSSendResult")
public class tbTZSSendResult {
    @Id
    @Column(name = "TZSSendResultID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tzsSendResultId;
    @Column(name = "TONGZHISBH")
    private String tongzhisbh;
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "SendTime")
    private Date sendTime;
    @Column(name = "SendResult")
    private String sendResult;

    public Integer getTzsSendResultId() {
        return tzsSendResultId;
    }

    public void setTzsSendResultId(Integer tzsSendResultId) {
        this.tzsSendResultId = tzsSendResultId;
    }

    public String getTongzhisbh() {
        return tongzhisbh;
    }

    public void setTongzhisbh(String tongzhisbh) {
        this.tongzhisbh = tongzhisbh;
    }

    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
