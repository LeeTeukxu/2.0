package com.zhide.dtsystem.configs;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 对存入Redis的对象，生成带公司ID的标识。
 */
@Component(value = "CompanyKeyGenerator")
public class CompanyKeyGenerator implements KeyGenerator {
    private final Logger logger = LoggerFactory.getLogger(CompanyKeyGenerator.class);
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        LoginUserInfo info = CompanyContext.get();
        if (Objects.isNull(info) == false) {
            String baseKey = info.getCompanyId();
            Parameter[] Ps = method.getParameters();
            if (Ps.length > 0 && Ps.length == objects.length) {
                for (int i = 0; i < Ps.length; i++) {
                    Parameter p = Ps[i];
                    Class<?> tt = p.getType();
                    if (tt == String.class || ClassUtils.isPrimitiveOrWrapper(p.getType()) == true) {
                        Object O = objects[i];
                        if (O != null) {
                            String pName = p.getName();
                            baseKey += ":" + pName + ":" + O.toString();
                        }
                    }
                }
            }
            return baseKey;
        } else return "";
    }
}
