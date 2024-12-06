package com.anypluspay.admin.model.order;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.web.json.std.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 入款订单DTO
 * @author wxj
 * 2024/11/13
 */
@Data
public class FundInOrderDetailDto {

    /**
     * 渠道订单号
     */
    private String orderId;

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 状态
     */
    private String status;

    /**
     * 机构订单ID
     */
    private String instOrderId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    private Money amount;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}
