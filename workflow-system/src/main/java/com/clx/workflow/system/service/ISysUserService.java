package com.clx.workflow.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.system.domain.SysUser;

/**
 * 用户 Service 接口
 *
 * @author clx
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     */
    SysUser selectUserByUserName(String userName);

}