package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.common.core.redis.RedisCache;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysDictData;
import com.clx.workflow.system.domain.SysDictType;
import com.clx.workflow.system.mapper.SysDictDataMapper;
import com.clx.workflow.system.mapper.SysDictTypeMapper;
import com.clx.workflow.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 字典类型 Service 实现类
 *
 * @author clx
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 字典缓存key前缀
     */
    private static final String DICT_CACHE_KEY = "sys_dict:";

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        if (dictType.getDictName() != null && !dictType.getDictName().isEmpty()) {
            wrapper.like(SysDictType::getDictName, dictType.getDictName());
        }
        if (dictType.getDictType() != null && !dictType.getDictType().isEmpty()) {
            wrapper.like(SysDictType::getDictType, dictType.getDictType());
        }
        if (dictType.getStatus() != null && !dictType.getStatus().isEmpty()) {
            wrapper.eq(SysDictType::getStatus, dictType.getStatus());
        }
        return dictTypeMapper.selectList(wrapper);
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        Long dictId = dictType.getDictId() == null ? -1L : dictType.getDictId();
        SysDictType info = this.lambdaQuery()
                .eq(SysDictType::getDictType, dictType.getDictType())
                .one();
        if (info != null && !info.getDictId().equals(dictId)) {
            return false;
        }
        return true;
    }

    @Override
    public void refreshCache() {
        // 清空缓存
        Collection<String> keys = redisCache.keys(DICT_CACHE_KEY + "*");
        if (keys != null && !keys.isEmpty()) {
            redisCache.deleteObject(new HashSet<>(keys));
        }
        // 重新加载缓存
        loadingDictCache();
    }

    @Override
    public void loadingDictCache() {
        List<SysDictData> dictDataList = dictDataMapper.selectList(new LambdaQueryWrapper<>());
        Map<String, List<SysDictData>> dictMap = new HashMap<>();
        for (SysDictData dictData : dictDataList) {
            String dictType = dictData.getDictType();
            if (!dictMap.containsKey(dictType)) {
                dictMap.put(dictType, new ArrayList<>());
            }
            dictMap.get(dictType).add(dictData);
        }
        for (Map.Entry<String, List<SysDictData>> entry : dictMap.entrySet()) {
            redisCache.setCacheObject(DICT_CACHE_KEY + entry.getKey(), entry.getValue(), 24, TimeUnit.HOURS);
        }
    }

    @Override
    public boolean save(SysDictType dictType) {
        if (!checkDictTypeUnique(dictType)) {
            throw new ServiceException("新增字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        return super.save(dictType);
    }

    @Override
    public boolean updateById(SysDictType dictType) {
        if (!checkDictTypeUnique(dictType)) {
            throw new ServiceException("修改字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        boolean result = super.updateById(dictType);
        if (result) {
            // 更新缓存
            refreshCache();
        }
        return result;
    }

    @Override
    public boolean removeByIds(Collection<?> dictIds) {
        for (Object dictId : dictIds) {
            SysDictType dictType = this.getById((Long) dictId);
            if (dictType != null) {
                // 检查是否有字典数据
                long count = dictDataMapper.selectCount(
                        new LambdaQueryWrapper<SysDictData>()
                                .eq(SysDictData::getDictType, dictType.getDictType())
                );
                if (count > 0) {
                    throw new ServiceException("字典类型'" + dictType.getDictName() + "'已分配字典数据，不能删除");
                }
            }
        }
        return super.removeByIds(dictIds);
    }
}