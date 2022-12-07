package com.zhide.dtsystem.multitenancy;

import com.zaxxer.hikari.HikariDataSource;
import com.zhide.dtsystem.models.connectionInfo;
import com.zhide.dtsystem.services.connectionParsor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zhide.dtsystem.models");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mybatis/mapper/*.xml"));
        return sqlSessionFactoryBean;
    }
    @Bean
    public  DynamicDataSource dynamicDataSource(){
        connectionParsor connectionParsor=new connectionParsor();
        Map<Object,Object> dataSources=new HashMap<>();
        DynamicDataSource d=new DynamicDataSource();
        DataSource defaultDataSource=connectionParsor.getDefaultConnection();
        dataSources.put("Default", defaultDataSource);
        d.setDefaultTargetDataSource(defaultDataSource);

        try {
            connectionParsor.setConnection(defaultDataSource.getConnection());
            List<String> allKeys = connectionParsor.getAllCompanyCodes();
            for (int i = 0; i < allKeys.size(); i++) {
                String Key = allKeys.get(i);
                connectionInfo Info = connectionParsor.getByID(Key);
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.url("jdbc:sqlserver://" + Info.getServer() +
                        ":"+Info.getPort()+";DatabaseName=" +
                        Info.getDataBase()+";Pooling=true;Min Pool Size=5;Max Pool Size=100;");

                dataSourceBuilder.username(Info.getUsername());
                dataSourceBuilder.password(Info.getPassword());
                dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                HikariDataSource tinySource = (HikariDataSource) dataSourceBuilder.build();
                tinySource.setMaximumPoolSize(100);
                dataSources.put(Key, tinySource);
            }
        }catch (Exception ax)
        {
            System.out.println(ax.getMessage());
        }
        d.setDataSource(dataSources);
        return d;
    }
}
