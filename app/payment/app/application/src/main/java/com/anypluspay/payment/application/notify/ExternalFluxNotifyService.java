package com.anypluspay.payment.application.notify;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.asset.external.ExternalResultService;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.service.FluxEngineService;
import com.anypluspay.payment.domain.pay.pay.PayOrderService;
import com.anypluspay.payment.domain.pay.refund.RefundOrderService;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外部交换结果通知
 *
 * @author wxj
 * 2025/2/23
 */
@Service
public class ExternalFluxNotifyService {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private ExternalResultService externalResultService;

    @Autowired
    private FluxEngineService fluxEngineService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public void process(FundResult fundResult) {
        FluxOrder fluxOrder = fluxOrderRepository.loadByInstructionId(fundResult.getRequestId());
        if (fluxOrder == null) {
            return;
        }
        FluxProcess fluxProcess = fluxOrder.getFluxChain().find(fundResult.getRequestId()).getFluxProcess();
        FluxResult result = externalResultService.process(fluxProcess, fundResult);
        result.setExecuteInstruction(fluxProcess);
        PayResult payResult = fluxEngineService.processByResult(fluxOrder, result);
//
//        if (payResult.getPayStatus() == PayStatus.SUCCESS || payResult.getPayStatus() == PayStatus.FAIL) {
//            IdType idType = idGeneratorService.getIdType(fluxOrder.getPayProcessId());
//            if (idType == IdType.PAY_ORDER_ID) {
//                PayProcess payProcess = payProcessRepository.load(fluxOrder.getPayProcessId());
//                payProcessService.processFluxResult(payProcess.getProcessId(), payResult);
//            } else if (idType == IdType.REFUND_ORDER_ID) {
//                RefundProcess refundOrder = refundProcessRepository.load(fluxOrder.getPayProcessId());
//                refundProcessService.processFluxResult(refundOrder, payResult);
//            }
//
//        }
    }
}
