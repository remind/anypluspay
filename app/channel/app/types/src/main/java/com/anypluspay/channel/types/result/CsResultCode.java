package com.anypluspay.channel.types.result;

import com.anypluspay.commons.response.ResultCode;
import lombok.Getter;

/**
 * @author wxj
 * 2024/7/13
 */
@Getter
public enum CsResultCode implements ResultCode {

    INVOKE_FAIL("INVOKE_FAIL", "调用器分发异常"),
    UNKNOWN_EXCEPTION("PROCESS", "处理中"),
    WAIT_SUBMIT_INST("WAIT_SUBMIT_INST", "等待提交机构"),
    ORDER_NOT_EXIST("ORDER_NOT_EXIST", "订单不存在"),
    NOT_EXIST_CHANNEL("NOT_EXIST_CHANNEL", "无可用渠道"),
    ;

    private final String code;

    private final String message;

    CsResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
