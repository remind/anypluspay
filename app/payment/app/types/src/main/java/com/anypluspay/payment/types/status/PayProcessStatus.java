package com.anypluspay.payment.types.status;

import lombok.Getter;

/**
 * 支付指令状态
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum PayProcessStatus implements OrderStatus {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    PayProcessStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
