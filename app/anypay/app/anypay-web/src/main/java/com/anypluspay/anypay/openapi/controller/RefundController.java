package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.application.trade.RefundService;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.application.trade.request.RefundApplyRequest;
import com.anypluspay.anypay.openapi.response.RefundResponse;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wxj
 * 2026/1/27
 */
@RestController
@RequestMapping("/openapi/refund")
@Validated
public class RefundController {

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private RefundService refundService;

    @PostMapping("/apply")
    public ResponseResult<RefundResponse> apply(@RequestBody RefundApplyRequest request) {
        refundService.apply(request);
        return ResponseResult.success(new RefundResponse());
    }

    @PostMapping("/query")
    public ResponseResult<RefundResponse> query(@RequestBody String tradeOrderId) {
        return ResponseResult.success(new RefundResponse());
    }

    @GetMapping("/query/out-trade-no")
    public ResponseResult<RefundResponse> queryByOutTradeNo(@RequestParam String outTradeNo) {
        return ResponseResult.success(new RefundResponse());
    }


}
