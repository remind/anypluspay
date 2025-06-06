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

    public void processResult(String tradeId, String orderId, boolean success) {
        log.info("支付业务单结果处理,tradeId={},success={}", tradeId, success);
        IdType bizOrderIdType = idGeneratorService.getIdType(tradeId);
        if (bizOrderIdType == IdType.DEPOSIT_ORDER_ID) {
            depositOrderService.processResult(tradeId, success);
        } else if (bizOrderIdType == IdType.WITHDRAW_ORDER_ID) {
            withdrawOrderService.processResult(tradeId, success);
        } else if (bizOrderIdType == IdType.ACQUIRING_ORDER_ID) {
            acquiringOrderService.processResult(tradeId, orderId, success);
        }
    }
}
