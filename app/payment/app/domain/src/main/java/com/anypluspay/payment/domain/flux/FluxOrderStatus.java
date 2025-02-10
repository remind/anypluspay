package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/1/29
 */
@Getter
public enum FluxOrderStatus implements CodeEnum {

    INIT("INIT", "初始化"),

    PROCESS("PROCESS", "处理中"),

    FAIL("FAIL", "失败"),

    CANCEL("CANCEL", "撤消"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    FluxOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}