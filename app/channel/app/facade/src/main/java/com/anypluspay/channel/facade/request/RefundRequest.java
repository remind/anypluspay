package com.anypluspay.channel.facade.request;

import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * @author wxj
 * 2024/9/24
 */
@Data
public class RefundRequest extends FundRequest {

    /**
     * 退款类型
     */
    private RefundType refundType;

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
}
