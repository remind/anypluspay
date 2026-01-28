package com.anypluspay.anypay.domain.trade;

import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.anypay.types.trade.TraderOrderStatus;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 交易订单
 *
 * @author wxj
 * 2026/1/27
 */
@Data
public class TradeOrder extends Entity {
    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 外部交易单号
     */
    private String outTradeNo;

    /**
     * 交易类型
     */
    private TradeType tradeType;

    /**
     * 关联的交易单号
     */
    private String relationTradeId;

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
     * 描述
     */
    private String body;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 状态
     */
    private TraderOrderStatus status;

    /**
     * 过期时间
     */
    private LocalDateTime gmtExpire;
}
