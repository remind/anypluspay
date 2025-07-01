package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.facade.trade.TradeBillFacade;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import com.anypluspay.pgw.wallet.convertor.TradeBillConvertor;
import com.anypluspay.pgw.wallet.response.TradeBillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易相关
 *
 * @author wxj
 * 2025/7/1
 */
@RestController
@RequestMapping("/wallet/trade")
public class TradeController extends AbstractWalletController {

    @Autowired
    private TradeBillFacade tradeBillFacade;

    @Autowired
    private TradeBillConvertor tradeBillConvertor;

    /**
     * 账单查询
     * @param query
     * @return
     */
    @GetMapping("/bill")
    public ResponseResult<PageResult<TradeBillResponse>> query(TradeBillQuery query) {
        query.setMemberId(getLoginMember().getMemberId());
        return ResponseResult.success(tradeBillConvertor.convert(tradeBillFacade.query(query)));
    }
}
