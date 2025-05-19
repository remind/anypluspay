package com.anypluspay.payment.types.status;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/5/17
 */
@Getter
public enum TradeOrderStatus implements CodeEnum {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    SUCCESS("SUCCESS", "成功"),

    CLOSED("CLOSED", "关闭"),
    ;

    private final String code;

    private final String displayName;

    TradeOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}