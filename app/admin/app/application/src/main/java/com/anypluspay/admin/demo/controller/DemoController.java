package com.anypluspay.admin.demo.controller;

import cn.hutool.core.lang.UUID;
import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.types.biz.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
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

    /**
     * 创建交易订单
     *
     * @param request
     * @return
     */
    @PostMapping("/create-trade-order")
    public ResponseResult<AcquiringCreateResponse> createTradeOrder(@RequestBody AcquiringCreateRequest request) {
        request.setTradeType(TradeType.INSTANT_ACQUIRING.getCode());
        request.setOutTradeNo(UUID.randomUUID().toString(true));
        request.setPayeeAccountNo(getBaseAccountNo(request.getPayeeId()));
        return ResponseResult.success(acquiringFacade.create(request));
    }

    private String getBaseAccountNo(String memberId) {
        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(memberId, "101");
        return accountResponse.getAccountNo();
    }

}
