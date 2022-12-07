package com.zhide.dtsystem.multitenancy;

import com.zhide.dtsystem.common.GlobalContext;
import com.zhide.dtsystem.models.LoginUserInfo;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CompanyIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        LoginUserInfo Info=CompanyContext.get();
        if(Info==null) return GlobalContext.Default;
        else return Info.getCompanyId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
