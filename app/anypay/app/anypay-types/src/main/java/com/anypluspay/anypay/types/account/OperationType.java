package com.anypluspay.anypay.types.account;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 操作类型
 * @author wxj
 * 2025/1/8
 */
@Getter
public enum OperationType implements CodeEnum {

    NORMAL("NM", "常规"),

    FROZEN("FR", "冻结"),

    UNFROZEN("UF", "解冻"),
    ;

    private final String code;

    private final String displayName;

    OperationType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}