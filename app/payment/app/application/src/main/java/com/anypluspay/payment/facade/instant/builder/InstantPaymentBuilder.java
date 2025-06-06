package com.anypluspay.payment.facade.instant.builder;

import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
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

    public PayOrder buildPayProcess(String paymentId, InstantPaymentRequest request) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(paymentId);
        payOrder.setOrderId(idGeneratorService.genIdByRelateId(paymentId, PayOrderType.PAY.getIdType()));
        payOrder.setAmount(request.getPayAmount());
        payOrder.setMemberId(request.getPayerId());
        payOrder.setStatus(PayProcessStatus.INIT);
        fillFundDetails(payOrder, request);
        return payOrder;
    }

    /**
     * 填充资金明细
     * @param generalPayOrder
     * @param request
     */
    private void fillFundDetails(PayOrder generalPayOrder, InstantPaymentRequest request) {
        request.getPayerFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayerFundDetail(buildFundDetail(generalPayOrder.getTradeId(), generalPayOrder.getOrderId(), fundDetailInfo, BelongTo.PAYER));
        });

        request.getPayeeFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayeeFundDetail(buildFundDetail(generalPayOrder.getTradeId(), generalPayOrder.getOrderId(), fundDetailInfo, BelongTo.PAYEE));
        });
    }

}
