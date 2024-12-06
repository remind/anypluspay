package com.anypluspay.account.types.buffer;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 缓冲规则状态
 * @author wxj
 * 2023/12/25
 */
public enum BufferedRuleStatus implements CodeEnum {
    INVALID("0","无效"),

    VALID("1", "有效"),

    ;

    private final String code;
    private final String displayName;

    private BufferedRuleStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }
}
