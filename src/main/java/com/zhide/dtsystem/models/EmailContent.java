package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.List;

public class EmailContent implements Serializable {
    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextAndValue getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(TextAndValue sendAddress) {
        this.sendAddress = sendAddress;
    }

    public List<TextAndValue> getReceAddress() {
        return receAddress;
    }

    public void setReceAddress(List<TextAndValue> receAddress) {
        this.receAddress = receAddress;
    }

    public List<TextAndValue> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TextAndValue> attachments) {
        this.attachments = attachments;
    }

    private TextAndValue sendAddress;
    private List<TextAndValue> receAddress;
    private List<TextAndValue> attachments;
}
