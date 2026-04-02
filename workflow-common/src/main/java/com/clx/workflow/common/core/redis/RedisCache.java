package com.clx.workflow.common.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author clx
 */
@Component
public class RedisCache {

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存基本对象
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本对象（带过期时间）
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取缓存对象
     */
    public <T> T getCacheObject(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存对象
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     */
    public boolean deleteObject(final Collection<String> collection) {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 设置过期时间
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 判断 key 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 缓存 List
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList.toArray());
        return count == null ? 0 : count;
    }

    /**
     * 获取缓存的 List
     */
    public <T> List<T> getCacheList(final String key) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存 Set
     */
    public <T> void setCacheSet(final String key, final Set<T> dataSet) {
        redisTemplate.opsForSet().add(key, dataSet.toArray());
    }

    /**
     * 获取缓存的 Set
     */
    public <T> Set<T> getCacheSet(final String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存 Map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取缓存的 Map
     */
    public <T> Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取匹配的 key
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}