package com.anypluspay.payment.types.trade.query;

import lombok.Data;

/**
 * @author wxj
 * 2025/7/4
 */
@Data
public class BaseTradeResponse {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 支付ID
     */
    private String orderId;

    /**
     * 金额
     */
    private String amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;
}
