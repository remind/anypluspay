package com.anypluspay.channel.types.enums;


import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum PayMode implements CodeEnum {

    ONLINE_BANK("ONLINE_BANK", "网银"),
    BALANCE("BALANCE", "余额"),
    QUICKPAY("QPAY", "快捷支付"),
    ;

    private final String code;

    private final String displayName;

    PayMode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
