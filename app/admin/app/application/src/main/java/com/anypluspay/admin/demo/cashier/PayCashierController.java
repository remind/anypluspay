package com.anypluspay.admin.demo.cashier;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.admin.demo.cashier.request.PayRequest;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.TradeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wxj
 * 2025/4/2
 */
@Controller
@RequestMapping("/demo/cashier")
public class PayCashierController {

    @Autowired
    private AcquiringFacade acquiringFacade;

    @Autowired
    private CashierService cashierService;

    /**
     * 收银台
     *
     * @param paymentId
     * @return
     */
    @GetMapping
    public String cashier(@RequestParam String paymentId, Model model) {
        TradeResponse tradeResponse = acquiringFacade.queryByPaymentId(paymentId);
        model.addAttribute("order", tradeResponse);
        model.addAttribute("payMethods", cashierService.buildPayMethods(CashierType.ACQUIRING, tradeResponse.getPayerId()));
        return "demo/cashier/pay";
    }

    /**
     * 支付
     *
     * @param request
     * @return
     */
    @PostMapping("/pay")
    @ResponseBody
    public ResponseResult<Map<String, String>> pay(@RequestBody PayRequest request) {
        return ResponseResult.success(cashierService.acquiringPay(request));
    }

}
