package com.anypluspay.anypay.types.trade;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 充值订单状态
 * @author wxj
 * 2025/5/14
 */
@Getter
public enum DepositOrderStatus implements CodeEnum {

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    DepositOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}