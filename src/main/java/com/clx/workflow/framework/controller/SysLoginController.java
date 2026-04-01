package com.clx.workflow.framework.controller;

import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.core.domain.model.LoginBody;
import com.clx.workflow.framework.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 *
 * @author clx
 */
@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return AjaxResult.success("登录成功").put("token", token);
    }

    /**
     * 登出接口
     */
    @PostMapping("/logout")
    public AjaxResult logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        loginService.logout(token);
        return AjaxResult.success("登出成功");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        // TODO: 从 SecurityContext 获取当前登录用户信息
        return AjaxResult.success();
    }
}