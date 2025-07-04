package com.anypluspay.payment.facade.trade;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.domain.repository.TradeQueryRepository;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2025/6/29
 */
@RestController
public class TradeQueryFacadeImpl implements TradeQueryFacade {

    @Autowired
    private TradeQueryRepository tradeQueryRepository;

    @Override
    public PageResult<TradeBillDto> pageQueryBill(TradeBillQuery query) {
        return tradeQueryRepository.queryBill(query);
    }

    @Override
    public PageResult<DepositOrderResponse> pageQueryDeposit(DepositOrderQuery query) {
        return tradeQueryRepository.pageQueryDeposit(query);
    }
}
