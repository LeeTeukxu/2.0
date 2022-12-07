package com.zhide.dtsystem.common;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CompanyTokenUtils {
    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate rd) {
        CompanyTokenUtils.redisTemplate = rd;
    }

    public static String getCompanyIdByToken(String token) {
        String Res = "";
        List<String> Keys = redisTemplate.keys("webToken" + "*").stream().collect(Collectors.toList());
        for (int i = 0; i < Keys.size(); i++) {
            String Key = Keys.get(i);
            Object value = redisTemplate.opsForValue().get(Key);
            if (Objects.isNull(value) == false) {
                if (value.toString().equals(token)) {
                    String[] targetKeys = Key.split("_");
                    Res = targetKeys[2];
                }
            }
        }
        return Res;
    }

    public static void deleteByToken(String webToke) {
        String tokenName = getTokenNameByToke(webToke);
        if (Strings.isNullOrEmpty(tokenName) == false) {
            redisTemplate.delete(tokenName);
        }
    }

    public static String getTokenNameByToke(String webToke) {
        String Res = "";
        List<String> Keys = redisTemplate.keys("webToken" + "*").stream().collect(Collectors.toList());
        for (int i = 0; i < Keys.size(); i++) {
            String Key = Keys.get(i);
            Object value = redisTemplate.opsForValue().get(Key);
            if (Objects.isNull(value) == false) {
                if (value.toString().equals(webToke)) {
                    Res = Key;
                }
            }
        }
        return Res;
    }

    public static String getOrCreateToken(String companyId, String account) {
        String webKey = "webToken_" + account + '_' + companyId;
        Object tokenObj = redisTemplate.opsForValue().get(webKey);
        if (tokenObj == null) {
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().append(webKey, token);
            redisTemplate.expire(webKey, 1000, TimeUnit.DAYS);
            return token;
        } else {
            return tokenObj.toString();
        }
    }

    public static boolean existToken(String token) {
        List<String> Keys = redisTemplate.keys("webToken" + "*").stream().collect(Collectors.toList());
        for (int i = 0; i < Keys.size(); i++) {
            String Key = Keys.get(i);
            Object value = redisTemplate.opsForValue().get(Key);
            if (Objects.isNull(value) == false) {
                if (value.toString().equals(token)) {
                    redisTemplate.opsForValue().set(Key, token);
                    redisTemplate.expire(Key, 1, TimeUnit.DAYS);
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer refreshToken(String token) {
        List<String> Keys = redisTemplate.keys("webToken" + "*").stream().collect(Collectors.toList());
        for (int i = 0; i < Keys.size(); i++) {
            String Key = Keys.get(i);
            Object value = redisTemplate.opsForValue().get(Key);
            if (Objects.isNull(value) == false) {
                if (value.toString().equals(token)) {
                    redisTemplate.opsForValue().set(Key, token);
                    redisTemplate.expire(Key, 1, TimeUnit.DAYS);
                    return IntegerUtils.parseInt(redisTemplate.getExpire(Key));
                }
            }
        }
        return 0;
    }

    public static boolean existToken(String companyId, String account) {
        String webKey = "webToken_" + account + '_' + companyId;
        Object tokenObj = redisTemplate.opsForValue().get(webKey);
        return !(tokenObj == null);
    }
}
