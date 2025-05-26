package com.anypluspay.admin.auth.request;

import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 系统用户请求
 *
 * @author wxj
 * 2025/5/26
 */
@Data
public class SysUserRequest {

    @NotNull(message = "ID不能为空", groups = UpdateValidate.class)
    private Long id;

    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空", groups = {AddValidate.class, UpdateValidate.class})
    @Length(min = 5, max = 18, message = "昵称长度为5-18", groups = {AddValidate.class, UpdateValidate.class})
    private String nickname;

    /**
     * 登录账户
     */
    @NotNull(message = "登录名不能为空", groups = AddValidate.class)
    @Length(min = 5, max = 18, message = "登录名长度为5-18", groups = AddValidate.class)
    private String username;

    /**
     * 状态
     */
    @NotNull(message = "状态", groups = {AddValidate.class, UpdateValidate.class})
    private String status;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空", groups = AddValidate.class)
    @Length(min = 6, max = 18, message = "密码长度为6-18")
    private String password;
}
