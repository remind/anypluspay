package com.anypluspay.channelgateway.testbank.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/9
 */
@Data
public class RefundOrderResponse {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 支付订单号
     */
    private Long payOrderId;

    /**
     * 退款请求号
     */
    private String outRequestNo;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
