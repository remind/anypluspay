package com.anypluspay.admin.demo.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.withdraw.WithdrawFacade;
import com.anypluspay.payment.facade.withdraw.WithdrawRequest;
import com.anypluspay.payment.facade.withdraw.WithdrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现
 * @author wxj
 * 2025/5/16
 */
@RestController
@RequestMapping("/demo/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawFacade withdrawFacade;

    @PostMapping("/apply")
    public ResponseResult<WithdrawResponse> apply(@RequestBody WithdrawRequest request) {
        return ResponseResult.success(withdrawFacade.apply(request));
    }
}
