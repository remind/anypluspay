package com.anypluspay.payment.types.biz;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/5/17
 */
@Getter
public enum TradeType implements CodeEnum {

    INSTANT_TRANSFER("10", "普通转账"),
    INSTANT_ACQUIRING("20", "即时到账"),
    ENSURE_ACQUIRING("21", "担保交易"),
    REFUND_ACQUIRING("30", "收单退款"),

    ;
    private final String code;

    private final String displayName;

    TradeType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}