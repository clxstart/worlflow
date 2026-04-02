package com.clx.workflow.framework.service;

import com.clx.workflow.framework.domain.LoginUser;
import com.clx.workflow.common.exception.ServiceException;
import com.clx.workflow.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public String login(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            throw new ServiceException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        SysUser user = loginUser.getUser();
        if ("1".equals(user.getStatus())) {
            throw new ServiceException("账号已被停用，请联系管理员");
        }

        return tokenService.createToken(loginUser);
    }

    public void logout(String token) {
        tokenService.delLoginUser(token);
    }
}