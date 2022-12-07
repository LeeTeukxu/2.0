package com.zhide.dtsystem.multitenancy;

import com.zaxxer.hikari.HikariConfig;

public class HikariPoolConfig {
    public HikariConfig getHikariConfig(){
        HikariConfig config=new HikariConfig();
        config.setUsername("sa");
        config.setPassword("xxm_123456");
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(5);
        config.setLeakDetectionThreshold(5000);
        return config;
    }
}
