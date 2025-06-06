package com.anypluspay.payment.domain.trade.acquiring;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.trade.BaseTradeOrder;
import com.anypluspay.payment.types.biz.TradeType;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收单类订单
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringOrder extends BaseTradeOrder {

    /**
     * 关联的交易单号
     */
    private String relationTradeId;

    /**
     * 交易类型
     */
    private TradeType tradeType;

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
    private Money amount;

    /**
     * 状态
     */
    private AcquiringOrderStatus status;

    /**
     * 过期时间
     */
    private LocalDateTime gmtExpire;

    /**
     * 扩展信息
     */
    private Extension extension;

}
