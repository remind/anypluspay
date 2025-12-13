package com.anypluspay.anypay.types.account;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 科目类型
 * @author wxj
 * 2024/12/30
 */
public enum AccountTitleType implements CodeEnum {
    Assets("1","资产类"),
    Liabilities("2","负债类"),
    OwnersEquity("3","所有者权益"),
    Public("4","公共类"),
    IncreaseAndDecrease("5","损益类");

    private final String code;
    private final String displayName;


    AccountTitleType(String code, String displayName) {
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
