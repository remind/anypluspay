package com.anypluspay.payment.types.pay;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 退款类型
 *
 * @author wxj
 * 2025/5/23
 */
@Getter
public enum RefundType implements CodeEnum {
    REPEAT("rp", "重复支付退款"),
    ORDER_CLOSE("oc", "订单已关闭"),
    BIZ_REQUEST("br", "业务请求退款"),
    ;

    private final String code;

    private final String displayName;

    RefundType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}