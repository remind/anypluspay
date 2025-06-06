package com.anypluspay.payment.domain.pay.pay;

import com.anypluspay.payment.domain.trade.TradeOrderService;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 支付单结果处理
 * @author wxj
 * 2025/5/23
 */
@Service
public class PayOrderResultService {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected TradeOrderService tradeOrderService;

    /**
     * 处理交换结果
     *
     * @param payId 支付单ID
     * @param payResult 交换结果
     */
    public void processFluxResult(String payId, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            PayOrder payOrder = payOrderRepository.lock(payId);
            if (payOrder.getStatus() == PayProcessStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                payOrder.setStatus(convertStatus(payResult.getPayStatus()));
                payOrder.setResultCode(payResult.getResultCode());
                payOrder.setResultMsg(payResult.getResultMessage());
                payOrderRepository.reStore(payOrder);
                if (payOrder.getStatus() == PayProcessStatus.SUCCESS || payOrder.getStatus() == PayProcessStatus.FAIL) {
                    tradeOrderService.processResult(payOrder.getTradeId(), payOrder.getOrderId(), payOrder.getStatus() == PayProcessStatus.SUCCESS);
                }
            }
        });
    }

    /**
     * 状态转换
     *
     * @param payStatus 支付状态
     * @return 支付订单状态
     */
    private PayProcessStatus convertStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> PayProcessStatus.SUCCESS;
            case FAIL -> PayProcessStatus.FAIL;
            case PROCESS -> PayProcessStatus.PAYING;
        };
    }
}
