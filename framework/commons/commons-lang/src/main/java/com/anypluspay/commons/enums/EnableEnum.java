package com.anypluspay.commons.enums;

/**
 * @author wxj
 * 2024/1/4
 */
public enum EnableEnum implements CodeEnum {

    ENABLE("E", "启用"),
    DISABLE("D", "禁用"),
    ;

    private final String code;

    private final String displayName;

    EnableEnum(String code, String displayName) {
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
