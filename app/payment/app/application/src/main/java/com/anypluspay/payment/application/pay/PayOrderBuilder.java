package com.anypluspay.payment.application.pay;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2025/5/18
 */
public class PayOrderBuilder {

    @Autowired
    private IdGeneratorService idGeneratorService;

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
        fundDetail.setAssetInfo(AssetInfo.parse(info.getAssetTypeCode(), info.getAssetJsonStr()));
        fundDetail.setBelongTo(belongTo);
        fundDetail.setFundAction(belongTo == BelongTo.PAYER ? FundAction.DECREASE : FundAction.INCREASE);
        fundDetail.setPayModel(EnumUtil.getByCode(PayModel.class, info.getPayModel()));
        fundDetail.setPayParam(new Extension(info.getPayParam()));
        fundDetail.setExtension(new Extension(info.getExtension()));
        return fundDetail;
    }
}
