package com.anypluspay.payment.facade.acquiring.refund;

import cn.hutool.core.lang.UUID;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrderStatus;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.TradeType;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2025/5/19
 */
@Service
public class AcquiringRefundBuilder extends PaymentBuilder {

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    /**
     * 构造交易订单
     *
     * @param request
     * @return
     */
    public AcquiringOrder buildTradeOrder(AcquiringRefundRequest request, AcquiringOrder origAcquiringOrder) {
        Money refundAmount = new Money(request.getAmount(), origAcquiringOrder.getAmount().getCurrency());
        checkRefundAmount(origAcquiringOrder, refundAmount);
        AcquiringOrder acquiringOrder = new AcquiringOrder();
        acquiringOrder.setPaymentId(idGeneratorService.genPaymentId(request.getPartnerId(), IdType.TRADE_ORDER_ID));
        acquiringOrder.setRelationPaymentId(origAcquiringOrder.getPaymentId());
        acquiringOrder.setPartnerId(origAcquiringOrder.getPartnerId());
        acquiringOrder.setOutTradeNo(request.getOutTradeNo());
        acquiringOrder.setTradeType(TradeType.REFUND_ACQUIRING);
        acquiringOrder.setAmount(refundAmount);
        acquiringOrder.setSubject(origAcquiringOrder.getSubject());
        acquiringOrder.setPayeeId(origAcquiringOrder.getPayeeId());
        acquiringOrder.setPayeeAccountNo(origAcquiringOrder.getPayeeAccountNo());
        acquiringOrder.setPayerId(origAcquiringOrder.getPayerId());
        acquiringOrder.setStatus(AcquiringOrderStatus.INIT);
        return acquiringOrder;
    }

    private void checkRefundAmount(AcquiringOrder origAcquiringOrder, Money amount) {
        List<AcquiringOrder> allRelationOrders = acquiringOrderRepository.loadByRelationPaymentId(origAcquiringOrder.getPaymentId());
        if (!CollectionUtils.isEmpty(allRelationOrders)) {
            Money refundAmount = allRelationOrders.stream()
                    .filter(r -> r.getStatus().equals(AcquiringOrderStatus.SUCCESS) && r.getTradeType() == TradeType.REFUND_ACQUIRING)
                    .map(AcquiringOrder::getAmount)
                    .reduce(new Money(), Money::add);
            Assert.isTrue(!amount.greaterThan(origAcquiringOrder.getAmount().subtract(refundAmount)), "退款金额超出可退金额");
        }
    }

    public RefundOrder buildRefundOrder(AcquiringOrder refundAcquiringOrder, Money refundAmount, GeneralPayOrder generalPayOrder) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setOrderId(idGeneratorService.genIdByRelateId(refundAcquiringOrder.getPaymentId(), PayOrderType.REFUND.getIdType()));
        refundOrder.setPaymentId(refundAcquiringOrder.getPaymentId());
        refundOrder.setMemberId(generalPayOrder.getMemberId());
        refundOrder.setRequestId(UUID.randomUUID().toString(true));
        refundOrder.setRelationId(generalPayOrder.getOrderId());
        refundOrder.setAmount(refundAmount);
        refundOrder.setOrderStatus(RefundOrderStatus.INIT);
        fillFundDetail(refundAmount, generalPayOrder, refundOrder);
        return refundOrder;
    }

    private void fillFundDetail(Money refundAmount, GeneralPayOrder generalPayOrder, RefundOrder refundOrder) {
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
        Assert.isTrue(!refundAmount.greaterThan(generalPayOrder.getAmount().subtract(refunedAmount)), "退款金额超出可退金额");
        refundOrder.setPayeeDetails(buildPayeeDetails(refundOrder.getPaymentId(), refundOrder.getOrderId(), refundAmount, generalPayOrder.getPayerDetails(), refundedPayeeDetails));
        refundOrder.setPayerDetails(buildPayerDetails(refundOrder.getPaymentId(), refundOrder.getOrderId(), refundAmount, generalPayOrder.getPayeeDetails(), refundedPayerDetails));
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
    private List<FundDetail> buildPayeeDetails(String paymentId, String orderId, Money totalAmount, List<FundDetail> origPayerDetails, List<FundDetail> refundedPayeeDetails) {
        List<FundDetail> fundDetails = new ArrayList<>();
        Money requestRefundAmount = totalAmount.clone();
        origPayerDetails.forEach(payerDetail -> {
            if (requestRefundAmount.greaterThan(new Money(0, requestRefundAmount.getCurrency()))) {
                FundDetail refundPayeeDetail = reverseFundDetail(paymentId, orderId, requestRefundAmount.clone(), payerDetail, refundedPayeeDetails);
                if (refundPayeeDetail != null) {
                    fundDetails.add(refundPayeeDetail);
                    requestRefundAmount.subtractFrom(refundPayeeDetail.getAmount());
                }
            }
        });
        Assert.isTrue(requestRefundAmount.equals(new Money(0, requestRefundAmount.getCurrency())), "退款金额超限");
        return fundDetails;
    }

    private List<FundDetail> buildPayerDetails(String paymentId, String orderId, Money totalAmount, List<FundDetail> origPayeeDetails, List<FundDetail> refundedPayerDetails) {
        List<FundDetail> fundDetails = new ArrayList<>();
        Money requestRefundAmount = totalAmount.clone();
        origPayeeDetails.forEach(payerDetail -> {
            if (requestRefundAmount.greaterThan(new Money(0, requestRefundAmount.getCurrency()))) {
                FundDetail refundPayeeDetail = reverseFundDetail(paymentId, orderId, requestRefundAmount.clone(), payerDetail, refundedPayerDetails);
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
    private FundDetail reverseFundDetail(String paymentId, String orderId, Money amount, FundDetail origFundDetail, List<FundDetail> allReverseFundDetails) {
        Money refundAbleAmount;
        if (!CollectionUtils.isEmpty(allReverseFundDetails)) {
            List<FundDetail> reverseFundDetails = allReverseFundDetails.stream().filter(r -> r.getRelationId().equals(origFundDetail.getDetailId())).toList();
            Money totalReverseAmount = reverseFundDetails.stream().map(FundDetail::getAmount).reduce(new Money(), Money::add);
            refundAbleAmount = origFundDetail.getAmount().subtract(totalReverseAmount);
            if (!refundAbleAmount.greaterThan(new Money())) {
                return null;
            }
        } else {
            refundAbleAmount = origFundDetail.getAmount();
        }
        FundDetail reverseFundDetail = new FundDetail();
        reverseFundDetail.setPaymentId(paymentId);
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


    /**
     * 构造交易退款响应
     *
     * @param acquiringOrder
     * @return
     */
    public AcquiringRefundResponse buildResponse(AcquiringOrder acquiringOrder) {
        AcquiringRefundResponse response = new AcquiringRefundResponse();
        response.setSuccess(true);
        response.setPaymentId(acquiringOrder.getPaymentId());
        response.setPartnerId(acquiringOrder.getPartnerId());
        response.setOutTradeNo(acquiringOrder.getOutTradeNo());
        response.setOrderStatus(acquiringOrder.getStatus().getCode());
        return response;
    }

    /**
     * 构造交易创建响应
     *
     * @param request
     * @param e
     * @return
     */
    public AcquiringRefundResponse buildExceptionResponse(AcquiringRefundRequest request, Exception e) {
        AcquiringRefundResponse response = new AcquiringRefundResponse();
        response.setPartnerId(request.getPartnerId());
        response.setOutTradeNo(request.getOutTradeNo());
        BaseResult.fillExceptionResult(response, e);
        return response;
    }
}