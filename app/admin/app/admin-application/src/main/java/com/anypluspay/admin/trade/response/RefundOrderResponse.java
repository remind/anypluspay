package com.anypluspay.admin.trade.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款订单
 * @author wxj
 * 2025/4/30
 */
@Data
public class RefundOrderResponse {

    /**
     * 退款单号
     */
    private String id;

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
