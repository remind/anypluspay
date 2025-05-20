package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/1/20
 */
@Getter
public enum FluxProcessStatus implements CodeEnum {

    INIT("INIT", "初始化"),

    PROCESS("PROCESS", "处理中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    FluxProcessStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
