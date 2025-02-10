package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 指令类型
 * @author wxj
 * 2025/2/7
 */
@Getter
public enum InstructionType implements CodeEnum {

    CLEARING("CLEARING","清算指令"),
    PAY("PAY","支付指令"),
    REFUND("REFUND","退款指令"),
    ;

    private final String code;

    private final String displayName;

    InstructionType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
