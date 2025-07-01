package com.anypluspay.payment.types.trade.query;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/6/29
 */
@Data
public class TradeBillDto {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 账单类型
     */
    private String billType;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 交易金额
     */
    private Money amount;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
