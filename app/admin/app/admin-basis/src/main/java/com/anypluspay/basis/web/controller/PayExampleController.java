package com.anypluspay.basis.web.controller;

import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.commons.response.ResponseResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2024/11/26
 */
@RestController
@RequestMapping("/pay-example")
public class PayExampleController {

    @DubboReference
    private FundInFacade fundInFacade;

    @DubboReference
    private RefundFacade refundFacade;

    @GetMapping("/gen-order")
    public ResponseResult<FundInRequest> orderInfo() {
        FundInRequest request = new FundInRequest();
        return ResponseResult.success(request);
    }

    @PostMapping("/pay")
    public ResponseResult<FundResult> pay(FundInRequest fundInRequest) {
        return ResponseResult.success(fundInFacade.apply(fundInRequest));
    }

    @PostMapping("/refund")
    public ResponseResult<FundResult> refund(RefundRequest request) {
        return ResponseResult.success(refundFacade.apply(request));
    }
}
