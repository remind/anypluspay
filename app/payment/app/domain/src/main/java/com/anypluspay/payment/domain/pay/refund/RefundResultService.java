package com.anypluspay.payment.domain.pay.refund;

import com.anypluspay.payment.domain.trade.TradeOrderService;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 退款结果处理服务
 *
 * @author wxj
 * 2025/5/23
 */
@Service
public class RefundResultService {

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected TradeOrderService tradeOrderService;

    /**
     * 处理 flux 结果
     *
     * @param processId 退款指令ID
     * @param payResult 支付结果
     */
    public void processFluxResult(String processId, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            RefundOrder refundProcess = refundOrderRepository.lock(processId);
            if (refundProcess.getStatus() == RefundOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                convertStatus(refundProcess, payResult);
                refundOrderRepository.reStore(refundProcess);
                if (refundProcess.getStatus() == RefundOrderStatus.SUCCESS || refundProcess.getStatus() == RefundOrderStatus.FAIL) {
                    if (refundProcess.getRefundType() == RefundType.BIZ_REQUEST) {
                        // 仅支付业务请求的才处理业务结果
                        tradeOrderService.processResult(refundProcess.getTradeId(), refundProcess.getOrderId(), refundProcess.getStatus() == RefundOrderStatus.SUCCESS);
                    }
                }
            }
        });
    }

    /**
     * 状态转换
     *
     * @param refundOrder 退款订单
     * @param payResult   支付结果
     */
    private void convertStatus(RefundOrder refundOrder, PayResult payResult) {
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            refundOrder.setStatus(RefundOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                refundOrder.setStatus(RefundOrderStatus.SUCCESS);
                break;
            case FAIL:
                refundOrder.setStatus(RefundOrderStatus.FAIL);
                break;
            case PROCESS:
                refundOrder.setStatus(RefundOrderStatus.PAYING);
                break;
        }
    }
}
