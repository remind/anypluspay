package com.anypluspay.payment.domain.process;

import com.anypluspay.payment.domain.biz.PaymentOrderService;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 支付指令结果处理
 * @author wxj
 * 2025/5/23
 */
@Service
public class PayResultService {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected PayProcessRepository payProcessRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected PaymentOrderService paymentOrderService;

    /**
     * 处理交换结果
     *
     * @param processId 支付单ID
     * @param payResult 交换结果
     */
    public void processFluxResult(String processId, PayResult payResult) {
        transactionTemplate.executeWithoutResult(status -> {
            PayProcess payProcess = payProcessRepository.lock(processId);
            if (payProcess.getStatus() == PayProcessStatus.PAYING) {
                // 仅支付中状态才处理结果，防止重复处理
                payProcess.setStatus(convertStatus(payResult.getPayStatus()));
                payProcess.setResultCode(payResult.getResultCode());
                payProcess.setResultMsg(payResult.getResultMessage());
                payProcessRepository.reStore(payProcess);
                if (payProcess.getStatus() == PayProcessStatus.SUCCESS || payProcess.getStatus() == PayProcessStatus.FAIL) {
                    paymentOrderService.processResult(payProcess.getPaymentId(), payProcess.getStatus() == PayProcessStatus.SUCCESS);
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
