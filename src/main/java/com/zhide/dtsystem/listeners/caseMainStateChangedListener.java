package com.zhide.dtsystem.listeners;

import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.events.caseStateChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName: caseMainStateChangedListener
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月04日 14:35
 **/
@Component
public class caseMainStateChangedListener {

    Logger logger= LoggerFactory.getLogger(caseMainStateChangedListener.class);
    @Autowired
    RabbitMessageUtils rabbitUtils;
    @Autowired
    AllMessageCollections allMessage;

    @EventListener
    public  void processEvent(caseStateChangedEvent evt){
        logger.info(evt.getMessage());
        try {
            rabbitUtils.send(allMessage.caseStateChanged(),evt);
        }
        catch(Exception ax){
            logger.info("发送:"+evt.getType()+","+evt.getName()+"，消息:"+evt.getMessage()+"失败，问题:"+ax.getMessage());
        }
    }
}
