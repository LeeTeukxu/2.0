package com.zhide.dtsystem.configs;

import com.rabbitmq.client.AMQP;
import com.zhide.dtsystem.common.AllMessageCollections;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitMQConfig
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月22日 9:15
 **/
@Configuration
public class RabbitMQConfig {
    @Autowired
    AllMessageCollections allMessages;

    @Bean
    public Queue clientChangeQueue(){
        return new Queue(allMessages.clientChagne(),true);
    }
    @Bean
    public FanoutExchange publicExchange(){
        return new FanoutExchange("publicEvent",true,false);
    }
    @Bean
    public Queue casesMainChanged(){ return new Queue(allMessages.casesMainChanged(),true);}
    @Bean
    public Queue casesSubChanged(){ return new Queue(allMessages.casesSubChanged(),true);}
    @Bean
    public Queue watchMethodChanged(){return new Queue(allMessages.watchMethodChanged(),true);}
    @Bean
    public Queue exceptionOccured(){return new Queue(allMessages.exceptionOccured(),true);}
    @Bean
    public Queue controllerProcessed(){return new Queue(allMessages.controllerProcessed(),true);}
}
