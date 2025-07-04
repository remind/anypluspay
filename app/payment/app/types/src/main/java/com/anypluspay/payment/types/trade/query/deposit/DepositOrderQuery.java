package com.anypluspay.payment.types.trade.query.deposit;

import com.anypluspay.payment.types.trade.query.BaseTradeQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/4
 */
@Data
public class DepositOrderQuery extends BaseTradeQuery {

    /**
     * 会员ID
     */
    private String memberId;
}
