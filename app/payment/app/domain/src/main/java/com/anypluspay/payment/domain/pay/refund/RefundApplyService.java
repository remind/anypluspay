package com.anypluspay.payment.domain.pay.refund;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.pay.AbstractPayService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.repository.RefundOrderRepository;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.pay.RefundOrderStatus;
import com.anypluspay.payment.types.pay.RefundType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款申请处理服务
 *
 * @author wxj
 * 2025/5/23
 */
@Service
public class RefundApplyService extends AbstractPayService {

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private PayOrderRepository payOrderRepository;

    /**
     * 申请退款
     *
     * @param refundOrder 退款指令
     */
    public PayResult apply(RefundOrder refundOrder) {
        FluxOrder fluxOrder = buildFluxOrder(refundOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
            refundOrder.setStatus(RefundOrderStatus.PAYING);
            refundOrderRepository.reStore(refundOrder);
        });

        return fluxEngineService.process(fluxOrder);
    }

    /**
     * 申请退款，原支付指令全额退
     *
     * @param paymentId  支付单ID
     * @param payOrderId 原支付指令ID
     * @param refundType 退款类型
     */
    public PayResult apply(String paymentId, String payOrderId, RefundType refundType) {
        PayOrder payOrder = payOrderRepository.load(payOrderId);
        RefundOrder refundOrder = buildRefundOrder(paymentId, payOrder.getAmount(), refundType, payOrder);
        refundOrderRepository.store(refundOrder);
        return apply(refundOrder);
    }

    /**
     * 构造退款指令
     *
     * @param paymentId    支付单ID
     * @param refundAmount 退款金额
     * @param refundType   退款类型
     * @param payOrder   原支付指令
     * @return 退款指令
     */
    public RefundOrder buildRefundOrder(String paymentId, Money refundAmount, RefundType refundType, PayOrder payOrder) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setOrderId(idGeneratorService.genIdByRelateId(paymentId, PayOrderType.REFUND.getIdType()));
        refundOrder.setTradeId(paymentId);
        refundOrder.setMemberId(payOrder.getMemberId());
        refundOrder.setRelationId(payOrder.getOrderId());
        refundOrder.setAmount(refundAmount);
        refundOrder.setStatus(RefundOrderStatus.INIT);
        refundOrder.setRefundType(refundType);
        fillFundDetail(refundAmount, payOrder, refundOrder);
        return refundOrder;
    }

    /**
     * 填充退款资金信息
     *
     * @param refundAmount 退款金额
     * @param payOrder   原支付指令
     * @param refundOrder  退款指令
     */
    private void fillFundDetail(Money refundAmount, PayOrder payOrder, RefundOrder refundOrder) {
        List<FundDetail> refundedPayerDetails = null;
        List<FundDetail> refundedPayeeDetails = null;
        Money refunedAmount = new Money();
        List<RefundOrder> allRefundOrders = refundOrderRepository.loadByOrigPayOrderId(payOrder.getOrderId()).stream()
                .filter(r -> r.getStatus().equals(RefundOrderStatus.SUCCESS))
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
        Assert.isTrue(!refundAmount.greaterThan(payOrder.getAmount().subtract(refunedAmount)), "退款金额超出可退金额");
        refundOrder.setPayeeDetails(buildPayeeDetails(refundOrder.getTradeId(), refundOrder.getOrderId(), refundAmount, payOrder.getPayerDetails(), refundedPayeeDetails));
        refundOrder.setPayerDetails(buildPayerDetails(refundOrder.getTradeId(), refundOrder.getOrderId(), refundAmount, payOrder.getPayeeDetails(), refundedPayerDetails));
    }

    /**
     * 构造退款收款方明细
     *
     * @param paymentId            支付单号
     * @param orderId              支付指令号
     * @param totalAmount          退款总金额
     * @param origPayerDetails     原支付付款方明细
     * @param refundedPayeeDetails 已经退款的收款方明细
     * @return 收款方明细
     */
    private List<FundDetail> buildPayeeDetails(String paymentId, String orderId, Money totalAmount, List<FundDetail> origPayerDetails, List<FundDetail> refundedPayeeDetails) {
        List<FundDetail> fundDetails = new ArrayList<>();
        Money refundAbleAmount = totalAmount.clone();
        origPayerDetails.forEach(origPayerDetail -> {
            if (refundAbleAmount.greaterThan(new Money(0, refundAbleAmount.getCurrency()))) {
                FundDetail refundPayeeDetail = reverseFundDetail(paymentId, orderId, refundAbleAmount.clone(), origPayerDetail, refundedPayeeDetails);
                if (refundPayeeDetail != null) {
                    fundDetails.add(refundPayeeDetail);
                    refundAbleAmount.subtractFrom(refundPayeeDetail.getAmount());
                }
            }
        });
        Assert.isTrue(refundAbleAmount.equals(new Money(0, refundAbleAmount.getCurrency())), "退款金额超限");
        return fundDetails;
    }

    /**
     * 构造退款付款方明细
     *
     * @param paymentId            支付单号
     * @param orderId              支付指令号
     * @param totalAmount          退款总金额
     * @param origPayeeDetails     原支付收款方明细
     * @param refundedPayerDetails 已经退款的付款方明细
     * @return 收款指令资金明细
     */
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
     * @param orderId          订单号
     * @param amount           退款金额
     * @param origFundDetail   原始资金明细
     * @param allFundedDetails 所有已退款的资金明细
     * @return 退款指令资金明细
     */
    private FundDetail reverseFundDetail(String paymentId, String orderId, Money amount, FundDetail origFundDetail, List<FundDetail> allFundedDetails) {
        Money refundAbleAmount;
        if (!CollectionUtils.isEmpty(allFundedDetails)) {
            List<FundDetail> reverseFundDetails = allFundedDetails.stream().filter(r -> r.getRelationId().equals(origFundDetail.getDetailId())).toList();
            Money totalRefundedAmount = reverseFundDetails.stream().map(FundDetail::getAmount).reduce(new Money(), Money::add);
            refundAbleAmount = origFundDetail.getAmount().subtract(totalRefundedAmount);
            if (!refundAbleAmount.greaterThan(new Money())) {
                return null;
            }
        } else {
            refundAbleAmount = origFundDetail.getAmount();
        }
        FundDetail reverseFundDetail = new FundDetail();
        reverseFundDetail.setTradeId(paymentId);
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
