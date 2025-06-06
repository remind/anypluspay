package com.anypluspay.payment.domain.pay.pay;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.pay.AbstractPayService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Service;

/**
 * 支付单处理
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class PayOrderService extends AbstractPayService {

    public PayResult process(PayOrder payOrder) {
        FluxOrder fluxOrder = buildFluxOrder(payOrder);

        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            payOrder.setStatus(PayProcessStatus.PAYING);
            payOrderRepository.reStore(payOrder);
        });

        return fluxEngineService.process(fluxOrder);
    }


}
