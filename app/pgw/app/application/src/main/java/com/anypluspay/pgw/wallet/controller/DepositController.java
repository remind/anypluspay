package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.pgw.cashier.model.CashierPayResult;
import com.anypluspay.pgw.cashier.model.CashierType;
import com.anypluspay.pgw.cashier.request.DepositPayRequest;
import com.anypluspay.pgw.cashier.response.WebPayMethodResponse;
import com.anypluspay.pgw.cashier.service.CashierService;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 充值
 *
 * @author wxj
 * 2025/7/1
 */
@RestController
@RequestMapping("/wallet/deposit")
public class DepositController extends AbstractWalletController {

    @Autowired
    private CashierService cashierService;

    /**
     * 获取充值的支付方式
     *
     * @return
     */
    @GetMapping("/pay-methods")
    public ResponseResult<List<WebPayMethodResponse>> payMethods() {
        return ResponseResult.success(cashierService.buildPayMethods(CashierType.DEPOSIT, getLoginMember().getMemberId()));
    }

    /**
     * 提交充值
     *
     * @param request
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public ResponseResult<CashierPayResult> submit(@RequestBody DepositPayRequest request) {
        request.setPartnerId("100000003");
        request.setMemberId(getLoginMember().getMemberId());
        return ResponseResult.success(cashierService.depositPay(request));
    }
}
