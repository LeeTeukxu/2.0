package com.zhide.dtsystem.configs;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: TtlRedisCacheManager
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月16日 16:14
 **/
public class TtlRedisCacheManager extends RedisCacheManager {
    private RedisCacheConfiguration defaultCacheConfiguration = null;
    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        Objects.requireNonNull(defaultCacheConfiguration);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration,
            String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        Objects.requireNonNull(defaultCacheConfiguration);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration,
            boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        Objects.requireNonNull(defaultCacheConfiguration);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration,
            Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        Objects.requireNonNull(defaultCacheConfiguration);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration,
            Map<String, RedisCacheConfiguration> initialCacheConfigurations,
            boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        Objects.requireNonNull(defaultCacheConfiguration);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }
    public RedisCache createCache(String cacheName, RedisCacheConfiguration config) {
        return super.createRedisCache(cacheName, config);
    }

    public RedisCacheConfiguration getCacheConfigurationCopy() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(defaultCacheConfiguration.getTtl())
                .serializeKeysWith(defaultCacheConfiguration.getKeySerializationPair())
                .serializeValuesWith(defaultCacheConfiguration.getValueSerializationPair())
                .withConversionService(defaultCacheConfiguration.getConversionService())
                .computePrefixWith(cacheName -> defaultCacheConfiguration.getKeyPrefixFor(cacheName));
    }
}
