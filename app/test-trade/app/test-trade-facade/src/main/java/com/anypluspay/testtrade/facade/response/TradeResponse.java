package com.anypluspay.testtrade.facade.response;

import lombok.Data;

/**
 * 交易响应
 * @author wxj
 * 2025/3/18
 */
@Data
public class TradeResponse {

    /**
     * 交易id
     */
    private String tradeId;

    /**
     * 状态
     */
    private String status;

}
