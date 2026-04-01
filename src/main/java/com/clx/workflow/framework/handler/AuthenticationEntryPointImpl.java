package com.clx.workflow.framework.handler;

import com.alibaba.fastjson2.JSON;
import com.clx.workflow.common.constant.HttpStatus;
import com.clx.workflow.common.core.domain.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 *
 * @author clx
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        AjaxResult result = AjaxResult.error(HttpStatus.UNAUTHORIZED, "认证失败，无法访问系统资源");
        response.getWriter().print(JSON.toJSONString(result));
    }
}