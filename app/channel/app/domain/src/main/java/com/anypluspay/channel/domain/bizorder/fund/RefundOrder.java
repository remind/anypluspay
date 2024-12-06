package com.anypluspay.channel.domain.bizorder.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 退款请求
 * @author wxj
 * 2024/7/13
 */
@Data
public class RefundOrder extends BaseBizOrder {

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

    public RefundOrder() {
        super(RequestType.REFUND);
    }
}
