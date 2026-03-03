package com.csc.irms.roadshow.util;

import com.csc.irms.roadshow.config.CasProperties;
import com.csc.irms.roadshow.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CAS Ticket 验证器
 * <p>
 * 使用 CAS 3.0 协议（/p3/serviceValidate）向 CAS Server 验证 ticket，
 * 并解析返回的 XML 获取用户信息。
 * <p>
 * CAS 3.0 验证成功的 XML 响应示例：
 * <pre>{@code
 * <cas:serviceResponse>
 *   <cas:authenticationSuccess>
 *     <cas:user>zhangsan</cas:user>
 *     <cas:attributes>
 *       <cas:displayName>张三</cas:displayName>
 *       <cas:email>zhangsan@csc.com.cn</cas:email>
 *     </cas:attributes>
 *   </cas:authenticationSuccess>
 * </cas:serviceResponse>
 * }</pre>
 */
@Component
public class CasValidator {

    private static final Logger log = LoggerFactory.getLogger(CasValidator.class);

    private static final Pattern USER_PATTERN =
            Pattern.compile("<cas:user>(.*?)</cas:user>");
    private static final Pattern DISPLAY_NAME_PATTERN =
            Pattern.compile("<cas:displayName>(.*?)</cas:displayName>");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("<cas:email>(.*?)</cas:email>");
    private static final Pattern AUTH_FAILURE_PATTERN =
            Pattern.compile("<cas:authenticationFailure");

    private final CasProperties casProperties;
    private final RestClient restClient;

    public CasValidator(CasProperties casProperties) {
        this.casProperties = casProperties;
        this.restClient = RestClient.create();
    }

    /**
     * 向 CAS Server 验证 ticket
     *
     * @param ticket  CAS 回调携带的 ticket
     * @param service 当前服务的 service URL
     * @return 验证成功返回 UserInfo，失败返回 null
     */
    public UserInfo validate(String ticket, String service) {
        String validateUrl = casProperties.getServer().getValidateUrl();

        try {
            String responseBody = restClient.get()
                    .uri(validateUrl + "?ticket={ticket}&service={service}", ticket, service)
                    .retrieve()
                    .body(String.class);

            log.debug("CAS validate response: {}", responseBody);

            if (responseBody == null || AUTH_FAILURE_PATTERN.matcher(responseBody).find()) {
                log.warn("CAS ticket validation failed for ticket: {}", ticket);
                return null;
            }

            // 解析用户名（必须存在）
            Matcher userMatcher = USER_PATTERN.matcher(responseBody);
            if (!userMatcher.find()) {
                log.warn("CAS response does not contain user element");
                return null;
            }
            String username = userMatcher.group(1).trim();

            // 解析可选属性
            String displayName = extractOptional(DISPLAY_NAME_PATTERN, responseBody, username);
            String email = extractOptional(EMAIL_PATTERN, responseBody, null);

            return UserInfo.builder()
                    .username(username)
                    .displayName(displayName)
                    .email(email)
                    .build();

        } catch (Exception e) {
            log.error("CAS ticket validation error", e);
            return null;
        }
    }

    private String extractOptional(Pattern pattern, String text, String defaultValue) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : defaultValue;
    }
}
