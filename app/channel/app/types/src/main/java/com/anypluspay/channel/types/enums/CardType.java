package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum CardType implements CodeEnum {

    UNLIMITED("UNLIMITED", "无限制"),
    DEBIT("debit", "储蓄卡"),
    CREDIT("credit", "信用卡"),
    ;

    private final String code;

    private final String displayName;

    CardType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
