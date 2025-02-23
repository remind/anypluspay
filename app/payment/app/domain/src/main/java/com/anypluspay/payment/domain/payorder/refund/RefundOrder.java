package com.anypluspay.payment.domain.payorder.refund;

import com.anypluspay.payment.domain.payorder.BasePayOrder;
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
}
