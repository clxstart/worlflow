package com.clx.workflow.framework.service;

import com.clx.workflow.common.core.domain.model.LoginUser;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysUser;
import com.clx.workflow.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * 登录服务
 *
 * @author clx
 */
@Service
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     */
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            throw new ServiceException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 检查用户状态
        SysUser user = loginUser.getUser();
        if ("1".equals(user.getStatus())) {
            throw new ServiceException("账号已被停用，请联系管理员");
        }

        // 生成 token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登出
     */
    public void logout(String token) {
        tokenService.delLoginUser(token);
    }
}