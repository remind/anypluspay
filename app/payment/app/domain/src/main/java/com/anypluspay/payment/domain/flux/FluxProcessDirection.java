package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 指令方向
 *
 * @author wxj
 * 2025/5/13
 */
@Getter
public enum FluxProcessDirection implements CodeEnum {

    APPLY("A", "申请"),
    REVOKE("R", "撤消"),
    ;

    private final String code;

    private final String displayName;

    FluxProcessDirection(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}