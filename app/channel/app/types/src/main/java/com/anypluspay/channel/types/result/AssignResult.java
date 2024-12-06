package com.anypluspay.channel.types.result;

import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import lombok.Data;

/**
 * 指定型结果，主要是人工场景会使用，如人工退款
 * @author wxj
 * 2024/9/15
 */
@Data
public class AssignResult extends ProcessResult {

    /**
     * 机构过程订单状态，当为指定结果时取该状态
     */
    private InstProcessOrderStatus processOrderStatus;

    /**
     * 机构订单状态，当为指定结果时取该状态
     */
    private InstOrderStatus orderStatus;
}
