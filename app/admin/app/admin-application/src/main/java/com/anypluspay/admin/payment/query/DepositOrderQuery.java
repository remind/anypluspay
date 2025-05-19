package com.anypluspay.admin.payment.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 充值搜索
 * @author wxj
 * 2025/5/19
 */
@Data
public class DepositOrderQuery extends PageQuery {

    /**
     * 支付业务单号
     */
    private String paymentId;
}
