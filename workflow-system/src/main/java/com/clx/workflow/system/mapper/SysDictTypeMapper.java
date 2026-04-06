package com.clx.workflow.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.system.domain.SysDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 校验字典类型是否唯一
     */
    SysDictType checkDictTypeUnique(@Param("dictType") String dictType);
}