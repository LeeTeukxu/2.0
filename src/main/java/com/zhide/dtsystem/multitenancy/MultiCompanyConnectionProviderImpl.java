package com.zhide.dtsystem.multitenancy;

import com.zhide.dtsystem.models.LoginUserInfo;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MultiCompanyConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private final Logger logger= LoggerFactory.getLogger(MultiCompanyConnectionProviderImpl.class);
    @Override
    protected DataSource selectAnyDataSource() {
        logger.info("get Connection from DTSystem.....");
        return CompanyDataSourceProvider.getDataSource("Default");
    }

    @Override
    protected DataSource selectDataSource(String s) {
        LoginUserInfo Info=CompanyContext.get();
        if(Info!=null){
            s=Info.getCompanyId();
        }
        if(s.equals("DTSystem")==false) {
            //logger.info("get  Connection from Client_" + s + ".....");
            return CompanyDataSourceProvider.getDataSource(s);
        } else {
            //logger.info("get  Connection from DTSystem.....");
            return CompanyDataSourceProvider.getDataSource("Default");
        }
    }
}
