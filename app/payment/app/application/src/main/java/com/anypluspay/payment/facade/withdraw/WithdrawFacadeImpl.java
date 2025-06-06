package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
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
        PayOrder payOrder = withdrawBuilder.buildPayProcess(withdrawOrder);
        transactionTemplate.executeWithoutResult(status -> {
            withdrawOrderRepository.store(withdrawOrder);
            payOrderRepository.store(payOrder);
        });
        PayResult result = payOrderService.process(payOrder);
        return processResult(withdrawOrder.getTradeId(), result);
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
