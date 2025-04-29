package com.anypluspay.testtrade.facade;

import com.anypluspay.testtrade.facade.request.PayRequest;
import com.anypluspay.testtrade.facade.request.TradeRefundRequest;
import com.anypluspay.testtrade.facade.request.TradeRequest;
import com.anypluspay.testtrade.facade.response.PayResponse;
import com.anypluspay.testtrade.facade.response.RefundResponse;
import com.anypluspay.testtrade.facade.response.TradeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @param tradeRequest 交易信息
     * @return
     */
    @PostMapping(PREFIX + "/create")
    TradeResponse create(@RequestBody TradeRequest tradeRequest);

    /**
     * 交易支付
     *
     * @param payRequest 支付请求
     * @return
     */
    @PostMapping(PREFIX + "/pay")
    PayResponse pay(@RequestBody PayRequest payRequest);

    /**
     * 查询交易
     *
     * @param tradeId 交易号
     * @return
     */
    @GetMapping(PREFIX + "/query")
    TradeResponse query(@RequestParam String tradeId);

    /**
     * 退款
     *
     * @param tradeRefundRequest 退款请求
     * @return
     */
    @PostMapping(PREFIX + "/refund")
    RefundResponse refund(@RequestBody TradeRefundRequest tradeRefundRequest);

}
