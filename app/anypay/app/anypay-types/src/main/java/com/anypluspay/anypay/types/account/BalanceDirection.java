package com.anypluspay.anypay.types.account;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 余额方向
 * @author wxj
 * 2023/12/16
 */
public enum BalanceDirection implements CodeEnum {

    TWO_WAY("T","双向"),

    DEBIT("D", "借方"),

    CREDIT("C", "贷方");

    private final String code;
    private final String displayName;

    private BalanceDirection(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }
}
