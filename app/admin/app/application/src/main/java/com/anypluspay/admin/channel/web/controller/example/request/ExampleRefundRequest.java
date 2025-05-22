package com.anypluspay.admin.channel.web.controller.example.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2024/12/8
 */
@Data
public class ExampleRefundRequest {

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 退款类型
     */
    private String refundType;

    /**
     * 原请求单号
     */
    private String origRequestId;

    /**
     * 原订单号
     */
    private String origOrderId;

    /**
     * 退款金额
     */
    private Money amount;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 扩展字段
     */
    private String extra;

    /**
     * 机构扩展信息，渠道网关API要使用的
     */
    private String instExtra;
}
