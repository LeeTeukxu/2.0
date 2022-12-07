package com.zhide.dtsystem.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.connectionInfo;
import com.zhide.dtsystem.services.connectionParsor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource{
    Map<Object,Object> OX=null;
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }
    @Override
    protected Object determineCurrentLookupKey() {
        LoginUserInfo info= CompanyContext.get();
        String Key="";
        if(info!=null) Key= info.getCompanyId();else Key= "Default";
        if(OX.containsKey(Key)==false){
            connectionParsor connectionParsor=new connectionParsor();
            DataSource defaultDataSource=(DataSource) OX.get("Default");
            try {
                connectionParsor.setConnection(defaultDataSource.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionInfo Info = connectionParsor.getByID(Key);
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url("jdbc:sqlserver://"
                    + Info.getServer() + ":"
                    +Info.getPort()+";DatabaseName="
                    +Info.getDataBase()
                    +";Pooling=true;Min Pool Size=5;Max Pool Size=100");

            dataSourceBuilder.username(Info.getUsername());
            dataSourceBuilder.password(Info.getPassword());
            dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            HikariDataSource tinySource = (HikariDataSource) dataSourceBuilder.build();
            OX.put(Key,tinySource);
            super.setTargetDataSources(OX);
            super.afterPropertiesSet();
        }
        return Key;
    }
    public  void setDataSource(Map<Object,Object> dataSources){
        OX=dataSources;
        super.setTargetDataSources(dataSources);
    }
}
