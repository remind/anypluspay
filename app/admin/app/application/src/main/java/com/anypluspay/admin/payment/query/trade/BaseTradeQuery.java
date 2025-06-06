package com.anypluspay.admin.payment.query.trade;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/6/6
 */
@Data
public class BaseTradeQuery extends PageQuery {

    /**
     * 交易ID
     */
    private String tradeId;
}
