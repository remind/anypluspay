package com.anypluspay.payment.domain.process.refund;

import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.process.AbstractBaseProcessService;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款申请指令处理服务
 *
 * @author wxj
 * 2025/5/23
 */
@Service
public class RefundApplyService extends AbstractBaseProcessService {

    @Autowired
    private RefundProcessRepository refundProcessRepository;

    @Autowired
    private RefundResultService refundResultService;

    /**
     * 申请退款
     *
     * @param paymentId    支付单ID
     * @param refundAmount 退款金额
     * @param payProcess   原支付指令
     */
    public PayResult apply(RefundProcess refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setStatus(RefundOrderStatus.PAYING);
            refundProcessRepository.reStore(refundOrder);
        });

        return fluxEngineService.process(fluxOrder);
    }

    /**
     * 处理退款指令
     * @param fluxOrder
     * @return
     */
    public PayResult process(FluxOrder fluxOrder) {
        PayResult payResult = fluxEngineService.process(fluxOrder);
        refundResultService.processFluxResult(fluxOrder.getPayProcessId(), payResult);
        return payResult;
    }


}
