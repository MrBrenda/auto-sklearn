package com.csc.irms.roadshow.util;

import com.csc.irms.roadshow.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 工具类
 * <p>
 * 负责 token 的生成和解析验证
 */
@Component
public class JwtUtil {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtUtil(JwtProperties properties) {
        byte[] keyBytes = Base64.getDecoder().decode(properties.getSecret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = properties.getExpiration() * 1000;
    }

    /**
     * 为指定用户名生成 JWT token
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    /**
     * 从 token 中解析用户名
     *
     * @return 用户名，解析失败返回 null
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 验证 token 是否有效（签名正确且未过期）
     */
    public boolean validateToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return false;
        }
        return claims.getExpiration().after(new Date());
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
