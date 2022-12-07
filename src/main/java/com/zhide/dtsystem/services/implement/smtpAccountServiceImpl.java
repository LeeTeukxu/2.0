package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.smtpAccount;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.smtpAccountRepository;
import com.zhide.dtsystem.services.define.IsmtpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class smtpAccountServiceImpl implements IsmtpAccountService {

    @Autowired
    smtpAccountRepository smtpAccountRepository;

    @Override
    public smtpAccount Save(smtpAccount sAccount, String UserName) throws Exception {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (sAccount.getId() == null) {
            if (sAccount.getSsl() == true) {
                sAccount.setSsl(true);
            } else sAccount.setSsl(false);
            sAccount.setUserName(UserName);
            sAccount.setUserId(Integer.parseInt(loginUserInfo.getUserId()));
            sAccount.setCanUse(true);
        } else {
            sAccount.setUserName(UserName);
            Optional<smtpAccount> fsmtpAccount = smtpAccountRepository.findById(sAccount.getId());
            if (fsmtpAccount.isPresent()) {
                EntityHelper.copyObject(sAccount, fsmtpAccount.get());
            }
        }
        smtpAccountRepository.save(sAccount);
        return sAccount;
    }
}
