package com.anypluspay.channelgateway.testbank.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/4
 */
@Data
public class PayOrder {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 标题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 通知URL
     */
    private String notifyUrl;

    /**
     * 跳转URL
     */
    private String returnUrl;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
