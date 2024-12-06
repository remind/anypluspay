package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 外部账户类型
 * @author wxj
 * 2023/12/16
 */
public enum AccountAttribute implements CodeEnum {

    PERSONAL("1", "对私"),
    COMPANY("2", "对公");

    private final String code;
    private final String displayName;

    AccountAttribute(String code, String displayName) {
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
