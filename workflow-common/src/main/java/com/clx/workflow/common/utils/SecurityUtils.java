package com.clx.workflow.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 *
 * @author clx
 */
public class SecurityUtils {

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // 如果是LoginUser类型，尝试获取userId
            if (userDetails.getClass().getName().contains("LoginUser")) {
                try {
                    // 通过反射获取userId
                    java.lang.reflect.Method method = userDetails.getClass().getMethod("getUserId");
                    Object userId = method.invoke(userDetails);
                    if (userId instanceof Long) {
                        return (Long) userId;
                    }
                } catch (Exception e) {
                    // 忽略异常
                }
            }
            // 默认返回管理员ID
            return 1L;
        }
        return 1L;
    }

    /**
     * 获取当前用户名
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * 判断是否为管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 获取当前用户部门ID
     */
    public static Long getDeptId() {
        // 暂时返回默认部门ID，需要根据实际情况从用户详情中获取
        return 100L;
    }
}