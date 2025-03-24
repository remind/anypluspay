package com.anypluspay.admin.channel.model.order;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.anypluspay.component.web.json.std.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/11/21
 */
@Data
public class FundInOrderDto {

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
     * 状态名称
     */
    private String statusName;

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
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 扩展参数
     */
    private String extra;

    /**
     * 路由扩展参数
     */
    private String routeExtra;

    /**
     * 金额
     */
    @JsonSerialize(using = MoneySerializer.class)
    private Money amount;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
