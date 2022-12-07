package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.EmailContent;

public interface ISendEmailService {
    public void sendEmailByContent(EmailContent content) throws Exception;

    public void sendPantentEmailByContent(EmailContent content) throws Exception;
}
