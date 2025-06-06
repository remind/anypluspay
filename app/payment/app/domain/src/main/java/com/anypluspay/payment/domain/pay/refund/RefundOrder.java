package com.anypluspay.payment.domain.pay.refund;

import com.anypluspay.payment.domain.pay.BasePayOrder;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import lombok.Data;

/**
 * @author wxj
 * 2024/1/15
 */
@Data
public class RefundOrder extends BasePayOrder<RefundOrderStatus> {

    /**
     * 关联订单号
     */
    private String relationId;

    /**
     * 退款类型
     */
    private RefundType refundType;
}
