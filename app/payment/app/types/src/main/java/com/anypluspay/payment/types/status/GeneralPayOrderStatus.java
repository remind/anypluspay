package com.anypluspay.payment.types.status;

import lombok.Getter;

/**
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum GeneralPayOrderStatus implements OrderStatus {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    GeneralPayOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
