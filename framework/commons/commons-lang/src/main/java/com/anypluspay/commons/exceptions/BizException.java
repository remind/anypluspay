package com.anypluspay.commons.exceptions;

import com.anypluspay.commons.response.GlobalResultCode;
import com.anypluspay.commons.response.ResultCode;

/**
 * 业务异常
 * @author wxj
 * 2023/12/16
 */
public class BizException extends RuntimeException {

    private final String code;

    public BizException(Exception e) {
        super(e.getMessage());
        if (e instanceof BizException bizException) {
            this.code = bizException.getCode();
        } else {
            this.code = GlobalResultCode.FAIL.getCode();
        }
    }

    public BizException(ResultCode resultCode) {
        this(resultCode, resultCode.getMessage());
    }
    public BizException(String message) {
        this(GlobalResultCode.FAIL, message);
    }

    public BizException(ResultCode resultCode, String message) {
        this(resultCode == null ? null : resultCode.getCode(), message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code != null ? code : GlobalResultCode.FAIL.getCode();
    }

    public String getCode() {
        return code;
    }

}
