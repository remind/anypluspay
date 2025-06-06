package com.anypluspay.payment.domain.pay.refund;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.pay.AbstractPayService;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款单服务
 *
 * @author wxj
 * 2025/2/18
 */
@Service
public class RefundOrderService extends AbstractPayService {

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    public PayResult process(RefundOrder refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setStatus(RefundOrderStatus.PAYING);
            refundOrderRepository.reStore(refundOrder);
        });

        return fluxEngineService.process(fluxOrder);
    }

}
