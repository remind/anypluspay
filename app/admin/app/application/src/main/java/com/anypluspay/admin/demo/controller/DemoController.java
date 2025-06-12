package com.anypluspay.admin.demo.controller;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.admin.core.SystemConfig;
import com.anypluspay.admin.demo.response.PayResponse;
import com.anypluspay.commons.lang.utils.StringUtil;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.types.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付示例
 *
 * @author wxj
 * 2025/3/28
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private AcquiringFacade acquiringFacade;

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 创建交易订单
     *
     * @param request
     * @return
     */
    @PostMapping("/create-trade-order")
    public ResponseResult<PayResponse> createTradeOrder(@RequestBody AcquiringCreateRequest request) {
        request.setTradeType(TradeType.INSTANT_ACQUIRING.getCode());
        request.setOutTradeNo(StringUtil.randomId());
        request.setPayeeAccountNo(getBaseAccountNo(request.getPayeeId()));
        PayResponse payResponse = new PayResponse();
        payResponse.setTrade(acquiringFacade.create(request));
        payResponse.setCashierUrl(systemConfig.getPgwAddress()  + "/cashier/pay?tradeId=" + payResponse.getTrade().getTradeId());
        return ResponseResult.success(payResponse);
    }

    /**
     * 获取充值地址
     * @return
     */
    @GetMapping("/get-deposit-address")
    @ResponseBody
    public ResponseResult<String> getDepositAddress() {
        return ResponseResult.success(systemConfig.getPgwAddress() + "/cashier/deposit");
    }

    private String getBaseAccountNo(String memberId) {
        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(memberId, "101");
        return accountResponse.getAccountNo();
    }

}
