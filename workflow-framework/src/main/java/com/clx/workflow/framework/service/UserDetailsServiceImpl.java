package com.clx.workflow.framework.service;

import com.clx.workflow.framework.domain.LoginUser;
import com.clx.workflow.system.domain.SysUser;
import com.clx.workflow.system.service.ISysRoleService;
import com.clx.workflow.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户验证处理
 *
 * @author clx
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 加载用户角色权限
        Set<String> permissions = roleService.selectRolePermissionByUserId(user.getUserId());
        return new LoginUser(user, permissions);
    }
}