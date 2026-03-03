package com.csc.irms.roadshow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CAS 登录验证成功后返回给前端的数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {

    /** JWT token */
    private String token;

    /** 用户信息 */
    private UserInfo user;
}
