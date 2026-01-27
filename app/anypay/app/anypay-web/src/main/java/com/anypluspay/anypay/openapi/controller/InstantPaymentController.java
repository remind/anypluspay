package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.application.trade.InstantTradeService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.openapi.response.TradeOrderResponse;
import com.anypluspay.anypay.types.request.UnifiedOrderRequest;
import com.anypluspay.commons.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 即时到账支付
 *
 * @author wxj
 * 2026/1/27
 */
@RestController
@RequestMapping("/openapi/instant")
@Validated
public class InstantPaymentController extends AbstractPaymentController {

    @Autowired
    private InstantTradeService instantTradeService;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @PostMapping("/create")
    public ResponseResult<String> create(@RequestBody @Validated UnifiedOrderRequest request) {
        return ResponseResult.success(instantTradeService.create(request));
    }

    @GetMapping("/close")
    public ResponseResult<String> close(@RequestParam String tradeOrderId) {
        return ResponseResult.success();
    }

    @GetMapping("/close/out-trade-no")
    public ResponseResult<String> closeByOutTradeNo(@RequestParam String outTradeNo) {
        return ResponseResult.success();
    }

    @GetMapping("/query")
    public ResponseResult<TradeOrderResponse> query(@RequestParam String tradeOrderId) {
        TradeOrder tradeOrder = tradeOrderRepository.load(tradeOrderId);
        return ResponseResult.success(buildTradeOrderResponse(tradeOrder));
    }

    @GetMapping("/query/out-trade-no")
    public ResponseResult<TradeOrderResponse> queryByOutTradeNo(@RequestParam String outTradeNo) {
        TradeOrder tradeOrder = tradeOrderRepository.loadByOutTradeNo(outTradeNo);
        return ResponseResult.success(buildTradeOrderResponse(tradeOrder));
    }

    private TradeOrderResponse buildTradeOrderResponse(TradeOrder tradeOrder) {
        TradeOrderResponse response = new TradeOrderResponse();
        response.setTradeOrderId(tradeOrder.getTradeOrderId());
        response.setOutTradeNo(tradeOrder.getOutTradeNo());
        response.setAmount(tradeOrder.getAmount().toString());
        response.setCurrency(tradeOrder.getAmount().getCurrency().getCurrencyCode());
        response.setStatus(tradeOrder.getStatus().getCode());
        return response;
    }
}
