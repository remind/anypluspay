package com.anypluspay.anypay.types.common;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/12/22
 */
@Getter
public enum PayCommandStatus implements CodeEnum {

    PAYING("PAYING", "支付中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    PayCommandStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}