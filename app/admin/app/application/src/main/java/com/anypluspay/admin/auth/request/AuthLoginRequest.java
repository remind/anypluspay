package com.anypluspay.admin.auth.request;

import lombok.Data;

/**
 * @author wxj
 * 2025/5/29
 */
@Data
public class AuthLoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
