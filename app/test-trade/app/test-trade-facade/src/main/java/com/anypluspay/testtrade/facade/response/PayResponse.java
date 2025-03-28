package com.anypluspay.testtrade.facade.response;

import lombok.Data;

/**
 * 支付结果
 *
 * @author wxj
 * 2025/3/18
 */
@Data
public class PayResponse {

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
     * 交易状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String message;
}
