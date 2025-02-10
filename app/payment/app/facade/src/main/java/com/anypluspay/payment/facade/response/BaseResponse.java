package com.anypluspay.payment.facade.response;

import com.anypluspay.payment.types.PayResult;
import lombok.Data;

/**
 * @author wxj
 * 2024/1/29
 */
@Data
public class BaseResponse {

    /**
     * 支付总单号
     */
    private String paymentId;

    /**
     * 支付结果
     */
    private PayResult result;
}
