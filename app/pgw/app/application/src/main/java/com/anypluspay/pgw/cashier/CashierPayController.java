package com.anypluspay.pgw.cashier;

import com.anypluspay.pgw.cashier.model.CashierPayResult;
import com.anypluspay.pgw.cashier.model.CashierType;
import com.anypluspay.pgw.cashier.request.PayRequest;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.TradeResponse;
import com.anypluspay.pgw.cashier.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 支付收银台
 *
 * @author wxj
 * 2025/4/2
 */
@Controller
@RequestMapping("/cashier/pay")
public class CashierPayController {

    @Autowired
    private AcquiringFacade acquiringFacade;

    @Autowired
    private CashierService cashierService;

    /**
     * 进入收银台
     *
     * @param tradeId
     * @return
     */
    @GetMapping
    public String cashier(@RequestParam String tradeId, Model model) {
        TradeResponse tradeResponse = acquiringFacade.queryByTradeId(tradeId);
        model.addAttribute("order", tradeResponse);
        model.addAttribute("payMethods", cashierService.buildPayMethods(CashierType.ACQUIRING, tradeResponse.getPayerId()));
        return "cashier/pay";
    }

    /**
     * 收银台提交支付
     *
     * @param request
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public ResponseResult<CashierPayResult> submit(@RequestBody PayRequest request) {
        return ResponseResult.success(cashierService.acquiringPay(request));
    }

}
