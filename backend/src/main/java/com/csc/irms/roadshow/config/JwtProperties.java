package com.csc.irms.roadshow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /** JWT 签名密钥（Base64 编码） */
    private String secret;

    /** token 过期时间（秒） */
    private long expiration = 28800;
}
