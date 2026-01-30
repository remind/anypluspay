package com.anypluspay.anypay.types.trade;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2026/1/30
 */
@Getter
public enum TradeNotifyStatus implements CodeEnum {

    WAIT_NOTIFY("W", "待通知"),
    SUCCESS("S", "成功"),
    IGNORED("I", "忽略"),
    ;
    private final String code;

    private final String displayName;

    TradeNotifyStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}