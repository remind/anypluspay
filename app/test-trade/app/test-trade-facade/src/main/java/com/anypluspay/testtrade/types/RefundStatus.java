package com.anypluspay.testtrade.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 退款状态
 *
 * @author wxj
 * 2025/4/27
 */
@Getter
public enum RefundStatus implements CodeEnum {
    INIT("0", "处理中"),

    SUCCESS("1", "成功"),

    FAIL("2", "失败"),

    ;

    private final String code;
    private final String displayName;

    RefundStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
