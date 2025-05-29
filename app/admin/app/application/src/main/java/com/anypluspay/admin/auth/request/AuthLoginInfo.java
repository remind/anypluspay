package com.anypluspay.admin.auth.request;

import lombok.Data;

/**
 * @author wxj
 * 2025/5/29
 */
@Data
public class AuthLoginInfo {

    /**
     * 昵称
     */
    private String realName;

    /**
     * 登录名
     */
    private String username;
}
