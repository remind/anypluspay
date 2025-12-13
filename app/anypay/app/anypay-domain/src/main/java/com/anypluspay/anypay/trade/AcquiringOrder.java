package com.anypluspay.anypay.trade;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收单订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class AcquiringOrder {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 合作方
     */
    private String partnerId;

    /**
     * 支付单号ID
     */
    private String orderId;

    /**
     * 关联的交易单号
     */
    private String relationTradeId;

    /**
     * 外部订单号
     */
    private String outTradeNo;

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
    private Money amount;

    /**
     * 状态
     */
    private AcquiringOrderStatus status;

    /**
     * 过期时间
     */
    private LocalDateTime gmtExpire;

}
