package com.anypluspay.payment.domain.payorder.service.impl;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.domain.payorder.service.AbstractBasePayService;
import com.anypluspay.payment.domain.payorder.service.PayOrderDomainService;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/1/27
 */
@Service
public class PayOrderDomainServiceImpl extends AbstractBasePayService implements PayOrderDomainService {

    @Override
    public PayResult pay(GeneralPayOrder generalPayOrder) {
        FluxOrder fluxOrder = buildFluxOrder(generalPayOrder);
        fluxOrderRepository.store(fluxOrder);
        PayResult payResult = fluxEngineService.process(fluxOrder);
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            generalPayOrder.setOrderStatus(PayOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                generalPayOrder.setOrderStatus(PayOrderStatus.SUCCESS);
                break;
            case FAIL:
                generalPayOrder.setOrderStatus(PayOrderStatus.FAIL);
                break;
            case PROCESS:
                generalPayOrder.setOrderStatus(PayOrderStatus.PAYING);
                break;
        }
        generalPayOrderRepository.reStore(generalPayOrder);
        return payResult;
    }
}
