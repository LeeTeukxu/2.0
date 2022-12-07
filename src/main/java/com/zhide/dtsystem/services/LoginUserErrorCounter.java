package com.zhide.dtsystem.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LoginUserErrorCounter {
    public LoginUserErrorCounter() {
        maxTimes = 3;
    }

    @Autowired
    StringRedisTemplate redisTemplate;
    int maxTimes;
    String account;

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isOver() {
        int nums = getTimes();
        return nums > maxTimes;
    }

    /**
     * 错误次数加一次。
     */
    public void addOne() {
        if (isLock() == false) {
            String key = "Login_Error_" + account;
            int nowNum = getTimes();
            if (nowNum <= maxTimes) {
                redisTemplate.opsForValue().increment(key);
                redisTemplate.expire(key, 1, TimeUnit.HOURS);
            }
        }
    }

    public boolean isLock() {
        String lockKey = "Login_Error_Lock_" + account;
        Object O = redisTemplate.opsForValue().get(lockKey);
        if (O == null) O = "0";
        Integer OX = Integer.parseInt(O.toString());
        return OX > 0;
    }

    /**
     * 错误的次数。
     *
     * @return
     */
    public int getTimes() {
        String key = "Login_Error_" + account;
        Object O = redisTemplate.opsForValue().get(key);
        if (O == null) {
            O = "0";
            redisTemplate.opsForValue().set(key,"0");
        }
        return Integer.parseInt(O.toString());
    }

    public void lockUser() {
        String lockKey = "Login_Error_Lock_" + account;
        Object O = redisTemplate.opsForValue().get(lockKey);
        if (O == null) O = "0";
        Integer OX = Integer.parseInt(O.toString());
        if (OX == 0) {
            String key = "Login_Error_" + account;
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(lockKey, "1");
            redisTemplate.expire(lockKey, 30, TimeUnit.MINUTES);
        }
    }

    public void clear() {
        String lockKey = "Login_Error_Lock_" + account;
        redisTemplate.delete(lockKey);
        String key = "Login_Error_" + account;
        redisTemplate.delete(key);
    }
}
