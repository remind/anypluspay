package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.biz.withdraw.WithdrawOrder;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现处理
 *
 * @author wxj
 * 2025/5/15
 */
@RestController
public class WithdrawFacadeImpl extends AbstractPaymentService implements WithdrawFacade {

    @Autowired
    private WithdrawBuilder withdrawBuilder;

    @Autowired
    private WithdrawOrderRepository withdrawOrderRepository;

    @Override
    public WithdrawResponse apply(WithdrawRequest request) {
        WithdrawOrder withdrawOrder = withdrawBuilder.buildWithdrawOrder(request);
        GeneralPayOrder payOrder = withdrawBuilder.buildPayOrder(withdrawOrder);
        transactionTemplate.executeWithoutResult(status -> {
            withdrawOrderRepository.store(withdrawOrder);
            generalPayOrderRepository.store(payOrder);
        });
        PayResult result = generalPayService.process(payOrder);
        return processResult(withdrawOrder.getPaymentId(), result);
    }

    private WithdrawResponse processResult(String paymentId, PayResult result) {
        WithdrawOrder withdrawOrder = withdrawOrderRepository.load(paymentId);
        WithdrawResponse response = new WithdrawResponse();
        response.setPaymentId(paymentId);
        response.setStatus(withdrawOrder.getStatus().getCode());
        response.setResult(result);
        return response;
    }
}
