package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 缓冲明细状态
 * @author wxj
 * 2023/12/21
 */
public enum BufferDetailStatus implements CodeEnum {
    INIT("1", "待入账"),
    PROCESSING("2", "处理中"),
    SUCCESS("3", "成功"),
    FAIL("4", "失败"),
    ;

    private final String code;
    private final String displayName;

    private BufferDetailStatus(String code, String displayName) {
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
