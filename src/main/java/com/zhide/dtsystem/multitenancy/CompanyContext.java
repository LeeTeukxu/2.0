package com.zhide.dtsystem.multitenancy;

import com.zhide.dtsystem.models.LoginUserInfo;
import org.springframework.stereotype.Component;

@Component
public class CompanyContext {
    private static ThreadLocal<LoginUserInfo> currentTenant = new ThreadLocal<>();
    public static  LoginUserInfo get(){
        return currentTenant.get();
    }
    public static  void set(LoginUserInfo info){
        currentTenant.set(info);
    }
    public static void clear(){
        currentTenant.set(null);
    }
}
