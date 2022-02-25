package com.caf.yeb.common.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2021/10/25 10:47
 */
@Slf4j
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public Boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis set error",e);
            return false;
        }
    }

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public Boolean set(String key, Object value, long timeout, TimeUnit unit){
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            return true;
        } catch (Exception e) {
            log.error("redis set is error",e);
            return false;
        }
    }

    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }

    public Boolean addSet(String key, Set set) {
        try {
            set.forEach(s -> {
                redisTemplate.opsForSet().add(key, s);
            });
            return true;
        } catch (Exception e) {
            log.error("redis set is error");
            return false;
        }
    }

    public Set<Object> getSet(String key) {
        return key == null ? null : redisTemplate.opsForSet().members(key);
    }

    public boolean removeSet(String key,String value) {
        try {
            redisTemplate.opsForSet().remove(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis remove false");
            return false;
        }
    }
}
