package com.anypluspay.admin.basis.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.testtrade.facade.TradeFacade;
import com.anypluspay.testtrade.facade.request.PayRequest;
import com.anypluspay.testtrade.facade.request.TradeInfo;
import com.anypluspay.testtrade.facade.response.PayResponse;
import com.anypluspay.testtrade.facade.response.TradeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 * @author wxj
 * 2025/3/28
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private TradeFacade tradeFacade;


    /**
     * 创建交易订单
     * @param tradeInfo
     * @return
     */
    @PostMapping("/create-trade-order")
    public ResponseResult<TradeResponse> createTradeOrder(@RequestBody TradeInfo tradeInfo) {
        return ResponseResult.success(tradeFacade.create(tradeInfo));
    }

    /**
     * 支付
     * @param payRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult<PayResponse> pay(@RequestBody PayRequest payRequest) {
        return ResponseResult.success(tradeFacade.pay(payRequest));
    }

}
