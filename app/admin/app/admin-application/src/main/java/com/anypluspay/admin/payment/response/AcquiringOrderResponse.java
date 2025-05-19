package com.anypluspay.admin.payment.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收单订单
 * @author wxj
 * 2025/5/19
 */
@Data
public class AcquiringOrderResponse {

    /**
     * 支付总单号
     */
    private String paymentId;

    /**
     * 关联的支付业务单号
     */
    private String relationPaymentId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 合作方
     */
    private String partnerId;

    /**
     * 收款方
     */
    private String payeeId;

    /**
     * 收款方账户号
     */
    private String payeeAccountNo;

    /**
     * 付款方
     */
    private String payerId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 金额
     */
    private String amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
