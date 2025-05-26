package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.process.AbstractBaseProcessService;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款指令服务
 *
 * @author wxj
 * 2025/2/18
 */
@Service
public class RefundProcessService extends AbstractBaseProcessService {

    @Autowired
    private RefundProcessRepository refundProcessRepository;

    public PayResult process(RefundProcess refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setStatus(RefundOrderStatus.PAYING);
            refundProcessRepository.reStore(refundOrder);
        });

        return fluxEngineService.process(fluxOrder);
    }

}
