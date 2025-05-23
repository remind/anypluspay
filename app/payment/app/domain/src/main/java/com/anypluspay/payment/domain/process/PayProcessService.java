package com.anypluspay.payment.domain.process;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Service;

/**
 * 支付指令处理
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class PayProcessService extends AbstractBaseProcessService {

    public PayResult process(PayProcess payProcess) {
        FluxOrder fluxOrder = buildFluxOrder(payProcess);

        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            payProcess.setStatus(PayProcessStatus.PAYING);
            payProcessRepository.reStore(payProcess);
        });

        return fluxEngineService.process(fluxOrder);
    }


}
