package com.anypluspay.testbank.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wxj
 * 2024/11/25
 */
@Data
public class OnlineBankPayDto {

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 主题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 通知URL
     */
    private String notifyUrl;

    /**
     * 跳转URL
     */
    private String returnUrl;
}
