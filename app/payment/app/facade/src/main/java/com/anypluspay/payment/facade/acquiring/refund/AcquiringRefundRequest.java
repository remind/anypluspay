package com.anypluspay.payment.facade.acquiring.refund;

import lombok.Data;

/**
 * 交易退款请求
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringRefundRequest {

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 外部交易退款号
     */
    private String outTradeNo;

    /**
     * 原支付单ID
     */
    private String origPaymentId;

    /**
     * 原外部交易号
     */
    private String origOutTradeNo;

    /**
     * 退款金额
     */
    private String amount;
}
