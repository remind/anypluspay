package com.anypluspay.testbank.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wxj
 * 2024/12/8
 */
@Data
public class RefundDto {

    /**
     * 请求号
     */
    private String outRequestNo;

    /**
     * 原订单号
     */
    private Long origOrderId;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 通知地址
     */
    private String notifyUrl;

}
