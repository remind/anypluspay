package com.anypluspay.testtrade.facade.request;

import lombok.Data;

/**
 * 退款请求
 * @author wxj
 * 2025/4/27
 */
@Data
public class TradeRefundRequest {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 退款金额
     */
    private String amount;

}
