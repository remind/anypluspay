package com.anypluspay.account.types.accounting;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 科目适用范围
 *
 * @author wxj
 * 2023/12/16
 */
public enum AccountTitleRange implements CodeEnum {
    INNER_ENTRIES("1", "内部科目"),
    OUTER_ENTRIES("2", "外部科目");

    private final String code;
    private final String displayName;


    AccountTitleRange(String code, String displayName) {
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
