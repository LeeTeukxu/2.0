package com.zhide.dtsystem.configs;

import com.zhide.dtsystem.multitenancy.CompanyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CustomMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CompanyInterceptor companyInterceptor;
    @Autowired
    private IndexActionInterceptor indexActionInterceptor;

    @Autowired
    private SinglePermissionCheckInterceptor singlePermission;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(companyInterceptor);
        registry.addInterceptor(indexActionInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:d:/Upload/Images/");
        registry.addResourceHandler("/appImages/**").addResourceLocations("classpath:/static/images/");
    }
}
