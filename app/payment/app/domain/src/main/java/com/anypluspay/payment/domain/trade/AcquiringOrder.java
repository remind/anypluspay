package com.anypluspay.payment.domain.trade;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.biz.PaymentBizOrder;
import com.anypluspay.payment.types.TradeType;
import com.anypluspay.payment.types.status.TradeOrderStatus;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringOrder extends PaymentBizOrder {

    /**
     * 关联的支付业务单号
     */
    private String relationPaymentId;

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
    private TradeOrderStatus status;

    /**
     * 扩展信息
     */
    private Extension extension;

}
