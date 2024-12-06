package com.anypluspay.commons.enums;

/**
 * 结果状态
 * @author wxj
 * 2024/1/8
 */
public enum ResultStatusEnum implements CodeEnum {

    UNKNOWN("U", "未知"),
    FAIL("F", "失败"),
    SUCCESS("S", "成功"),
    ;

    private final String code;

    private final String displayName;

    ResultStatusEnum(String code, String displayName) {
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
