package com.clx.workflow.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.system.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}