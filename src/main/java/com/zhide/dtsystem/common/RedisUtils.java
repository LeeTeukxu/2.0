package com.zhide.dtsystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @ClassName: RedisUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月14日 23:10
 **/
@Component
public class RedisUtils {
    @Autowired
    StringRedisTemplate redRep;
    Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    public void clearAll(String KeyPattern) {
        Set<String> Keys = redRep.keys("*" + KeyPattern + "*");
        Keys.forEach(f -> {
            Boolean X = redRep.delete(f);
            logger.info("删除Redis中Key为:" + f + "的记录,结果为:" + X.toString());
        });
    }
}
