package com.anypluspay.payment.facade.trade;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.domain.repository.TradeBillQueryRepository;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2025/6/29
 */
@RestController
public class TradeBillFacadeImpl implements TradeBillFacade {

    @Autowired
    private TradeBillQueryRepository tradeBillQueryRepository;

    @Override
    public PageResult<TradeBillDto> query(TradeBillQuery query) {
        return tradeBillQueryRepository.queryBill( query);
    }
}
