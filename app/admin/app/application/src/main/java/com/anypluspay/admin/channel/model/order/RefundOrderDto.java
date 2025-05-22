package com.anypluspay.admin.channel.model.order;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.anypluspay.commons.lang.std.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/11
 */
@Data
public class RefundOrderDto {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 退款类型
     */
    private String refundType;

    /**
     * 退款类型名称
     */
    private String refundTypeName;

    /**
     * 原订单号
     */
    private String origOrderId;

    /**
     * 原请求号
     */
    private String origRequestId;

    /**
     * 机构订单ID
     */
    private String instOrderId;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 退款金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    private Money amount;

    /**
     * 原因
     */
    private String reason;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
