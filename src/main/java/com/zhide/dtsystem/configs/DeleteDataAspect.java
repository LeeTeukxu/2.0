package com.zhide.dtsystem.configs;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: DeleteDataAspect
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月17日 7:21
 **/
/*@Aspect
@Component*/
public class DeleteDataAspect {
    Logger logger = LoggerFactory.getLogger(DeleteDataAspect.class);

    @Pointcut("execution(* com.zhide.dtsystem.services..*remove*(..)) || execution(* com.zhide.dtsystem.services." +
            ".*Remove*(..))")
    public void DefaultAspect() {

    }

    @Around("DefaultAspect()")
    public Object executeRemoveMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        LoginUserInfo Info = CompanyContext.get();
        Date Begin = new Date();
        Object OO = null;
        try {
            OO = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Date End = new Date();
            logger.info(Info.getUserName() + ",运行:" + joinPoint.getSignature().getName() + "耗时:" + Long.toString(End.getTime() - Begin.getTime()) + "毫秒。");
            throw throwable;
        }
        Date End = new Date();
        logger.info(Info.getUserName() + ",运行:" + joinPoint.getSignature().getName() + "耗时:" + Long.toString(End.getTime() - Begin.getTime()) + "毫秒。");
        return OO;
    }
}
