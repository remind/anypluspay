package com.anypluspay.admin.payment.response.trade;

import lombok.Data;

/**
 * 收单订单
 *
 * @author wxj
 * 2025/5/19
 */
@Data
public class AcquiringOrderResponse extends BaseTradeResponse {

    /**
     * 关联的交易ID
     */
    private String relationTradeId;

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
