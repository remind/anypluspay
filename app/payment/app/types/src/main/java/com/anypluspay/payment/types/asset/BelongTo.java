package com.anypluspay.payment.types.asset;


import com.anypluspay.commons.enums.CodeEnum;

/**
 * @author wxj
 * 2024/1/15
 */
public enum BelongTo implements CodeEnum {

    PAYEE("payee", "收款方"),
    PAYER("payer","付款方");


    private String code;

    private String displayName;

    BelongTo(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
