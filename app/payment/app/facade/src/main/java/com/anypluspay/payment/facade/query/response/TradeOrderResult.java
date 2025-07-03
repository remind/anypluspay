package com.anypluspay.payment.facade.query.response;

import lombok.Data;

/**
 * 交易订单结果
 * @author wxj
 * 2025/7/3
 */
@Data
public class TradeOrderResult {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 状态
     */
    private String orderStatus;
}
