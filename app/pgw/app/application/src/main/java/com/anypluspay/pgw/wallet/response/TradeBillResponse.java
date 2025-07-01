package com.anypluspay.pgw.wallet.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.anypluspay.commons.lang.std.MoneyDisplaySerializer;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/7/1
 */
@Data
public class TradeBillResponse {

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
    @JsonSerialize(using = MoneyDisplaySerializer.class)
    private Money amount;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;
}
