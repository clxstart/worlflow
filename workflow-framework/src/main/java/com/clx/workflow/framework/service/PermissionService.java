package com.clx.workflow.framework.service;

import com.clx.workflow.framework.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 权限验证服务
 *
 * @author clx
 */
@Service("ss")
public class PermissionService {

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (permission == null || permission.isEmpty()) {
            return true;
        }

        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return false;
        }

        // 超级管理员拥有所有权限
        if (loginUser.getUser() != null && loginUser.getUser().isAdmin()) {
            return true;
        }

        Set<String> permissions = loginUser.getPermissions();
        return permissions != null && permissions.contains(permission);
    }

    /**
     * 验证用户是否不具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return true;
        }

        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return false;
        }

        // 超级管理员拥有所有权限
        if (loginUser.getUser() != null && loginUser.getUser().isAdmin()) {
            return true;
        }

        Set<String> userPermissions = loginUser.getPermissions();
        if (userPermissions == null) {
            return false;
        }

        for (String permission : permissions.split(",")) {
            if (userPermissions.contains(permission.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前登录用户
     */
    private LoginUser getLoginUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof LoginUser) {
                return (LoginUser) authentication.getPrincipal();
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
}