package com.anypluspay.payment.types.paymethod;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 支付模式，对支付方式的进一步抽象，不同支付模式拥有不同参数
 * @author wxj
 * 2025/4/3
 */
@Getter
public enum PayModel implements CodeEnum {
    /**
     * 跳转类的均可算
     */
    ONLINE_BANK("ONLINE_BANK", "网银支付"),

    /**
     * 自建账户余额支付
     */
    BALANCE("BALANCE", "余额支付"),

    /**
     * 快捷支付，通常指银行卡需要签约型的
     */
    QUICK_PAY("QUICK_PAY", "快捷支付"),
    ;

    private final String code;

    private final String displayName;

    PayModel(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
