package com.anypluspay.channel.types.result;

import com.anypluspay.channel.types.order.InstOrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 指定型结果，主要是人工场景会使用，如人工退款
 * @author wxj
 * 2024/9/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignResult extends ProcessResult {

    /**
     * 机构订单状态，当为指定结果时取该状态
     */
    private InstOrderStatus orderStatus;
}
