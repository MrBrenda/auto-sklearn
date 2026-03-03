package com.csc.irms.roadshow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CAS 配置属性，从 application.yml 中读取
 */
@Data
@Component
@ConfigurationProperties(prefix = "cas")
public class CasProperties {

    private Server server = new Server();
    private Client client = new Client();

    @Data
    public static class Server {
        /** CAS Server 登录 URL */
        private String loginUrl;
        /** CAS Server 登出 URL */
        private String logoutUrl;
        /** CAS Server ticket 验证 URL */
        private String validateUrl;
    }

    @Data
    public static class Client {
        /** 本服务的 service URL */
        private String serviceUrl;
    }
}
