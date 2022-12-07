package com.zhide.dtsystem.configs;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.MethodWatch;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.viewModel.MethodWatchInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: MethodWatchAspect
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月08日 16:03
 **/
@Aspect
@Component
public class MethodWatchAspect {
    Logger logger= LoggerFactory.getLogger(MethodWatchAspect.class);
    @Autowired
    RabbitMessageUtils messageUtils;
    @Autowired
    AllMessageCollections allMessage;
    @Pointcut("@annotation(com.zhide.dtsystem.models.MethodWatch)")
    public void MethodWatchAspect(){

    }
    @After("MethodWatchAspect() && @annotation(watch)")
    public void AfterMethod(JoinPoint jp, MethodWatch watch){
        try {
            LoginUserInfo Info = CompanyContext.get();
            Object[] args = jp.getArgs();
            String MethodName = jp.getSignature().toLongString();

            MethodWatchInfo AInfo = new MethodWatchInfo();
            AInfo.setMethodName(MethodName);
            AInfo.setArgsValue(JSON.toJSONString(args));
            AInfo.setMethodFunction(watch.name());
            AInfo.setUserName(Info.getUserName());
            AInfo.setRoleName(Info.getRoleName());
            AInfo.setCurTime(new Date());
            AInfo.setCompanyName(Info.getCompanyName());
            AInfo.setCompanyID(Info.getCompanyId());
            logger.info(String.format("%s的%s在%s执行了%s的操作", Info.getCompanyName(),
                    Info.getUserName(),
                    DateTimeUtils.formatCurrentTime(),
                    watch.name()));
            messageUtils.send(allMessage.watchMethodChanged(), AInfo);
        }catch(Exception ax){
            logger.error("发布methodWatch消息发生错误:"+ax.getMessage());
        }
    }

}
