package com.anypluspay.payment.facade.query;

import com.anypluspay.payment.facade.ApiConstants;
import com.anypluspay.payment.facade.query.response.TradeOrderResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付查询
 * @author wxj
 * 2025/7/3
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "PaymentQueryFacade")
public interface PaymentQueryFacade {

    String PREFIX = "/payment-query";

    /**
     * 查询
     *
     * @param tradeId
     * @return
     */
    @GetMapping(PREFIX + "/query-by-trade-id")
    TradeOrderResult queryByTradeId(@RequestParam String tradeId);
}
