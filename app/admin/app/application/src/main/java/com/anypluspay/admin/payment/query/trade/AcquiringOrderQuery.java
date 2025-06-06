package com.anypluspay.admin.payment.query.trade;

import lombok.Data;

/**
 * 收单订单查询
 * @author wxj
 * 2025/5/19
 */
@Data
public class AcquiringOrderQuery extends BaseTradeQuery {

    /**
     * 关联支付业务单号
     */
    private String relationTradeId;
}
