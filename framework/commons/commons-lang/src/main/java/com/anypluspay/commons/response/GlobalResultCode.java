package com.anypluspay.commons.response;

/**
 * 全局结果码
 *
 * @author wxj
 * 2023/12/22
 */
public enum GlobalResultCode implements ResultCode {
    SUCCESS("SUCCESS", "处理成功"),
    FAIL("FAIL", "处理异常"),
    ILLEGAL_PARAM("ILLEGAL_PARAM", "非法参数"),
    UNKNOWN("UNKNOWN", "未知结果"),
    ;

    private final String code;

    private final String message;

    GlobalResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
