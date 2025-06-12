package com.anypluspay.pgw.cashier;

import com.anypluspay.pgw.cashier.model.CashierPayResult;
import com.anypluspay.pgw.cashier.model.CashierType;
import com.anypluspay.pgw.cashier.request.DepositCashierRequest;
import com.anypluspay.pgw.cashier.request.DepositPayRequest;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.pgw.cashier.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 充值收银台
 *
 * @author wxj
 * 2025/5/15
 */
@Controller
@RequestMapping("/cashier/deposit")
public class CashierDepositController {

    @Autowired
    private CashierService cashierService;

    /**
     * 进入收银台
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping
    public String cashier(@ModelAttribute DepositCashierRequest request, Model model) {
        model.addAttribute("request", request);
        model.addAttribute("payMethods", cashierService.buildPayMethods(CashierType.DEPOSIT, request.getMemberId()));
        return "cashier/deposit";
    }

    /**
     * 收银台提交支付
     *
     * @param request
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public ResponseResult<CashierPayResult> submit(@RequestBody DepositPayRequest request) {
        return ResponseResult.success(cashierService.depositPay(request));
    }

}
