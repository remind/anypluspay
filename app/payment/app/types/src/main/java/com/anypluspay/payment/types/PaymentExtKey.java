package com.anypluspay.payment.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 扩展参数Key
 * @author wxj
 * 2025/4/8
 */
@Getter
public enum PaymentExtKey implements CodeEnum {
    PAY_INST("payInst", "支付机构"),
    INST_EXT("instExt", "机构扩展参数"),
    ;

    private final String code;

    private final String displayName;

    PaymentExtKey(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
