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

    private String paymentId;

    private String relationPaymentId;
}
