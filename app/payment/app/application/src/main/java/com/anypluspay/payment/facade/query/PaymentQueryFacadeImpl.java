package com.anypluspay.payment.facade.query;

import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.repository.DepositOrderRepository;
import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.trade.deposit.DepositOrder;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
import com.anypluspay.payment.facade.query.response.TradeOrderResult;
import com.anypluspay.payment.types.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2025/7/3
 */
@RestController
public class PaymentQueryFacadeImpl implements PaymentQueryFacade {

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    private WithdrawOrderRepository withdrawOrderRepository;

    @Override
    public TradeOrderResult queryByTradeId(String tradeId) {
        TradeOrderResult tradeOrderResult = new TradeOrderResult();
        tradeOrderResult.setTradeId(tradeId);
        IdType bizOrderIdType = idGeneratorService.getIdType(tradeId);
        if (bizOrderIdType == IdType.DEPOSIT_ORDER_ID) {
            fillDepositOrderResult(tradeOrderResult);
        } else if (bizOrderIdType == IdType.WITHDRAW_ORDER_ID) {
            fillWithdrawOrderResult(tradeOrderResult);
        } else if (bizOrderIdType == IdType.ACQUIRING_ORDER_ID) {
            fillAcquiringOrderResult(tradeOrderResult);
        }
        return tradeOrderResult;
    }

    private void fillDepositOrderResult(TradeOrderResult tradeOrderResult) {
        DepositOrder depositOrder = depositOrderRepository.load(tradeOrderResult.getTradeId());
        tradeOrderResult.setOrderStatus(depositOrder.getStatus().getCode());
    }

    private void fillAcquiringOrderResult(TradeOrderResult tradeOrderResult) {
        AcquiringOrder acquiringOrder = acquiringOrderRepository.load(tradeOrderResult.getTradeId());
        tradeOrderResult.setOrderStatus(acquiringOrder.getStatus().getCode());
    }

    private void fillWithdrawOrderResult(TradeOrderResult tradeOrderResult) {
        WithdrawOrder withdrawOrder = withdrawOrderRepository.load(tradeOrderResult.getTradeId());
        tradeOrderResult.setOrderStatus(withdrawOrder.getStatus().getCode());
    }
}
