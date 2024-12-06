package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 激活状态
 * @author wxj
 * 2023/12/16
 */
public enum ActivateStatus implements CodeEnum {
    NOTACTIVATED("0", "未激活"),

    ACTIVATED("1", "已激活");

    private final String code;
    private final String displayName;

    ActivateStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;

    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
