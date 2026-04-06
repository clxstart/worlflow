package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysDictData;

import java.util.List;

/**
 * 字典数据 Service 接口
 *
 * @author clx
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 查询字典数据列表
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典类型查询字典数据
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 查询所有字典数据
     */
    List<SysDictData> selectDictDataAll();
}