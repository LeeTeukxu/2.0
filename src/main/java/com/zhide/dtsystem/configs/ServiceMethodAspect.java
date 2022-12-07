package com.zhide.dtsystem.configs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhide.dtsystem.common.AllMessageCollections;
import com.zhide.dtsystem.common.RabbitMessageUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.viewModel.ExceptionMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;

/**
 * @ClassName: ServiceMethodAspect
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月02日 16:52
 **/
@Component
@Aspect
public class ServiceMethodAspect {
    Logger logger = LoggerFactory.getLogger(ServiceMethodAspect.class);
    @Autowired
    RabbitMessageUtils rabbitUtils;
    @Autowired
    AllMessageCollections allMessage;
    @Pointcut("execution(* com.zhide.dtsystem.services.implement..*(..))")
    public void ServiceMethodAspect() {

    }

    @AfterThrowing(pointcut = "ServiceMethodAspect()", throwing = "e")
    public void AfterThrow(JoinPoint joinPoint, Throwable e) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            ExceptionMessage message = new ExceptionMessage();
            message.setMessage(e.getMessage());
            message.setMethodName(method.getName());
            message.setType(method.getDeclaringClass().getName());
            if (request != null) {
                message.setUrl(request.getRequestURI());
                String ps = getRequestParameters(request);
                message.setParameters(ps);
            }
            message.setCreateTime(new Date());
            message.setDetail(getExceptionDetail(e));
            LoginUserInfo info = CompanyContext.get();
            if (info != null) {
                message.setUserName(info.getUserName());
                message.setCompanyName(info.getCompanyName());
            }
            rabbitUtils.send(allMessage.exceptionOccured(),message);
        }
        catch(Exception ax){
            logger.info(method.getName()+"发生异常时推送异常信息时发生错误:"+ax.getMessage());
        }
    }

    private String getRequestParameters(HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        Enumeration<String> paramters = request.getParameterNames();
        while (paramters.hasMoreElements()) {
            String Key = paramters.nextElement();
            String Value = request.getParameter(Key);
            JSONObject object = new JSONObject();
            object.put(Key, Value);
            jsonArray.add(object);
        }
        return jsonArray.toJSONString();
    }

    private String getExceptionDetail(Throwable e) {
        JSONArray js = new JSONArray();
        StackTraceElement[] ts = e.getStackTrace();
        for (int i = 0; i < ts.length; i++) {
            StackTraceElement ele = ts[i];
            if (ele.getClassName().startsWith("com.zhide.dtsystem") && ele.getLineNumber() > 0) {
                JSONObject obj = new JSONObject();
                obj.put("fileName", ele.getFileName());
                obj.put("line", ele.getLineNumber());
                obj.put("methodName", ele.getMethodName());
                obj.put("className", ele.getClassName());
                js.add(obj);
            }
        }
        return js.toJSONString();
    }
}
