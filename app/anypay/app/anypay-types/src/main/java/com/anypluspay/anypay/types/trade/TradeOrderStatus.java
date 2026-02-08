package com.anypluspay.anypay.types.trade;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 交易订单状态
 *
 * @author wxj
 * 2026/1/27
 */
@Getter
public enum TradeOrderStatus implements CodeEnum {

    WAIT_PAY("WAIT_PAY", "待支付"), // 支付单待支付，退款单退款中
    SUCCESS("SUCCESS", "成功"),
    CLOSED("CLOSED", "关闭"),
    FAIL("FAIL", "失败"),
    ;

    private final String code;

    private final String displayName;

    TradeOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}

