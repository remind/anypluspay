package com.anypluspay.payment.facade.instant.builder;

import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.payment.types.asset.BelongTo;
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

    public GeneralPayOrder buildPayOrder(String paymentId, InstantPaymentRequest request) {
        GeneralPayOrder generalPayOrder = new GeneralPayOrder();
        generalPayOrder.setPaymentId(paymentId);
        generalPayOrder.setOrderId(idGeneratorService.genIdByRelateId(paymentId, PayOrderType.PAY.getIdType()));
        generalPayOrder.setRequestId(request.getRequestId());
        generalPayOrder.setAmount(request.getPayAmount());
        generalPayOrder.setMemberId(request.getPayerId());
        generalPayOrder.setOrderStatus(GeneralPayOrderStatus.INIT);
        fillFundDetails(generalPayOrder, request);
        return generalPayOrder;
    }

    /**
     * 填充资金明细
     * @param generalPayOrder
     * @param request
     */
    private void fillFundDetails(GeneralPayOrder generalPayOrder, InstantPaymentRequest request) {
        request.getPayerFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayerFundDetail(buildFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getOrderId(), fundDetailInfo, BelongTo.PAYER));
        });

        request.getPayeeFundDetail().forEach(fundDetailInfo -> {
            generalPayOrder.addPayeeFundDetail(buildFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getOrderId(), fundDetailInfo, BelongTo.PAYEE));
        });
    }

}
