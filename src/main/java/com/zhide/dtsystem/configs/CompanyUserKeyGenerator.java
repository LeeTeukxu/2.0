package com.zhide.dtsystem.configs;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @ClassName: RoleKeyGenerator
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月12日 15:32
 **/
@Component(value="CompanyUserKeyGenerator")
public class CompanyUserKeyGenerator implements KeyGenerator {
    /**
     * Generate a key for the given method and its parameters.
     *
     * @param target the target instance
     * @param method the method being called
     * @param objects the method parameters (with any var-args expanded)
     * @return a generated key
     */
    @Override
    public Object generate(Object target, Method method, Object... objects) {
        LoginUserInfo info = CompanyContext.get();
        if (Objects.isNull(info) == false) {
            String baseKey = info.getCompanyId()+":"+info.getRoleId()+":"+info.getUserId();
            Parameter[] Ps = method.getParameters();
            if (Ps.length > 0 && Ps.length == objects.length) {
                for (int i = 0; i < Ps.length; i++) {
                    Parameter p = Ps[i];
                    Class<?> tt = p.getType();
                    if (tt == String.class || ClassUtils.isPrimitiveOrWrapper(p.getType()) == true) {
                        Object O = objects[i];
                        if (O != null) {
                            String pName = p.getName();
                            String pValue=O.toString();
                            if(StringUtils.isEmpty(pValue)==false) {
                                if (pValue.length() > 200) {
                                    baseKey += ":" + pName;
                                } else baseKey += ":" + pName + ":" + O.toString();
                            }else  baseKey += ":" + pName;
                        }
                    }
                }
            }
            return baseKey;
        } else return "";
    }
}
