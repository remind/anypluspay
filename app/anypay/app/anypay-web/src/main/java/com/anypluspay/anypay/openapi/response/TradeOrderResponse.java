package com.anypluspay.anypay.openapi.response;

import lombok.Data;

/**
 * @author wxj
 * 2026/1/27
 */
@Data
public class TradeOrderResponse {

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
    private String tradeType;

    /**
     * 金额
     */
    private String amount;

    /**
     * 货币代码
     */
    private String currency;

    /**
     * 状态
     */
    private String status;

}
