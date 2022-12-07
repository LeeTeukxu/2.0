package com.zhide.dtsystem.services;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * create by: mmzs
 * description: TODO
 * create time:
 * 指定过期时间的缓存。默认取当前公司标识做前缀。
 *
 * @return
 */
@Component
@Scope("prototype")
public class CompanyTimeOutCache {
    private Long timeOut;
    String CompanyID = "";
    @Autowired
    StringRedisTemplate redisRep;
    Logger logger = LoggerFactory.getLogger(CompanyTimeOutCache.class);
    List<String> Keys = null;

    /**
     * create by: mmzs
     * description: 设置超时，以毫秒为单位
     * create time:
     *
     * @return
     */
    public void setTimeOut(Long TimeOut) {
        this.timeOut = TimeOut;
        CompanyID = CompanyContext.get().getCompanyId();
        //logger.info(new Integer(this.hashCode()).toString());
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * CompanyID_Key_时间
     *
     * @return
     */
    public boolean existsKey(String key) {
        getKey(key);
        if (Keys.size() == 0) Keys = null;
        if (Keys == null) return false;
        else return Keys.size() > 0;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 指定Key是否已过期
     *
     * @return
     */
    public boolean hasExpire(String key) {
        if (existsKey(key)) {
            String Key = Keys.get(Keys.size() - 1);
            String[] Ks = Key.split("_");
            Long ST = Long.parseLong(Ks[2]);
            return (System.currentTimeMillis() - ST) > timeOut;
        } else return true;
    }

    public Object getValue(String key, Class t) {
        if (existsKey(key)) {
            String getKey = Keys.get(Keys.size() - 1);
            String Value = redisRep.opsForValue().get(getKey);
            if (StringUtils.isEmpty(Value) == true) return null;
            else return JSON.parseObject(Value, t);
        } else return null;
    }

    public <T> List<T> getArray(String key, Class<T> t) {
        if (existsKey(key)) {
            String getKey = Keys.get(Keys.size() - 1);
            String Value = redisRep.opsForValue().get(getKey);
            if (StringUtils.isEmpty(Value) == true) return null;
            else return JSON.parseArray(Value, t);
        } else return null;
    }

    public List<String> getArrayToStrings(String Key) {
        if (existsKey(Key)) {
            String getKey = Keys.get(Keys.size() - 1);
            String Value = redisRep.opsForValue().get(getKey);
            if (StringUtils.isEmpty(Value)  == true) return null;
            else return JSON.parseArray(Value, String.class);
        }else return null;
    }

    public void setValue(String key, Object obj) {
        if (ObjectUtils.isEmpty(obj) || StringUtils.isEmpty(key)) return;
        String TKey = new Long(System.currentTimeMillis()).toString();
        delete(key);
        String nKey = CompanyID + "_" + key + "_" + TKey;
        redisRep.opsForValue().set(nKey, JSON.toJSONString(obj));
        if (this.timeOut > 1000) redisRep.expire(nKey, this.timeOut, TimeUnit.MILLISECONDS);
    }

    public void delete(String key) {
        if (existsKey(key)) {
            if (Keys == null) getKey(key);
            for (int i = 0; i < Keys.size(); i++) {
                String Key = Keys.get(i);
                redisRep.delete(Key);
            }
            Keys = null;
        }
    }

    private void getKey(String key) {
        String FindKey = CompanyID + "_" + key;
        long X1 = System.currentTimeMillis();
        Keys = redisRep.keys(FindKey + "*").stream().collect(Collectors.toList());
        long X2 = System.currentTimeMillis();
    }
}
