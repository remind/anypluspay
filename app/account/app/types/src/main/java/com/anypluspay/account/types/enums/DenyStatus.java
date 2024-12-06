package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 禁止状态
 * @author wxj
 * 2023/12/16
 */
public enum DenyStatus implements CodeEnum {
    INIT("0", "未冻结"),

    DENY_OUT("1", "止出"),

    DENY_IN("2", "止入"),

    DENY_IN_OUT("3", "止入止出");

    private final String code;
    private final String displayName;

    DenyStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;

    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
