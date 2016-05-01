package org.hope6537.rest.utils;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by dintama on 16/4/3.
 */
public class RedisUtil {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    public String get(String key) {
        String result = (String) redisTemplate.opsForValue().get(key);
        return result;
    }

}
