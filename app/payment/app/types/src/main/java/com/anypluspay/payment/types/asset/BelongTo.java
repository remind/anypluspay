package com.anypluspay.payment.types.asset;


import com.anypluspay.commons.enums.CodeEnum;

/**
 * @author wxj
 * 2024/1/15
 */
public enum BelongTo implements CodeEnum {

    PAYEE("payee", "收款方") {
        @Override
        public BelongTo reverse() {
            return PAYER;
        }
    },
    PAYER("payer", "付款方") {
        @Override
        public BelongTo reverse() {
            return PAYEE;
        }
    };


    private final String code;

    private final String displayName;

    /**
     * 返回方向
     *
     * @return
     */
    public abstract BelongTo reverse();

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
