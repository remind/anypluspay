package com.anypluspay.payment.facade.instant.builder;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrderStatus;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款订单构造器
 *
 * @author wxj
 * 2025/2/14
 */
@Component
public class RefundOrderBuilder extends PaymentBuilder {

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    public RefundOrder build(RefundRequest request, GeneralPayOrder generalPayOrder) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setOrderId(idGeneratorService.genIdByRelateId(generalPayOrder.getPaymentId(), PayOrderType.REFUND.getIdType()));
        refundOrder.setPaymentId(generalPayOrder.getPaymentId());
        refundOrder.setMemberId(generalPayOrder.getMemberId());
        refundOrder.setRequestId(request.getRequestId());
        refundOrder.setRelationId(generalPayOrder.getOrderId());
        refundOrder.setAmount(request.getAmount());
        refundOrder.setOrderStatus(RefundOrderStatus.INIT);
        fillFundDetail(request, generalPayOrder, refundOrder);
        return refundOrder;
    }

    private void fillFundDetail(RefundRequest request, GeneralPayOrder generalPayOrder, RefundOrder refundOrder) {
        List<FundDetail> refundedPayerDetails = null;
        List<FundDetail> refundedPayeeDetails = null;
        Money refunedAmount = new Money();
        List<RefundOrder> allRefundOrders = refundOrderRepository.loadByPayOrderId(generalPayOrder.getOrderId()).stream()
                .filter(r -> r.getOrderStatus().equals(RefundOrderStatus.SUCCESS))
                .toList();
        if (!CollectionUtils.isEmpty(allRefundOrders)) {
            refundedPayerDetails = allRefundOrders.stream()
                    .map(RefundOrder::getPayerDetails)
                    .flatMap(List::stream)
                    .toList();
            refundedPayeeDetails = allRefundOrders.stream()
                    .map(RefundOrder::getPayeeDetails)
                    .flatMap(List::stream)
                    .toList();
            refunedAmount = allRefundOrders.stream().map(RefundOrder::getAmount).reduce(new Money(), Money::add);
        }
        Assert.isTrue(!request.getAmount().greaterThan(generalPayOrder.getAmount().subtract(refunedAmount)), "退款金额超出可退金额");
        refundOrder.setPayeeDetails(buildPayeeDetails(refundOrder.getOrderId(), request.getAmount(), generalPayOrder.getPayerDetails(), refundedPayeeDetails));
        refundOrder.setPayerDetails(buildPayerDetails(refundOrder.getOrderId(), request.getAmount(), generalPayOrder.getPayeeDetails(), refundedPayerDetails));
    }

    /**
     * 构造退款收款方明细
     *
     * @param orderId              订单号
     * @param totalAmount          退款总金额
     * @param origPayerDetails     原支付付款方明细
     * @param refundedPayeeDetails 已经退款的收款方明细
     * @return
     */
    private List<FundDetail> buildPayeeDetails(String orderId, Money totalAmount, List<FundDetail> origPayerDetails, List<FundDetail> refundedPayeeDetails) {
        List<FundDetail> fundDetails = new ArrayList<>();
        Money requestRefundAmount = totalAmount.clone();
        origPayerDetails.forEach(payerDetail -> {
            if (requestRefundAmount.greaterThan(new Money(0, requestRefundAmount.getCurrency()))) {
                FundDetail refundPayeeDetail = reverseFundDetail(orderId, requestRefundAmount.clone(), payerDetail, refundedPayeeDetails);
                if (refundPayeeDetail != null) {
                    fundDetails.add(refundPayeeDetail);
                    requestRefundAmount.subtractFrom(refundPayeeDetail.getAmount());
                }
            }
        });
        Assert.isTrue(requestRefundAmount.equals(new Money(0, requestRefundAmount.getCurrency())), "退款金额超限");
        return fundDetails;
    }

    private List<FundDetail> buildPayerDetails(String orderId, Money totalAmount, List<FundDetail> origPayeeDetails, List<FundDetail> refundedPayerDetails) {
        List<FundDetail> fundDetails = new ArrayList<>();
        Money requestRefundAmount = totalAmount.clone();
        origPayeeDetails.forEach(payerDetail -> {
            if (requestRefundAmount.greaterThan(new Money(0, requestRefundAmount.getCurrency()))) {
                FundDetail refundPayeeDetail = reverseFundDetail(orderId, requestRefundAmount.clone(), payerDetail, refundedPayerDetails);
                if (refundPayeeDetail != null) {
                    fundDetails.add(refundPayeeDetail);
                    requestRefundAmount.subtractFrom(refundPayeeDetail.getAmount());
                }
            }
        });
        Assert.isTrue(requestRefundAmount.equals(new Money(0, requestRefundAmount.getCurrency())), "退款金额超限");
        return fundDetails;
    }

    /**
     * 反转原资金明细
     *
     * @param orderId               订单号
     * @param amount                退款金额
     * @param origFundDetail        原始资金明细
     * @param allReverseFundDetails 所有已经退款的资金明细
     * @return
     */
    private FundDetail reverseFundDetail(String orderId, Money amount, FundDetail origFundDetail, List<FundDetail> allReverseFundDetails) {
        Money refundAbleAmount;
        if (!CollectionUtils.isEmpty(allReverseFundDetails)) {
            List<FundDetail> reverseFundDetails = allReverseFundDetails.stream().filter(r -> r.getRelationId().equals(origFundDetail.getRelationId())).toList();
            Money totalReverseAmount = reverseFundDetails.stream().map(FundDetail::getAmount).reduce(new Money(), Money::add);
            refundAbleAmount = origFundDetail.getAmount().subtract(totalReverseAmount);
            if (!refundAbleAmount.greaterThan(new Money())) {
                return null;
            }
        } else {
            refundAbleAmount = origFundDetail.getAmount();
        }
        FundDetail reverseFundDetail = new FundDetail();
        reverseFundDetail.setPaymentId(origFundDetail.getPaymentId());
        reverseFundDetail.setPaymentId(origFundDetail.getPaymentId());
        reverseFundDetail.setOrderId(orderId);
        reverseFundDetail.setDetailId(idGeneratorService.genIdByRelateId(orderId, IdType.FUND_DETAIL_ID));
        reverseFundDetail.setAmount(refundAbleAmount.greaterThan(amount) ? amount : refundAbleAmount);
        reverseFundDetail.setMemberId(origFundDetail.getMemberId());
        reverseFundDetail.setAssetInfo(origFundDetail.getAssetInfo());
        reverseFundDetail.setBelongTo(origFundDetail.getBelongTo().reverse());
        reverseFundDetail.setFundAction(reverseFundDetail.getBelongTo() == BelongTo.PAYER ? FundAction.DECREASE : FundAction.INCREASE);
        reverseFundDetail.setRelationId(origFundDetail.getDetailId());
        return reverseFundDetail;
    }
}
