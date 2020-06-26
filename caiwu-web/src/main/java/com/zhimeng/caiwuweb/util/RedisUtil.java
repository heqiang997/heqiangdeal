package com.zhimeng.caiwuweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author:paul
 * @Description:
 */
@Component
public class RedisUtil {

    @Autowired
    private  RedisTemplate <String, String> redisTemplate;

    /**
     * @Description: 批量删除缓存
     */
    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * @Description: 批量删除缓存(通配符)
     */
    public void removePattern(String pattern ) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * @Description: 删除缓存
     */
    public void remove(String key ) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * @Description: 判断缓存中是否有对应的value
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @Description: 读取缓存
     */
    public String get(String key) {
        if (StringUtils.isEmpty(key)){
            return "";
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @Description: 写入缓存
     */
    public Boolean set(String key , String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 写入缓存(可以配置过期时间)
     */
    public Boolean set(String key , String value, Long expireTime )  {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
