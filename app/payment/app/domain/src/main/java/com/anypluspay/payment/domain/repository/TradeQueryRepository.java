package com.anypluspay.payment.domain.repository;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;

/**
 * 交易查询
 * @author wxj
 * 2025/7/1
 */
public interface TradeQueryRepository {

    PageResult<TradeBillDto> queryBill(TradeBillQuery query);

    PageResult<DepositOrderResponse> pageQueryDeposit(DepositOrderQuery query);
}
