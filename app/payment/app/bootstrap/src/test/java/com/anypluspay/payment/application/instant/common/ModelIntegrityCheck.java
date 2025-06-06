package com.anypluspay.payment.application.instant.common;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.Payment;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.pay.refund.RefundOrder;
import com.anypluspay.payment.types.status.PayProcessStatus;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.PaymentRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 模型完整型校验
 *
 * @author wxj
 * 2025/2/28
 */
@Service
public class ModelIntegrityCheck {

    @Autowired
    protected PaymentRepository paymentRepository;

    @Autowired
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected RefundOrderRepository refundOrderRepository;

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    public void checkInstantPayment(String paymentId) {
        Payment payment = paymentRepository.load(paymentId);
        Assert.assertNotNull(payment);
        Assert.assertEquals(PaymentType.INSTANT, payment.getPaymentType());
        Assert.assertNotNull(payment.getPaymentId());
        Assert.assertNotNull(payment.getMemberId());
        Assert.assertNotNull(payment.getMerchantId());

        List<PayOrder> payOrders = payOrderRepository.loadByTradeId(paymentId);
        Assert.assertNotNull(payOrders);
        Assert.assertEquals(1, payOrders.size());
        checkGeneralPayOrder(paymentId, payOrders.get(0));
    }

    public void checkGeneralPayOrder(String paymentId, PayOrder generalPayOrder) {
        Assert.assertNotNull(generalPayOrder);
        Assert.assertNotNull(generalPayOrder.getTradeId());
        Assert.assertNotNull(generalPayOrder.getOrderId());
        Assert.assertNotNull(generalPayOrder.getStatus());
        Assert.assertNotNull(generalPayOrder.getAmount());
        Assert.assertNotNull(generalPayOrder.getMemberId());
        Assert.assertNotNull(generalPayOrder.getGmtCreate());
        Assert.assertNotNull(generalPayOrder.getGmtModified());

        Assert.assertEquals(paymentId, generalPayOrder.getTradeId());
        checkFundDetail(generalPayOrder.getTradeId(), generalPayOrder.getOrderId(), generalPayOrder.getAmount(), generalPayOrder.getPayerDetails(), generalPayOrder.getPayeeDetails());
        checkRefundOrder(generalPayOrder);
    }

    public void checkRefundOrder(PayOrder generalPayOrder) {
        List<RefundOrder> refundOrders = refundOrderRepository.loadByOrigPayOrderId(generalPayOrder.getOrderId());
        if (!CollectionUtils.isEmpty(refundOrders)) {
            Assert.assertEquals(generalPayOrder.getStatus(), PayProcessStatus.SUCCESS);
            refundOrders.forEach(refundOrder -> {
                checkSingleRefundOrder(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder);
                checkFundDetail(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());
            });
            Money refundMount = refundOrders.stream().filter(ro -> ro.getStatus() != RefundOrderStatus.FAIL).map(RefundOrder::getAmount).reduce(new Money(), Money::add);
            Assert.assertFalse(refundMount.greaterThan(generalPayOrder.getAmount()));
        }
    }

    public void checkSingleRefundOrder(String paymentId, String orderId, RefundOrder refundOrder) {
        Assert.assertNotNull(refundOrder);
        Assert.assertNotNull(refundOrder.getTradeId());
        Assert.assertNotNull(refundOrder.getOrderId());
        Assert.assertNotNull(refundOrder.getStatus());
        Assert.assertNotNull(refundOrder.getAmount());
        Assert.assertNotNull(refundOrder.getMemberId());
        Assert.assertNotNull(refundOrder.getRelationId());
        Assert.assertNotNull(refundOrder.getGmtCreate());
        Assert.assertNotNull(refundOrder.getGmtModified());

        Assert.assertEquals(paymentId, refundOrder.getTradeId());
        Assert.assertEquals(orderId, refundOrder.getOrderId());
        checkFundDetail(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());


    }

    public void checkFundDetail(String paymentId, String orderId, Money totalAmount, List<FundDetail> payerFundDetails, List<FundDetail> payeeFundDetails) {
        Assert.assertNotNull(payerFundDetails);
        Assert.assertNotNull(payeeFundDetails);

        Money payerAmount = new Money(0, payerFundDetails.get(0).getAmount().getCurrency());
        Money payeeAmount = new Money(0, payeeFundDetails.get(0).getAmount().getCurrency());
        payerFundDetails.forEach(fundDetail -> {
            checkSingleFundDetail(paymentId, orderId, fundDetail);
            payerAmount.addTo(fundDetail.getAmount());
            Assert.assertEquals(BelongTo.PAYER, fundDetail.getBelongTo());
            Assert.assertEquals(FundAction.DECREASE, fundDetail.getFundAction());
        });
        payeeFundDetails.forEach(fundDetail -> {
            checkSingleFundDetail(paymentId, orderId, fundDetail);
            payeeAmount.addTo(fundDetail.getAmount());
            Assert.assertEquals(BelongTo.PAYEE, fundDetail.getBelongTo());
            Assert.assertEquals(FundAction.INCREASE, fundDetail.getFundAction());
        });
        Assert.assertEquals(payerAmount, payeeAmount);
        Assert.assertEquals(totalAmount, payerAmount);
    }

    public void checkSingleFundDetail(String paymentId, String orderId, FundDetail fundDetail) {
        Assert.assertNotNull(fundDetail);
        Assert.assertNotNull(fundDetail.getDetailId());
        Assert.assertNotNull(fundDetail.getMemberId());
        Assert.assertNotNull(fundDetail.getTradeId());
        Assert.assertNotNull(fundDetail.getOrderId());
        Assert.assertNotNull(fundDetail.getAssetInfo());
        Assert.assertNotNull(fundDetail.getAmount());
        Assert.assertNotNull(fundDetail.getFundAction());
        Assert.assertNotNull(fundDetail.getBelongTo());
        Assert.assertNotNull(fundDetail.getGmtCreate());
        Assert.assertNotNull(fundDetail.getGmtModified());

        Assert.assertEquals(paymentId, fundDetail.getTradeId());
        Assert.assertEquals(orderId, fundDetail.getOrderId());
    }

}
