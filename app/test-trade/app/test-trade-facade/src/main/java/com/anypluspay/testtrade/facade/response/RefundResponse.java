package com.anypluspay.testtrade.facade.response;

import lombok.Data;

/**
 * 退款响应
 * @author wxj
 * 2025/4/27
 */
@Data
public class RefundResponse {

    /**
     * 退款单号
     */
    private String refundId;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 支付总单号
     */
    private String paymentId;

    /**
     * 支付单号
     */
    private String payOrderId;

    /**
     * 退款状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String message;
}
