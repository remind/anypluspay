package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.domain.process.refund.RefundProcess;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2025/5/20
 */
@Service
public class AcquiringIntegrityCheck {

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    protected PayProcessRepository payProcessRepository;

    @Autowired
    protected RefundProcessRepository refundProcessRepository;

    public void checkAcquiringOrder(String paymentId) {
        AcquiringOrder acquiringOrder = acquiringOrderRepository.load(paymentId);
        Assert.assertNotNull(acquiringOrder);
        Assert.assertNotNull(acquiringOrder.getPaymentId());
        Assert.assertNotNull(acquiringOrder.getPartnerId());
        Assert.assertNotNull(acquiringOrder.getOutTradeNo());

        List<PayProcess> payProcesses = payProcessRepository.loadByPaymentId(paymentId);
        Assert.assertNotNull(payProcesses);
        Assert.assertEquals(1, payProcesses.size());
        checkPayProcess(paymentId, payProcesses.get(0));
    }

    public void checkPayProcess(String paymentId, PayProcess generalPayOrder) {
        Assert.assertNotNull(generalPayOrder);
        Assert.assertNotNull(generalPayOrder.getPaymentId());
        Assert.assertNotNull(generalPayOrder.getProcessId());
        Assert.assertNotNull(generalPayOrder.getStatus());
        Assert.assertNotNull(generalPayOrder.getAmount());
        Assert.assertNotNull(generalPayOrder.getMemberId());
        Assert.assertNotNull(generalPayOrder.getGmtCreate());
        Assert.assertNotNull(generalPayOrder.getGmtModified());

        Assert.assertEquals(paymentId, generalPayOrder.getPaymentId());
        checkFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getProcessId(), generalPayOrder.getAmount(), generalPayOrder.getPayerDetails(), generalPayOrder.getPayeeDetails());
        checkRefundProcess(generalPayOrder);
    }

    public void checkRefundProcess(PayProcess payProcess) {
        List<RefundProcess> refundOrders = refundProcessRepository.loadByPayProcessId(payProcess.getProcessId());
        if (!CollectionUtils.isEmpty(refundOrders)) {
            Assert.assertEquals(payProcess.getStatus(), PayProcessStatus.SUCCESS);
            refundOrders.forEach(refundOrder -> {
                checkSingleRefundProcess(refundOrder.getPaymentId(), refundOrder.getProcessId(), refundOrder);
                checkFundDetail(refundOrder.getPaymentId(), refundOrder.getProcessId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());
            });
            Money refundMount = refundOrders.stream().filter(ro -> ro.getStatus() != RefundOrderStatus.FAIL).map(RefundProcess::getAmount).reduce(new Money(), Money::add);
            Assert.assertFalse(refundMount.greaterThan(payProcess.getAmount()));
        }
    }

    public void checkSingleRefundProcess(String paymentId, String orderId, RefundProcess refundOrder) {
        Assert.assertNotNull(refundOrder);
        Assert.assertNotNull(refundOrder.getPaymentId());
        Assert.assertNotNull(refundOrder.getProcessId());
        Assert.assertNotNull(refundOrder.getStatus());
        Assert.assertNotNull(refundOrder.getAmount());
        Assert.assertNotNull(refundOrder.getMemberId());
        Assert.assertNotNull(refundOrder.getRelationId());
        Assert.assertNotNull(refundOrder.getGmtCreate());
        Assert.assertNotNull(refundOrder.getGmtModified());

        Assert.assertEquals(paymentId, refundOrder.getPaymentId());
        Assert.assertEquals(orderId, refundOrder.getProcessId());
        checkFundDetail(refundOrder.getPaymentId(), refundOrder.getProcessId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());


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
        Assert.assertNotNull(fundDetail.getPaymentId());
        Assert.assertNotNull(fundDetail.getPayProcessId());
        Assert.assertNotNull(fundDetail.getAssetInfo());
        Assert.assertNotNull(fundDetail.getAmount());
        Assert.assertNotNull(fundDetail.getFundAction());
        Assert.assertNotNull(fundDetail.getBelongTo());
        Assert.assertNotNull(fundDetail.getGmtCreate());
        Assert.assertNotNull(fundDetail.getGmtModified());

        Assert.assertEquals(paymentId, fundDetail.getPaymentId());
        Assert.assertEquals(orderId, fundDetail.getPayProcessId());
    }
}
