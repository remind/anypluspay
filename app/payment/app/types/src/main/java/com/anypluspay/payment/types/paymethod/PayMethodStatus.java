package com.anypluspay.payment.types.paymethod;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/5/28
 */
@Getter
public enum PayMethodStatus implements CodeEnum {

    ENABLE("E", "启用"),
    DISABLE("D", "禁用"),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String displayName;

    PayMethodStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}