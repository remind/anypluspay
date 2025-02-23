package com.anypluspay.payment.domain.refund;

import com.anypluspay.payment.types.OrderStatus;
import lombok.Getter;

/**
 * 退款状态
 * @author remind
 * 2023年05月06日 21:37
 */
@Getter
public enum RefundOrderStatus implements OrderStatus {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    RefundOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
