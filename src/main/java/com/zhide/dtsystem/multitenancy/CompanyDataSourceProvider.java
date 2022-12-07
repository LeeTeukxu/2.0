package com.zhide.dtsystem.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import com.zhide.dtsystem.models.connectionInfo;
import com.zhide.dtsystem.services.connectionParsor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CompanyDataSourceProvider {
    private static Map<String,DataSource> dataSourceMap=new HashMap<>();
    private static Logger logger= LoggerFactory.getLogger(CompanyDataSourceProvider.class);
    private static DataSource getByCompanyID(String companyId){
        logger.info("get DataSource by Id:"+companyId);
        if(dataSourceMap.containsKey(companyId)){
            return dataSourceMap.get(companyId);
        } else {
            DataSource defaultSource=dataSourceMap.get("Default");
            connectionParsor connectionParsor=new connectionParsor();
            connectionInfo targetInfo=null;
            try {
                connectionParsor.setConnection(defaultSource.getConnection());
                targetInfo=connectionParsor.getByID(companyId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(targetInfo==null){
                logger.info("targetInfo is null");
                return null;
            }
            String url="jdbc:sqlserver://"+targetInfo.getServer()+
                    ":"+targetInfo.getPort()+";DatabaseName="+targetInfo.getDataBase()+";Pooling=true;Min Pool " +
                    "Size=5;Max Pool Size=100;";
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(url);
            dataSourceBuilder.username(targetInfo.getUsername());
            dataSourceBuilder.password(targetInfo.getPassword());
            dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            HikariDataSource dd=(HikariDataSource) dataSourceBuilder.build();
            dd.setMaximumPoolSize(20);
            dd.setIdleTimeout(40000);
            dd.setConnectionTimeout(60000);
            dd.setMaximumPoolSize(100);
            dd.setConnectionTestQuery("Select 1");
            try {
                dd.setLoginTimeout(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dd.setMaxLifetime(60000);
            dataSourceMap.put(companyId,dd);
            return dd;
        }
    }
    public static DataSource getDataSource(String companyId){
        DataSource dx=null;
        if(dataSourceMap.containsKey(companyId)){
            dx= dataSourceMap.get(companyId);
        } else {
            if(companyId!="Default") {
                dx = getByCompanyID(companyId);
            } else {
                connectionParsor connectionParsor=new connectionParsor();
                dx=connectionParsor.getDefaultConnection();
                dataSourceMap.put("Default",dx);
            }
        }
        /**try {
            HikariDataSource vv=(HikariDataSource) dx;
            HikariPoolMXBean pool=vv.getHikariPoolMXBean();
            if(pool!=null) {
                int unUsedNum=pool.getIdleConnections();
                int usedNum=pool.getActiveConnections();
                int totalNum=pool.getTotalConnections();
               logger.info("------"+Integer.toString(totalNum)+" Connection in Pool,"+ "Used Number:" +Integer
                        .toString(usedNum)+ ",Free Number:"+Integer.toString(unUsedNum)+"------");
            }
        }catch (Exception e){
            logger.info(""+e.getMessage());

        }**/
        return dx;
    }
}
