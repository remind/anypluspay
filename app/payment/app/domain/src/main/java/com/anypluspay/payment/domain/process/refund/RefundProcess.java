package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.process.BasePayProcess;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import lombok.Data;

/**
 * @author wxj
 * 2024/1/15
 */
@Data
public class RefundProcess extends BasePayProcess<RefundOrderStatus> {

    /**
     * 关联订单号
     */
    private String relationId;

    /**
     * 退款类型
     */
    private RefundType refundType;
}
