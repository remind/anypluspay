package com.anypluspay.payment.application.notify;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.asset.external.ExternalResultService;
import com.anypluspay.payment.domain.biz.PaymentOrderService;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.service.FluxEngineService;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.general.GeneralPayService;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrderStatus;
import com.anypluspay.payment.domain.payorder.refund.RefundService;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付结果通知
 *
 * @author wxj
 * 2025/2/23
 */
@Service
public class PayNotifyService {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private ExternalResultService externalResultService;

    @Autowired
    private FluxEngineService fluxEngineService;

    @Autowired
    private GeneralPayService generalPayService;

    @Autowired
    private GeneralPayOrderRepository generalPayOrderRepository;

    @Autowired
    private RefundService refundService;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public void process(FundResult fundResult) {
        FluxOrder fluxOrder = fluxOrderRepository.loadByInstructionId(fundResult.getRequestId());
        if (fluxOrder == null) {
            return;
        }
        FluxInstruction fluxInstruction = fluxOrder.getInstructChain().find(fundResult.getRequestId()).getFluxInstruction();
        FluxResult result = externalResultService.process(fluxInstruction, fundResult);
        result.setExecuteInstruction(fluxInstruction);
        PayResult payResult = fluxEngineService.processByResult(fluxOrder, result);
        if (payResult.getPayStatus() == PayStatus.SUCCESS || payResult.getPayStatus() == PayStatus.FAIL) {
            IdType idType = idGeneratorService.getIdType(fluxOrder.getPayOrderId());
            if (idType == IdType.PAY_ORDER_ID) {
                GeneralPayOrder generalPayOrder = generalPayOrderRepository.load(fluxOrder.getPayOrderId());
                generalPayService.processFluxResult(generalPayOrder, payResult);

            } else if (idType == IdType.REFUND_ORDER_ID) {
                RefundOrder refundOrder = refundOrderRepository.load(fluxOrder.getPayOrderId());
                refundService.processFluxResult(refundOrder, payResult);

            }

        }
    }
}
