package com.anypluspay.payment.application.instant.request;

import lombok.Data;

/**
 * @author remind
 * 2023年07月15日 11:46
 */
@Data
public abstract class BasePaymentRequest {

    /**
     * 支付请求号
     */
    private String requestId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 发起人ID，会做为生成各类ID的凭证
     * @return
     */
    public abstract String getMemberId();

}
