package com.clx.workflow.framework.controller;

import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.framework.domain.LoginBody;
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

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return AjaxResult.success("登录成功").put("token", token);
    }

    @PostMapping("/logout")
    public AjaxResult logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        loginService.logout(token);
        return AjaxResult.success("登出成功");
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        return AjaxResult.success();
    }
}