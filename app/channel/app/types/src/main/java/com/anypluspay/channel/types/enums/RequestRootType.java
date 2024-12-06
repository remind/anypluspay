package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 请求类型
 *
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum RequestRootType implements CodeEnum {
    FUND("FUND", "资金"),
    AUTH("AUTH", "鉴权"),
    CONTROL("CONTROL", "控制类"),
    ;

    private final String code;

    private final String displayName;

    RequestRootType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
