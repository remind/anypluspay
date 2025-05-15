package com.anypluspay.admin.demo.cashier;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.testtrade.facade.TradeFacade;
import com.anypluspay.testtrade.facade.request.PayRequest;
import com.anypluspay.testtrade.facade.response.PayResponse;
import com.anypluspay.testtrade.facade.response.TradeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author wxj
 * 2025/4/2
 */
@Controller
@RequestMapping("/demo/cashier")
public class PayCashierController {

    @Autowired
    private TradeFacade tradeFacade;

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    /**
     * 收银台
     *
     * @param tradeId
     * @return
     */
    @GetMapping
    public String cashier(@RequestParam String tradeId, Model model) {
        TradeResponse tradeResponse = tradeFacade.query(tradeId);
        model.addAttribute("trade", tradeResponse);

        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(tradeResponse.getPayerId(), "101");
        model.addAttribute("availableBalance", accountResponse.getAvailableBalance());

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
    public ResponseResult<PayResponse> pay(@RequestBody PayRequest request) {
        PayResponse response = tradeFacade.pay(request);
        return ResponseResult.success(response);
    }
}
