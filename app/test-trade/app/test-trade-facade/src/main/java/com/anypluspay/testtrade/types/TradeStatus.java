package com.anypluspay.testtrade.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/3/18
 */
@Getter
public enum TradeStatus implements CodeEnum {
    INIT("0", "初始"),

    SUCCESS("1", "成功"),

    FAIL("2", "失败"),

    ;

    private final String code;
    private final String displayName;

    TradeStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
