package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.core.redis.RedisCache;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysConfig;
import com.clx.workflow.system.mapper.SysConfigMapper;
import com.clx.workflow.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 参数配置 Service 实现类
 *
 * @author clx
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 参数缓存key前缀
     */
    private static final String CONFIG_CACHE_KEY = "sys_config:";

    @Override
    public String selectConfigByKey(String configKey) {
        // 先从缓存获取
        String cacheValue = redisCache.getCacheObject(CONFIG_CACHE_KEY + configKey);
        if (cacheValue != null) {
            return cacheValue;
        }
        // 从数据库获取
        SysConfig config = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, configKey)
                .one();
        if (config != null) {
            redisCache.setCacheObject(CONFIG_CACHE_KEY + configKey, config.getConfigValue(), 24, TimeUnit.HOURS);
            return config.getConfigValue();
        }
        return null;
    }

    @Override
    public boolean checkConfigKeyUnique(SysConfig config) {
        Long configId = config.getConfigId() == null ? -1L : config.getConfigId();
        SysConfig info = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, config.getConfigKey())
                .one();
        if (info != null && !info.getConfigId().equals(configId)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(SysConfig config) {
        if (!checkConfigKeyUnique(config)) {
            throw new ServiceException("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return super.save(config);
    }

    @Override
    public boolean updateById(SysConfig config) {
        if (!checkConfigKeyUnique(config)) {
            throw new ServiceException("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        boolean result = super.updateById(config);
        if (result) {
            // 更新缓存
            redisCache.setCacheObject(CONFIG_CACHE_KEY + config.getConfigKey(), config.getConfigValue(), 24, TimeUnit.HOURS);
        }
        return result;
    }
}