package com.anypluspay.admin.model.order;

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
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 终端类型
     */
    private String terminalType;

    /**
     * 终端信息
     */
    private String terminal;

    /**
     * 路由信息
     */
    private String routeExtra;

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
