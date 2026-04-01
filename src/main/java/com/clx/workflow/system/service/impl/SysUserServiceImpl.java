package com.clx.workflow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.system.domain.SysUser;
import com.clx.workflow.system.mapper.SysUserMapper;
import com.clx.workflow.system.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户 Service 实现类
 *
 * @author clx
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * 根据用户名查询用户
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.lambdaQuery()
                .eq(SysUser::getUserName, userName)
                .one();
    }
}