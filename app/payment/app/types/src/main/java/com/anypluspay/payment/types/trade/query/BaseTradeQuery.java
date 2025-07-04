package com.anypluspay.payment.types.trade.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/4
 */
@Data
public class BaseTradeQuery extends PageQuery {

    /**
     * 交易ID
     */
    private String tradeId;
}