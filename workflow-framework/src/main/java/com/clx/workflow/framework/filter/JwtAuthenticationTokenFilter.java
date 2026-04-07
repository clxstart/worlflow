package com.clx.workflow.framework.filter;

import com.clx.workflow.framework.domain.LoginUser;
import com.clx.workflow.framework.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT 认证过滤器
 *
 * 【这个类的作用】
 * 每次前端发请求到后端，都会经过这个过滤器。
 * 它负责：检查请求有没有带 Token，如果有就验证 Token 是否有效，有效则提取用户信息。
 *
 * 【在整个项目中的位置】
 * 它是 Spring Security 安全框架的一部分，位于请求处理链的最前面。
 * 请求先经过这个过滤器验证身份，验证成功后才能继续访问 Controller。
 *
 * 【继承 OncePerRequestFilter 的原因】
 * OncePerRequestFilter 保证每个请求只过滤一次。
 * 防止请求转发或重定向时被重复过滤。
 *
 * @author clx
 */
@Component  // @Component 表示这是一个 Spring Bean，会被自动扫描并注册到容器里
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * 注入 TokenService，用来处理 Token 的解析和验证
     * 【依赖关系】这个过滤器依赖 TokenService 来做具体的 Token 操作
     */
    @Autowired
    private TokenService tokenService;

    /**
     * 白名单：这些 URL 不需要 Token 验证，可以直接访问
     *
     * 【为什么需要白名单】
     * 用户还没登录时，需要访问 /login 接口来登录。
     * 如果 /login 也需要 Token，那用户永远无法登录，形成死循环。
     *
     * 【白名单里有哪些】
     * - /login：登录接口，用户输入账号密码换取 Token
     * - /register：注册接口
     * - /captcha：验证码接口
     * - /hello：测试接口
     * - /user：临时开放的接口
     * - /test/**：测试相关接口（**表示匹配多级路径）
     * - /static/**：静态资源文件
     * - /favicon.ico：浏览器自动请求的图标
     * - /health：健康检查接口
     */
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/login", "/register", "/captcha", "/hello", "/user",
            "/test/**", "/test/db", "/test/init", "/test/verifyPwd", "/test/fullLogin", "/test/resetPwd", "/test/createTables", "/test/checkRedis",
            "/static/**", "/favicon.ico", "/health"
    );

    /**
     * AntPathMatcher：路径匹配器
     * 【作用】用来判断请求路径是否匹配白名单里的模式
     * 【举例】它能匹配 /test/** 这种带通配符的模式
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * shouldNotFilter：判断这个请求是否需要过滤
     *
     * 【方法作用】
     * 如果返回 true，表示不需要过滤（跳过这个过滤器）
     * 如果返回 false，表示需要过滤（执行 doFilterInternal 方法）
     *
     * 【执行逻辑】
     * 1. 获取请求路径，如 /login 或 /system/user/list
     * 2. 遍历白名单，用 pathMatcher 匹配
     * 3. 如果匹配到白名单，返回 true（跳过过滤）
     * 4. 如果不在白名单，返回 false（需要过滤验证 Token）
     *
     * @param request 当前 HTTP 请求
     * @return true 表示跳过过滤，false 表示需要过滤
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();  // 获取请求路径，如 "/login"
        // 检查路径是否匹配白名单中的任意一个模式
        return WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    /**
     * doFilterInternal：核心过滤方法，验证 Token
     *
     * 【执行时机】
     * 当 shouldNotFilter 返回 false 时（即请求不在白名单），这个方法会被执行。
     *
     * 【方法流程】
     * 1. 从请求头里取出 Token（通过 tokenService.getLoginUser）
     * 2. 如果 Token 有效，解析出用户信息
     * 3. 验证 Token 是否过期
     * 4. 把用户信息存到 SecurityContext（Spring Security 的上下文）
     * 5. 放行请求，继续执行后续过滤器或 Controller
     *
     * 【参数说明】
     * - request：HTTP 请求对象，包含请求头、请求参数等
     * - response：HTTP 响应对象，用来返回数据给前端
     * - chain：过滤器链，用来放行请求（chain.doFilter 表示继续往下走）
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param chain    过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // ========== 第一步：从请求中获取用户信息 ==========
        // tokenService.getLoginUser 会：
        //   1. 从请求头 Authorization 取出 Token
        //   2. 解析 Token，拿到 uuid
        //   3. 用 uuid 去 Redis 查用户信息
        // 返回 LoginUser 对象（包含用户信息），如果 Token 无效则返回 null
        LoginUser loginUser = tokenService.getLoginUser(request);

        // ========== 第二步：验证用户信息并设置到 Spring Security 上下文 ==========
        // 条件1：loginUser != null 表示 Token 有效，成功解析出用户信息
        // 条件2：SecurityContextHolder.getContext().getAuthentication() == null
        //        表示当前请求还没有设置认证信息（防止重复设置）
        if (loginUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 验证 Token 是否过期，如果快过期则自动续期
            // 【具体逻辑见 TokenService.java 的 verifyToken 方法】
            tokenService.verifyToken(loginUser);

            // 创建 Spring Security 的认证对象
            // UsernamePasswordAuthenticationToken 是 Spring Security 的标准认证对象
            // 参数1：principal（主体），这里放 LoginUser 用户信息
            // 参数2：credentials（凭证），密码，已验证所以放 null
            // 参数3：authorities（权限列表），用户的角色权限
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

            // 设置认证详情，包含请求的 IP、SessionId 等信息
            // 【作用】用于审计日志、安全追踪
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ========== 第三步：把认证信息存到 SecurityContext ==========
            // SecurityContext 是 Spring Security 的安全上下文，存储当前登录用户信息
            // 设置后，后续代码可以通过 SecurityContextHolder.getContext().getAuthentication() 获取用户信息
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // ========== 第四步：放行请求 ==========
        // chain.doFilter 表示请求继续往下走，到达下一个过滤器或 Controller
        // 【注意】无论 Token 是否有效，都要放行。无效的请求会被 Spring Security 的后续机制拦截
        chain.doFilter(request, response);
    }
}