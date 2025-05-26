package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.biz.PaymentOrderService;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 退款指令结果处理服务
 *
 * @author wxj
 * 2025/5/23
 */
@Service
public class RefundResultService {

    @Autowired
    private RefundProcessRepository refundProcessRepository;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected PayProcessRepository payProcessRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected PaymentOrderService paymentOrderService;

    /**
     * 处理 flux 结果
     *
     * @param processId 退款指令ID
     * @param payResult 支付结果
     */
    public void processFluxResult(String processId, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            RefundProcess refundProcess = refundProcessRepository.lock(processId);
            if (refundProcess.getStatus() == RefundOrderStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                convertStatus(refundProcess, payResult);
                refundProcessRepository.reStore(refundProcess);
                if (refundProcess.getStatus() == RefundOrderStatus.SUCCESS || refundProcess.getStatus() == RefundOrderStatus.FAIL) {
                    if (refundProcess.getRefundType() == RefundType.BIZ_REQUEST) {
                        // 仅支付业务请求的才处理业务结果
                        paymentOrderService.processResult(refundProcess.getPaymentId(), refundProcess.getProcessId(), refundProcess.getStatus() == RefundOrderStatus.SUCCESS);
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
    private void convertStatus(RefundProcess refundOrder, PayResult payResult) {
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
