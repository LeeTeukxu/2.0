package com.zhide.dtsystem.services.define;

public interface ICompanyConfigService {
    boolean isExpired(String companyID) throws Exception;

    boolean canCreate() throws Exception;

    boolean canLogin(String companyID) throws Exception;
}
