package com.anypluspay.channel.types.channel;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 机构能力
 * @author wxj
 * 2024/10/31
 */
@Getter
public enum InstAbility implements CodeEnum {

    PAY("PAY", "支付"),
    ACQUIRING("ACQUIRING", "收单"),
    AUTH("AUTH", "认证"),
    ;

    private final String code;

    private final String displayName;

    InstAbility(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
