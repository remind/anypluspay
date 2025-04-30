package com.anypluspay.admin.trade.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/4/21
 */
@Data
public class TradeOrderResponse {

    /**
     * 交易号
     */
    private String id;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 收款方ID
     */
    private String payeeId;

    /**
     * 收款方账户号
     */
    private String payeeAccount;

    /**
     * 付款方ID
     */
    private String payerId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 金额
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
