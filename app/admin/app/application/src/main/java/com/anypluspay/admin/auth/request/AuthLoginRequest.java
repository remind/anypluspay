package com.anypluspay.admin.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 登录认证请求
 * @author wxj
 * 2025/5/29
 */
@Data
public class AuthLoginRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 18, message = "用户名长度为5-18")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度为6-18")
    private String password;
}
