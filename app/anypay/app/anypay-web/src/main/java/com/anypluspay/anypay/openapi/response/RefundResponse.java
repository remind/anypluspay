package com.anypluspay.anypay.openapi.response;

import lombok.Data;

/**
 * @author wxj
 * 2026/1/27
 */
@Data
public class RefundResponse {

    /**
     * 退款交易单号
     */
    private String refundTradeOrderId;

    /**
     * 退款外部交易单号
     */
    private String outTradeNo;

    /**
     * 原交易单号
     */
    private String origTradeOrderId;

    /**
     * 原外部交易单号
     */
    private String origOutTradeNo;

    /**
     * 退款金额
     */
    private String amount;

    /**
     * 退款状态
     */
    private String status;

}
