package com.zhide.dtsystem.autoTask;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {
    @Value("${scheduling.enabled}")
    private boolean taskEnabled;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //线程池大小
        scheduler.setPoolSize(40);
        //线程名字前缀
        scheduler.setThreadNamePrefix("DTSystemTask");
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (!taskEnabled) {
            //清空扫描到的定时任务即可
            taskRegistrar.setTriggerTasks(Maps.newHashMap());
            taskRegistrar.setCronTasks(Maps.newHashMap());
            taskRegistrar.setFixedRateTasks(Maps.newHashMap());
            taskRegistrar.setFixedDelayTasks(Maps.newHashMap());
        }
        taskRegistrar.setScheduler(taskScheduler());
    }
}
