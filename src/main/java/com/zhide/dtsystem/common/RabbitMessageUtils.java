package com.zhide.dtsystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RabbitMessageUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月19日 10:37
 **/
@Component
public class RabbitMessageUtils {
    @Autowired
    RabbitTemplate rabbitTemplate;
    Logger logger= LoggerFactory.getLogger(RabbitMessageUtils.class);

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     * @return
     */
    public void send(String queueName,Object sendObj){
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend(queueName, sendObj);
        }catch(Exception ax){
            logger.info("send message error:"+ax.getMessage());
        }
    }
    public void publish(String exchangeName,Object sendObj){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(exchangeName,"",sendObj);
    }
}
