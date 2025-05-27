package com.anypluspay.admin.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新密码
 * @author wxj
 * 2025/5/26
 */
@Data
public class SysUserUpdatePasswordRequest {

    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度为6-18")
    private String newPassword;

    /**
     * 重复密码
     */
    @NotNull(message = "重复密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度为6-18")
    private String rePassword;
}
