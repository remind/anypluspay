package com.anypluspay.anypay.openapi.response;

import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.anypay.types.trade.TraderOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2026/1/27
 */
@Data
public class TradeOrderResponse {

    /**
     * 交易单号
     */
    private String tradeOrderId;

    /**
     * 外部交易单号
     */
    private String outTradeNo;

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
