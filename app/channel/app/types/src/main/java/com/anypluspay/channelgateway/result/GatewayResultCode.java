package com.anypluspay.channelgateway.result;

import com.anypluspay.commons.response.ResultCode;
import lombok.Getter;

/**
 * @author wxj
 * 2024/9/18
 */
@Getter
public enum GatewayResultCode implements ResultCode {

    NOT_SUPPORT("NOT_SUPPORT", "不支持的请求"),
    ;

    private final String code;

    private final String message;

    GatewayResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}