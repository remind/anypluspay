package com.anypluspay.payment.types.trade.query;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 交易账单类型
 *
 * @author wxj
 * 2025/6/29
 */
@Getter
public enum TradeBillType implements CodeEnum {
    INSTANT_TRANSFER("1", "收入"),
    INSTANT_ACQUIRING("2", "支出"),
    ENSURE_ACQUIRING("3", "充值"),
    REFUND_ACQUIRING("4", "提现"),
    ;
    private final String code;

    private final String displayName;

    TradeBillType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}