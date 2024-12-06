package com.anypluspay.channel.domain.institution;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/8/6
 */
@Getter
public enum InstOrderType implements CodeEnum {

    FUND_IN("FUND_IN", "流入类"),
    REFUND("REFUND", "退款"),
    CONTROL("CONTROL", "控制类"),
    ;

    private final String code;

    private final String displayName;

    InstOrderType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
