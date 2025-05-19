package com.anypluspay.payment.domain.biz;

import com.anypluspay.payment.domain.deposit.DepositService;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.domain.trade.TradeService;
import com.anypluspay.payment.domain.withdraw.WithdrawService;
import com.anypluspay.payment.types.IdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2025/5/19
 */
@Service
@Slf4j
public class PaymentOrderService {

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private WithdrawService withdrawService;

    public void processResult(String paymentId, boolean success) {
        log.info("支付业务单结果处理,paymentId={},success={}", paymentId, success);
        IdType bizOrderIdType = idGeneratorService.getIdType(paymentId);
        if (bizOrderIdType == IdType.DEPOSIT_ORDER_ID) {
            depositService.processResult(paymentId, success);
        } else if (bizOrderIdType == IdType.WITHDRAW_ORDER_ID) {
            withdrawService.processResult(paymentId, success);
        } else if (bizOrderIdType == IdType.TRADE_ORDER_ID) {
            tradeService.processResult(paymentId, success);
        }
    }
}
