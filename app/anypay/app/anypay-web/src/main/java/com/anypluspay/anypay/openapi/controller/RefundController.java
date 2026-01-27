package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.openapi.request.RefundApplyRequest;
import com.anypluspay.anypay.openapi.response.RefundResponse;
import com.anypluspay.commons.response.ResponseResult;
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

    @PostMapping("/apply")
    public ResponseResult<RefundResponse> apply(@RequestBody RefundApplyRequest request) {
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
