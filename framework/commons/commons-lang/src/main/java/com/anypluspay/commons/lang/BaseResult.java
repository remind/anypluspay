package com.anypluspay.commons.lang;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.GlobalResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础结果
 *
 * @author wxj
 * 2025/5/16
 */
@Data
public class BaseResult implements Serializable {

    /**
     * 处理状态
     */
    protected boolean success = false;

    /**
     * 结果码
     */
    protected String resultCode;

    /**
     * 结果信息
     */
    protected String resultMsg;

    /**
     * 填充异常结果
     *
     * @param result 结果对象
     * @param e      异常
     */
    public static void fillExceptionResult(BaseResult result, Exception e) {
        result.setSuccess(false);
        if (e instanceof BizException bizException) {
            result.setResultCode(bizException.getCode());
            result.setResultMsg(bizException.getMessage());
        } else {
            result.setResultCode(GlobalResultCode.FAIL.getCode());
            result.setResultMsg(e.getMessage());
        }
    }
}
