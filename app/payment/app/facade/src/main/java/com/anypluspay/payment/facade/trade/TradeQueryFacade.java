package com.anypluspay.payment.facade.trade;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.facade.ApiConstants;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 交易查询
 *
 * @author wxj
 * 2025/6/29
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "TradeQueryFacade")
public interface TradeQueryFacade {

    String PREFIX = "/trade-query";

    /**
     * 查询账单
     *
     * @param query
     * @return
     */
    @GetMapping(PREFIX + "/page-query-bill")
    PageResult<TradeBillDto> pageQueryBill(@SpringQueryMap TradeBillQuery query);

    /**
     * 查询充值订单
     *
     * @param query
     * @return
     */
    @GetMapping(PREFIX + "/page-query-deposit")
    PageResult<DepositOrderResponse> pageQueryDeposit(@SpringQueryMap DepositOrderQuery query);

}
