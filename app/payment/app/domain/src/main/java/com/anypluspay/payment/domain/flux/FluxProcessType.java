package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 指令类型
 * @author wxj
 * 2025/2/7
 */
@Getter
public enum FluxProcessType implements CodeEnum {

    NORMAL("N","常规指令"),
    CLEARING("C","清算指令"),
    ;

    private final String code;

    private final String displayName;

    FluxProcessType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
