package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.smtpAccount;

public interface IsmtpAccountService {
    smtpAccount Save(smtpAccount sAccount, String UserName) throws Exception;
}
