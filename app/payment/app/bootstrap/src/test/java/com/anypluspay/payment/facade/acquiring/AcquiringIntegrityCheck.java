package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.pay.refund.RefundOrder;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
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
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected RefundOrderRepository refundOrderRepository;

    public void checkAcquiringOrder(String tradeId) {
        AcquiringOrder acquiringOrder = acquiringOrderRepository.load(tradeId);
        Assert.assertNotNull(acquiringOrder);
        Assert.assertNotNull(acquiringOrder.getTradeId());
        Assert.assertNotNull(acquiringOrder.getPartnerId());
        Assert.assertNotNull(acquiringOrder.getOutTradeNo());

        List<PayOrder> payOrders = payOrderRepository.loadByTradeId(tradeId);
        Assert.assertNotNull(payOrders);
        Assert.assertEquals(1, payOrders.size());
        checkPayProcess(tradeId, payOrders.get(0));
    }

    public void checkPayProcess(String tradeId, PayOrder generalPayOrder) {
        Assert.assertNotNull(generalPayOrder);
        Assert.assertNotNull(generalPayOrder.getTradeId());
        Assert.assertNotNull(generalPayOrder.getOrderId());
        Assert.assertNotNull(generalPayOrder.getStatus());
        Assert.assertNotNull(generalPayOrder.getAmount());
        Assert.assertNotNull(generalPayOrder.getMemberId());
        Assert.assertNotNull(generalPayOrder.getGmtCreate());
        Assert.assertNotNull(generalPayOrder.getGmtModified());

        Assert.assertEquals(tradeId, generalPayOrder.getTradeId());
        checkFundDetail(generalPayOrder.getTradeId(), generalPayOrder.getOrderId(), generalPayOrder.getAmount(), generalPayOrder.getPayerDetails(), generalPayOrder.getPayeeDetails());
        checkRefundProcess(generalPayOrder);
    }

    public void checkRefundProcess(PayOrder payOrder) {
        List<RefundOrder> refundOrders = refundOrderRepository.loadByOrigPayOrderId(payOrder.getOrderId());
        if (!CollectionUtils.isEmpty(refundOrders)) {
            Assert.assertEquals(payOrder.getStatus(), PayProcessStatus.SUCCESS);
            refundOrders.forEach(refundOrder -> {
                checkSingleRefundProcess(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder);
                checkFundDetail(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());
            });
            Money refundMount = refundOrders.stream().filter(ro -> ro.getStatus() != RefundOrderStatus.FAIL).map(RefundOrder::getAmount).reduce(new Money(), Money::add);
            Assert.assertFalse(refundMount.greaterThan(payOrder.getAmount()));
        }
    }

    public void checkSingleRefundProcess(String tradeId, String orderId, RefundOrder refundOrder) {
        Assert.assertNotNull(refundOrder);
        Assert.assertNotNull(refundOrder.getTradeId());
        Assert.assertNotNull(refundOrder.getOrderId());
        Assert.assertNotNull(refundOrder.getStatus());
        Assert.assertNotNull(refundOrder.getAmount());
        Assert.assertNotNull(refundOrder.getMemberId());
        Assert.assertNotNull(refundOrder.getRelationId());
        Assert.assertNotNull(refundOrder.getGmtCreate());
        Assert.assertNotNull(refundOrder.getGmtModified());

        Assert.assertEquals(tradeId, refundOrder.getTradeId());
        Assert.assertEquals(orderId, refundOrder.getOrderId());
        checkFundDetail(refundOrder.getTradeId(), refundOrder.getOrderId(), refundOrder.getAmount(), refundOrder.getPayerDetails(), refundOrder.getPayeeDetails());


    }

    public void checkFundDetail(String tradeId, String orderId, Money totalAmount, List<FundDetail> payerFundDetails, List<FundDetail> payeeFundDetails) {
        Assert.assertNotNull(payerFundDetails);
        Assert.assertNotNull(payeeFundDetails);

        Money payerAmount = new Money(0, payerFundDetails.get(0).getAmount().getCurrency());
        Money payeeAmount = new Money(0, payeeFundDetails.get(0).getAmount().getCurrency());
        payerFundDetails.forEach(fundDetail -> {
            checkSingleFundDetail(tradeId, orderId, fundDetail);
            payerAmount.addTo(fundDetail.getAmount());
            Assert.assertEquals(BelongTo.PAYER, fundDetail.getBelongTo());
            Assert.assertEquals(FundAction.DECREASE, fundDetail.getFundAction());
        });
        payeeFundDetails.forEach(fundDetail -> {
            checkSingleFundDetail(tradeId, orderId, fundDetail);
            payeeAmount.addTo(fundDetail.getAmount());
            Assert.assertEquals(BelongTo.PAYEE, fundDetail.getBelongTo());
            Assert.assertEquals(FundAction.INCREASE, fundDetail.getFundAction());
        });
        Assert.assertEquals(payerAmount, payeeAmount);
        Assert.assertEquals(totalAmount, payerAmount);
    }

    public void checkSingleFundDetail(String tradeId, String orderId, FundDetail fundDetail) {
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

        Assert.assertEquals(tradeId, fundDetail.getTradeId());
        Assert.assertEquals(orderId, fundDetail.getOrderId());
    }
}
