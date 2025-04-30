package com.anypluspay.payment.types.message;

import lombok.Data;

/**
 * 支付订单结果消息
 * @author wxj
 * 2025/4/10
 */
@Data
public class PayOrderResult {

    /**
     * 支付总单号
     */
    private String paymentId;

    /**
     * 支付单号
     */
    private String orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 请求单号
     */
    private String requestId;

    /**
     * 支付状态码
     */
    private String status;

    /**
     * 错误信息
     */
    private String message;


}
