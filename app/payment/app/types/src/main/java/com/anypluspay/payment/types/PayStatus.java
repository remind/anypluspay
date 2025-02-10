package com.anypluspay.payment.types;

import lombok.Getter;

/**
 * 支付状态
 * @author wxj
 * 2024/1/26
 */
@Getter
public enum PayStatus implements OrderStatus {

    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    PROCESS("PROCESS", "处理中"),
    ;

    private final String code;

    private final String displayName;

    PayStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}