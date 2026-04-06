package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysDictType;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author clx
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 查询字典类型列表
     */
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 校验字典类型是否唯一
     */
    boolean checkDictTypeUnique(SysDictType dictType);

    /**
     * 刷新字典缓存
     */
    void refreshCache();

    /**
     * 重置字典缓存
     */
    void loadingDictCache();
}