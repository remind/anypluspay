package com.anypluspay.testtrade.facade;

import com.anypluspay.testtrade.facade.request.PayRequest;
import com.anypluspay.testtrade.facade.request.TradeInfo;
import com.anypluspay.testtrade.facade.response.PayResponse;
import com.anypluspay.testtrade.facade.response.TradeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 交易
 *
 * @author wxj
 * 2025/3/18
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "TradeFacade")
public interface TradeFacade {

    String PREFIX = "/trade";

    /**
     * 创建交易
     *
     * @param tradeInfo 交易信息
     * @return
     */
    @PostMapping(PREFIX + "/create")
    TradeResponse create(@RequestBody TradeInfo tradeInfo);


    /**
     * 交易支付
     *
     * @param payRequest 支付请求
     * @return
     */
    @PostMapping(PREFIX + "/pay")
    PayResponse pay(@RequestBody PayRequest payRequest);

}
