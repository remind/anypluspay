package com.anypluspay.admin.payment.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 收单订单查询
 * @author wxj
 * 2025/5/19
 */
@Data
public class AcquiringOrderQuery extends PageQuery {

    /**
     * 支付业务单号
     */
    private String paymentId;

    /**
     * 关联支付业务单号
     */
    private String relationPaymentId;
}
