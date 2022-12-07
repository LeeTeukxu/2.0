package com.zhide.dtsystem.configs;

import com.zhide.dtsystem.models.CacheableTtl;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

/**
 * @ClassName: TtlCacheResolver
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月16日 16:18
 **/
public class TtlCacheResolver extends AbstractCacheResolver {
    private static final int MAX_CACHE_SIZE = 1024;
    Map<String,RedisCache> cacheMap=new HashMap<>();
    public TtlCacheResolver(RedisCacheManager redisCacheManager){
        super(redisCacheManager);
    }
    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Collection<String> cacheNames = this.getCacheNames(context);
        if (cacheNames == null) {
            return Collections.emptyList();
        } else {
            Collection<Cache> result = new ArrayList(cacheNames.size());
            Iterator iterator = cacheNames.iterator();
            while (iterator.hasNext()) {
                String cacheName = (String) iterator.next();
                Cache cache = getCacheFromMem(context, cacheName);
                if (cache == null) {
                    cache = this.getCacheManager().getCache(cacheName);
                }
                if (cache == null) {
                    throw new IllegalArgumentException(
                            "Cannot find cache named '" + cacheName + "' for " + context.getOperation());
                }
                result.add(cache);
            }
            return result;
        }
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        return context.getOperation().getCacheNames();
    }

    private Cache getCacheFromMem(CacheOperationInvocationContext<?> context, String cacheName) {
        Method method = context.getMethod();
        CacheableTtl cacheableTtl = method.getAnnotation(CacheableTtl.class);
        if (cacheableTtl != null && cacheableTtl.ttl() > 0) {
            long ttl = cacheableTtl.ttl();
            String cacheKey = cacheName + "_" + ttl;
            RedisCache cache = cacheMap.get(cacheKey);
            this.clearIfOverload();
            if (cache == null) {
                cache = this.createRedisCache(cacheName, ttl);
                if (cache != null) {
                    cacheMap.putIfAbsent(cacheKey, cache);
                }
            }
            return cache;
        }
        return null;
    }

    private RedisCache createRedisCache(String cacheName, Long ttl) {
        CacheManager cacheManager = super.getCacheManager();
        if (cacheManager instanceof TtlRedisCacheManager) {
            TtlRedisCacheManager manager = (TtlRedisCacheManager) cacheManager;
            RedisCacheConfiguration configuration = manager.getCacheConfigurationCopy();
            return manager.createCache(cacheName, configuration.entryTtl(Duration.ofSeconds(ttl)));
        }
        return null;
    }

    private void clearIfOverload() {
        if (cacheMap.size() > MAX_CACHE_SIZE) {
            cacheMap.clear();
        }
    }
}
