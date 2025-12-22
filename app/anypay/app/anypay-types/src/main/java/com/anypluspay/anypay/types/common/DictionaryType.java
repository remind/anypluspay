package com.anypluspay.anypay.types.common;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/8/27
 */
@Getter
public enum DictionaryType implements CodeEnum {

    PAY_METHOD("PAY_METHOD", "支付方式"),
    ;

    private final String code;

    private final String displayName;

    DictionaryType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static DictionaryType getByCode(String code) {
        for (DictionaryType value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
