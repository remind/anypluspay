package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.facade.trade.TradeQueryFacade;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderQuery;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;
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
    private TradeQueryFacade tradeQueryFacade;

    @Autowired
    private TradeBillConvertor tradeBillConvertor;

    /**
     * 账单查询
     *
     * @param query
     * @return
     */
    @GetMapping("/bill")
    public ResponseResult<PageResult<TradeBillResponse>> query(TradeBillQuery query) {
        query.setMemberId(getLoginMember().getMemberId());
        return ResponseResult.success(tradeBillConvertor.convert(tradeQueryFacade.pageQueryBill(query)));
    }

    /**
     * 充值查询
     *
     * @param query
     * @return
     */
    @GetMapping("/deposit-list")
    public ResponseResult<PageResult<DepositOrderResponse>> depositList(DepositOrderQuery query) {
        query.setMemberId(getLoginMember().getMemberId());
        return ResponseResult.success(tradeQueryFacade.pageQueryDeposit(query));
    }
}
