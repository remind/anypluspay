package com.anypluspay.payment.domain.repository;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;

/**
 * @author wxj
 * 2025/7/1
 */
public interface TradeBillQueryRepository {

    PageResult<TradeBillDto> queryBill(TradeBillQuery query);
}
