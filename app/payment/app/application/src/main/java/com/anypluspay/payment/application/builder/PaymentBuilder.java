package com.anypluspay.payment.application.builder;

import com.anypluspay.payment.domain.BasePayment;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.facade.request.BasePaymentRequest;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/1/15
 */
public abstract class PaymentBuilder {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    /**
     * 填充支付基础信息
     * @param payment
     * @param request
     * @param paymentType
     */
    protected void fillBasePayment(BasePayment payment, BasePaymentRequest request, PaymentType paymentType) {
        payment.setPaymentId(idGeneratorService.genPaymentId(request.getMemberId(), paymentType.getIdType()));
        payment.setMemberId(request.getMemberId());
        payment.setPaymentType(paymentType);
        payment.setMerchantId(request.getMerchantId());
    }

    /**
     * 构造资金明细
     * @param paymentId
     * @param orderId
     * @param info
     * @param belongTo
     * @return
     */
    protected FundDetail buildFundDetail(String paymentId, String orderId, FundDetailInfo info, BelongTo belongTo) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setPaymentId(paymentId);
        fundDetail.setOrderId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(paymentId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(info.getAmount());
        fundDetail.setMemberId(info.getMemberId());
        fundDetail.setAssetInfo(info.getAssetInfo());
        fundDetail.setBelongTo(belongTo);
        fundDetail.setFundAction(belongTo == BelongTo.PAYER ? FundAction.DECREASE : FundAction.INCREASE);
        return fundDetail;
    }
}
