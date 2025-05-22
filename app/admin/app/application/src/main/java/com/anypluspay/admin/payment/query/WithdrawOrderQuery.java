package com.anypluspay.admin.payment.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 提现搜索
 * @author wxj
 * 2025/5/19
 */
@Data
public class WithdrawOrderQuery extends PageQuery {

    /**
     * 支付业务单号
     */
    private String paymentId;
}
