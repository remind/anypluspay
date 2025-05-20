package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.process.BasePayProcess;
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
}
