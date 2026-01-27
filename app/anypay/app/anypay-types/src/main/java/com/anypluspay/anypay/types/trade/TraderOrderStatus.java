package com.anypluspay.anypay.types.trade;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 交易订单状态
 * @author wxj
 * 2026/1/27
 */
@Getter
public enum TraderOrderStatus implements CodeEnum {

    INIT("INIT", "初始化"),

    PAYING("PAYING", "支付中"),

    SUCCESS("SUCCESS", "成功"),

    CLOSED("CLOSED", "关闭"),
    ;

    private final String code;

    private final String displayName;

    TraderOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}

