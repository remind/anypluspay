package com.anypluspay.payment.domain.payorder.service.impl;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.engine.FluxEngineService;
import com.anypluspay.payment.domain.payorder.PayOrder;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.domain.payorder.service.AbstractBasePayService;
import com.anypluspay.payment.domain.payorder.service.PayOrderDomainService;
import com.anypluspay.payment.domain.repository.BasePayOrderRepository;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/1/27
 */
@Service
public class PayOrderDomainServiceImpl extends AbstractBasePayService implements PayOrderDomainService {

    @Autowired
    private FluxEngineService fluxEngineService;

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private BasePayOrderRepository basePayOrderRepository;
    @Override
    public PayResult pay(PayOrder payOrder) {
        FluxOrder fluxOrder = buildFluxOrder(payOrder);
        fluxOrderRepository.store(fluxOrder);
        PayResult payResult = fluxEngineService.process(fluxOrder);
        if (payResult.getPayStatus() == PayStatus.SUCCESS) {
            payOrder.setOrderStatus(PayOrderStatus.SUCCESS);
        }
        switch (payResult.getPayStatus()) {
            case SUCCESS:
                payOrder.setOrderStatus(PayOrderStatus.SUCCESS);
                break;
            case FAIL:
                payOrder.setOrderStatus(PayOrderStatus.FAIL);
                break;
            case PROCESS:
                payOrder.setOrderStatus(PayOrderStatus.PAYING);
                break;
        }
        basePayOrderRepository.reStore(payOrder);
        return payResult;
    }
}
