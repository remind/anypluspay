package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 退款类型
 *
 * @author wxj
 * 2024/8/20
 */
@Getter
public enum RefundType implements CodeEnum {
    PAYEE_REFUND("PAYEE_REFUND", "卖家退款"),
    PAYER_REFUND("PAYER_REFUND", "买家退款"),
    REPEAT_PAY("REPEAT_PAY", "重复支付退款"),
    ;

    private final String code;

    private final String displayName;

    RefundType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
