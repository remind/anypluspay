package com.anypluspay.admin.auth;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 系统用户状态
 *
 * @author wxj
 * 2025/5/26
 */
@Getter
public enum SysUserStatus implements CodeEnum {
    ENABLE("E", "启用"),
    DISABLE("D", "禁用"),
    ;

    private final String code;

    private final String displayName;

    SysUserStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}