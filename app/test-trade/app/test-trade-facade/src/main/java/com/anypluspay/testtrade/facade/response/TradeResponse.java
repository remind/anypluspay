package com.anypluspay.testtrade.facade.response;

import com.anypluspay.testtrade.facade.request.TradeRequest;
import lombok.Data;

/**
 * 交易响应
 * @author wxj
 * 2025/3/18
 */
@Data
public class TradeResponse extends TradeRequest {

    /**
     * 交易id
     */
    private String tradeId;

    /**
     * 状态
     */
    private String status;

}
