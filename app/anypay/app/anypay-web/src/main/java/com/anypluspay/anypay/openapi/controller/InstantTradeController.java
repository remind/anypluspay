package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.openapi.response.TradeOrderResponse;
import com.anypluspay.anypay.openapi.request.InstantCreateOrderRequest;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.commons.response.ResponseResult;
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
public class InstantTradeController extends AbstractTradeController {

    @PostMapping("/create")
    public ResponseResult<String> create(@RequestBody @Validated InstantCreateOrderRequest request) {
        TradeOrder tradeOrder = tradeBuilder.buildTradeOrder(request, TradeType.INSTANT_ACQUIRING);
        save(tradeOrder);
        return ResponseResult.success(tradeOrder.getTradeId());
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
        response.setTradeOrderId(tradeOrder.getTradeId());
        response.setOutTradeNo(tradeOrder.getOutTradeNo());
        response.setAmount(tradeOrder.getAmount().toString());
        response.setCurrency(tradeOrder.getAmount().getCurrency().getCurrencyCode());
        response.setStatus(tradeOrder.getStatus().getCode());
        return response;
    }
}
