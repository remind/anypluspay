package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.query.PaymentQueryFacade;
import com.anypluspay.payment.facade.query.response.TradeOrderResult;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 钱包支付
 *
 * @author wxj
 * 2025/7/3
 */
@Controller
@RequestMapping("/wallet/pay")
public class PayController extends AbstractWalletController {

    @Autowired
    private PaymentQueryFacade paymentQueryFacade;

    /**
     * 查询支付结果
     *
     * @param tradeId
     * @return
     */
    @RequestMapping("/query-result")
    @ResponseBody
    public ResponseResult<TradeOrderResult> queryResult(@RequestParam String tradeId) {
        return ResponseResult.success(paymentQueryFacade.queryByTradeId(tradeId));
    }
}
