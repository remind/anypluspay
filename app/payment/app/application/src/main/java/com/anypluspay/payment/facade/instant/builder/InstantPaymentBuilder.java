package com.anypluspay.payment.facade.instant.builder;

import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/1/15
 */
@Component
public class InstantPaymentBuilder extends PaymentBuilder {

    public Payment buildPayment(InstantPaymentRequest request) {
        return buildPayment(request, PaymentType.INSTANT);
    }

    public PayProcess buildPayProcess(String paymentId, InstantPaymentRequest request) {
        PayProcess payProcess = new PayProcess();
        payProcess.setPaymentId(paymentId);
        payProcess.setProcessId(idGeneratorService.genIdByRelateId(paymentId, PayOrderType.PAY.getIdType()));
        payProcess.setAmount(request.getPayAmount());
        payProcess.setMemberId(request.getPayerId());
        payProcess.setStatus(PayProcessStatus.INIT);
        fillFundDetails(payProcess, request);
        return payProcess;
    }

    /**
     * 填充资金明细
     * @param generalPayOrder
     * @param request
     */
    private void fillFundDetails(PayProcess generalPayOrder, InstantPaymentRequest request) {
        request.getPayerFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayerFundDetail(buildFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getProcessId(), fundDetailInfo, BelongTo.PAYER));
        });

        request.getPayeeFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayeeFundDetail(buildFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getProcessId(), fundDetailInfo, BelongTo.PAYEE));
        });
    }

}
