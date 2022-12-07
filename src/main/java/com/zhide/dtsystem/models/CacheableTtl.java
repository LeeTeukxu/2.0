package com.zhide.dtsystem.models;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Cacheable()
public @interface CacheableTtl {
    @AliasFor("cacheNames")
    String[] value() default {};

    @AliasFor("value")
    String[] cacheNames() default {};

    String key() default "";

    String cacheResolver() default "ttlCacheResolver";

    String keyGenerator() default "CompanyKeyGenerator";

    String condition() default "";

    String unless() default "";

    boolean sync() default false;

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     缓存时长：秒
     * @return
     */
    long ttl();
}
