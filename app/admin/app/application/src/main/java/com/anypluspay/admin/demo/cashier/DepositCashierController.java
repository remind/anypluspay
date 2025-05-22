package com.anypluspay.admin.demo.cashier;

import com.anypluspay.admin.demo.cashier.request.DepositCashierRequest;
import com.anypluspay.admin.demo.cashier.request.DepositPayRequest;
import com.anypluspay.commons.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 充值
 *
 * @author wxj
 * 2025/5/15
 */
@Controller
@RequestMapping("/demo/deposit-cashier")
public class DepositCashierController {

    @Autowired
    private CashierService cashierService;

    /**
     * 进入充值收银台
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping
    public String cashier(@ModelAttribute DepositCashierRequest request, Model model) {
        model.addAttribute("request", request);
        model.addAttribute("payMethods", cashierService.buildPayMethods(CashierType.DEPOSIT, request.getMemberId()));
        return "demo/cashier/deposit";
    }

    /**
     * 充值支付
     *
     * @param request
     * @return
     */
    @PostMapping("/pay")
    @ResponseBody
    public ResponseResult<Map<String, String>> paySubmit(@RequestBody DepositPayRequest request) {
        return ResponseResult.success(cashierService.depositPay(request));
    }

}
