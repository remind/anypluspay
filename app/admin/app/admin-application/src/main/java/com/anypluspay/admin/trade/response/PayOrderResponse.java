package com.anypluspay.admin.trade.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/4/21
 */
@Data
public class PayOrderResponse {

    /**
     * 支付单号
     */
    private String id;

    /**
     * 交易单号
     */
    private String tradeId;

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
