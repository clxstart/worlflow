package com.clx.workflow.framework.service;

import com.clx.workflow.framework.domain.LoginUser;
import com.clx.workflow.common.core.redis.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Token 验证处理
 *
 * @author clx
 */
@Component
public class TokenService {

    @Value("${token.header:Authorization}")
    private String header;

    @Value("${token.secret:abcdefghijklmnopqrstuvwxyz}")
    private String secret;

    @Value("${token.expireTime:30}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final String LOGIN_TOKEN_KEY = "login_tokens:";

    @Autowired
    private RedisCache redisCache;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (token != null && !token.isEmpty()) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get("login_user_key");
                String userKey = getTokenKey(uuid);
                return redisCache.getCacheObject(userKey);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put("login_user_key", token);
        return createToken(claims);
    }

    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= 10 * MILLIS_MINUTE) {
            refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token) {
        if (token != null && !token.isEmpty()) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return LOGIN_TOKEN_KEY + uuid;
    }
}