package com.anypluspay.admin.payment.response.trade;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/5/19
 */
@Data
public abstract class BaseTradeResponse {

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
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
