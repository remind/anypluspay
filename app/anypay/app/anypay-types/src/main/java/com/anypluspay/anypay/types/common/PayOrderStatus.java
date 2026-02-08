package com.anypluspay.anypay.types.common;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 支付状态
 *
 * @author wxj
 * 2025/12/22
 */
@Getter
public enum PayOrderStatus implements CodeEnum {

    INIT("INIT", "初始化"),
    PAYING("PAYING", "支付中"),
    CLOSED("CLOSED", "关闭"),
    FAIL("FAIL", "失败"),
    MANUAL("MANUAL", "人工处理"),
    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    PayOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}