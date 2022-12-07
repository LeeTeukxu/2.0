package com.zhide.dtsystem.common;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @ClassName: TimeWatch
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年10月28日 15:28
 **/

@Component
@Scope(SCOPE_PROTOTYPE)
public class TimeWatch {
    Logger logger= LoggerFactory.getLogger(TimeWatch.class);
    Long  newTime=null;
    public void start(){
        newTime=System.currentTimeMillis();
    }
    public Long getUseTime(){
        Long Y=System.currentTimeMillis();
        return Y-newTime;
    }
    public void printUseTime(String moduleName){
        logger.info("{}执行用时:{}毫秒。",moduleName,getUseTime());
    }
}
