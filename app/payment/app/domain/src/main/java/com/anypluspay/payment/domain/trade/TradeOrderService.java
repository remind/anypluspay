package com.anypluspay.payment.domain.trade;

import com.anypluspay.payment.domain.trade.deposit.DepositOrderService;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrderService;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrderService;
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
public class TradeOrderService {

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private AcquiringOrderService acquiringOrderService;

    @Autowired
    private DepositOrderService depositOrderService;

    @Autowired
    private WithdrawOrderService withdrawOrderService;

    public void processResult(String paymentId, String orderId, boolean success) {
        log.info("支付业务单结果处理,paymentId={},success={}", paymentId, success);
        IdType bizOrderIdType = idGeneratorService.getIdType(paymentId);
        if (bizOrderIdType == IdType.DEPOSIT_ORDER_ID) {
            depositOrderService.processResult(paymentId, success);
        } else if (bizOrderIdType == IdType.WITHDRAW_ORDER_ID) {
            withdrawOrderService.processResult(paymentId, success);
        } else if (bizOrderIdType == IdType.TRADE_ORDER_ID) {
            acquiringOrderService.processResult(paymentId, orderId, success);
        }
    }
}
