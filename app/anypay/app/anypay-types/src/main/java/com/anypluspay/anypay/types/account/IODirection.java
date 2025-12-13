package com.anypluspay.anypay.types.account;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 账户实际余额方向
 * @author wxj
 * 2023/12/16
 */
public enum IODirection implements CodeEnum {

    IN("I", "加"),

    OUT("O", "减");
    ;

    private final String code;

    private final String displayName;

    IODirection(String code, String displayName) {
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
