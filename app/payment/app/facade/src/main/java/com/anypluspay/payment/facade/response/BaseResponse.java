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
     * 交易ID
     */
    private String tradeId;

    /**
     * 支付结果
     */
    private PayResult result;
}
