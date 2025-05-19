package com.anypluspay.payment.domain.biz.withdraw;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2025/5/15
 */
@Getter
public enum WithdrawOrderStatus implements CodeEnum {

    PAYING("PAYING", "处理中"),

    FAIL("FAIL", "失败"),

    SUCCESS("SUCCESS", "成功"),
    ;

    private final String code;

    private final String displayName;

    WithdrawOrderStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}