package com.anypluspay.payment.domain.payorder;

import com.anypluspay.payment.types.OrderStatus;
import lombok.Getter;

/**
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum PayOrderStatus implements OrderStatus {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    PayOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
