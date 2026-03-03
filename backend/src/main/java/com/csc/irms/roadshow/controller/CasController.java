package com.csc.irms.roadshow.controller;

import com.csc.irms.roadshow.config.CasProperties;
import com.csc.irms.roadshow.model.LoginResult;
import com.csc.irms.roadshow.model.R;
import com.csc.irms.roadshow.model.UserInfo;
import com.csc.irms.roadshow.util.CasValidator;
import com.csc.irms.roadshow.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * CAS 认证控制器
 * <p>
 * 提供两个核心接口：
 * <ul>
 *   <li>GET /api/cas/login  - 跳转到 CAS 登录页</li>
 *   <li>GET /api/cas/validate - 验证 CAS ticket 并返回 JWT token</li>
 * </ul>
 *
 * <h3>完整认证流程</h3>
 * <pre>
 * 1. 前端发现用户未登录 → 重定向到 GET /api/cas/login?service=前端页面地址
 * 2. 后端构造 CAS 登录 URL 并 302 跳转
 * 3. 用户在 CAS 登录页完成认证
 * 4. CAS Server 回调到 service 地址，并在 URL 中附带 ticket 参数
 * 5. 前端拿到 ticket → 调用 GET /api/cas/validate?ticket=xxx&service=前端页面地址
 * 6. 后端拿 ticket 去 CAS Server 验证 → 验证通过后生成 JWT token 返回前端
 * 7. 前端保存 token，后续请求通过 Authorization 头携带
 * </pre>
 */
@RestController
@RequestMapping("/cas")
public class CasController {

    private static final Logger log = LoggerFactory.getLogger(CasController.class);

    private final CasProperties casProperties;
    private final CasValidator casValidator;
    private final JwtUtil jwtUtil;

    public CasController(CasProperties casProperties,
                         CasValidator casValidator,
                         JwtUtil jwtUtil) {
        this.casProperties = casProperties;
        this.casValidator = casValidator;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 跳转 CAS 登录页
     * <p>
     * 前端将 service 参数设为当前页面地址，CAS 登录成功后会带 ticket 回调到该地址。
     *
     * @param service 前端页面地址（CAS 登录成功后回调地址）
     */
    @GetMapping("/login")
    public void login(@RequestParam String service,
                      HttpServletResponse response) throws IOException {
        String encodedService = URLEncoder.encode(service, StandardCharsets.UTF_8);
        String casLoginUrl = casProperties.getServer().getLoginUrl() + "?service=" + encodedService;
        log.info("Redirecting to CAS login: {}", casLoginUrl);
        response.sendRedirect(casLoginUrl);
    }

    /**
     * 验证 CAS ticket
     * <p>
     * 前端收到 CAS 回调的 ticket 后，调用此接口。
     * 后端向 CAS Server 校验 ticket，校验通过后签发 JWT token。
     *
     * @param ticket  CAS 回调携带的 ticket
     * @param service 前端页面地址（必须与登录时一致）
     * @return JWT token 和用户信息
     */
    @GetMapping("/validate")
    public R<LoginResult> validate(@RequestParam String ticket,
                                   @RequestParam String service) {
        log.info("Validating CAS ticket for service: {}", service);

        UserInfo userInfo = casValidator.validate(ticket, service);
        if (userInfo == null) {
            return R.fail(401, "CAS ticket 验证失败");
        }

        // 签发 JWT token
        String token = jwtUtil.generateToken(userInfo.getUsername());

        LoginResult result = LoginResult.builder()
                .token(token)
                .user(userInfo)
                .build();

        log.info("CAS login success for user: {}", userInfo.getUsername());
        return R.ok(result);
    }

    /**
     * CAS 登出
     * <p>
     * 跳转到 CAS Server 的登出地址，CAS 会销毁 SSO 会话后跳转回 service 指定的地址。
     *
     * @param service 登出后回跳地址（通常为前端首页）
     */
    @GetMapping("/logout")
    public void logout(@RequestParam(required = false) String service,
                       HttpServletResponse response) throws IOException {
        String casLogoutUrl = casProperties.getServer().getLogoutUrl();
        if (service != null && !service.isEmpty()) {
            String encodedService = URLEncoder.encode(service, StandardCharsets.UTF_8);
            casLogoutUrl += "?service=" + encodedService;
        }
        log.info("Redirecting to CAS logout: {}", casLogoutUrl);
        response.sendRedirect(casLogoutUrl);
    }
}
