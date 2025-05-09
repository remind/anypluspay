package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 资金类型
 *
 * @author wxj
 * 2025/5/9
 */
@Getter
public enum FundType implements CodeEnum {
    NORMAL("NORMAL", "常规"),
    ;

    private final String code;
    private final String displayName;

    FundType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;

    }

}
