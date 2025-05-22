package com.anypluspay.admin.payment.response;

import lombok.Data;

/**
 * 收单订单
 *
 * @author wxj
 * 2025/5/19
 */
@Data
public class AcquiringOrderResponse extends AbstractBizOrderResponse {

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

}
