package com.zhide.dtsystem.configs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.viewModel.ProcessInfo;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @ClassName DefaultAspect
 * @Author xiao
 * @CreateTime 2020-07-06 22:51
 **/
@Aspect
@Component
public class DefaultAspect {
    Logger logger = LoggerFactory.getLogger(DefaultAspect.class);
    ThreadLocal<Integer> XV = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return super.initialValue();
        }
    };

    @Autowired
    RabbitMessageUtils rabbitUtils;
    @Autowired
    AllMessageCollections allMessage;
    @Autowired
    StringRedisTemplate redisRep;

    List<String> urls= Arrays.asList("/admin/getUsedTime");
    @Pointcut("execution(* com.zhide.dtsystem.controllers..*(..))")
    public void DefaultAspect() {}

    @Around("DefaultAspect()")
    public Object BeforeGetData(ProceedingJoinPoint joinPoint) throws Throwable {
        Long  Begin = System.currentTimeMillis();
        Object OO = null;
        try {
            OO = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            Long End = System.currentTimeMillis();
            Long Free=End-Begin;
            if(Free>50){
                try {
                    LoginUserInfo U = CompanyContext.get();
                    if(U!=null && StringUtils.isEmpty(U.getCompanyName())==false) {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        HttpServletRequest request = attributes.getRequest();
                        String url = request.getRequestURI();
                        if(urls.contains(url)==false) {
                            String params = getRequestParameters(request);
                            ProcessInfo Info = new ProcessInfo();
                            Info.setArgs(params);
                            Info.setUrl(url);
                            Info.setCurTime(new Date());
                            Info.setUsedTime(Free);
                            Info.setCompanyName(U.getCompanyName());
                            Info.setCompanyId(U.getCompanyId());
                            Info.setUserName(U.getUserName());
                            rabbitUtils.send(allMessage.controllerProcessed(), Info);
                        }
                    }
                }catch(Exception ax){
                    logger.info("error when send rabbit to controllerProcess:"+ax.getMessage());
                }
            }
        }
        return OO;
    }
    private String getRequestParameters(HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        Enumeration<String> paramters = request.getParameterNames();
        while (paramters.hasMoreElements()) {
            String Key = paramters.nextElement();
            String Value = request.getParameter(Key);
            JSONObject object = new JSONObject();
            if(Value.length()<2000) {
                object.put(Key, Value);
            } else {
                object.put(Key,Value.substring(0,2000));
            }
            jsonArray.add(object);
        }
        return jsonArray.toJSONString();
    }
}

