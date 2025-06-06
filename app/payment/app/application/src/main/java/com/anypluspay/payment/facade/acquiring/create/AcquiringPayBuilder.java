package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.response.GlobalResultCode;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.PayChannelParamService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/5/18
 */
@Component
public class AcquiringPayBuilder extends PaymentBuilder {

    @Autowired
    private PayChannelParamService payChannelParamService;

    public PayOrder buildPayProcess(AcquiringOrder acquiringOrder, AcquiringPayRequest request) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(acquiringOrder.getTradeId());
        payOrder.setOrderId(idGeneratorService.genIdByRelateId(acquiringOrder.getTradeId(), PayOrderType.PAY.getIdType()));
        payOrder.setAmount(acquiringOrder.getAmount());
        payOrder.setMemberId(acquiringOrder.getPayerId());
        payOrder.setStatus(PayProcessStatus.INIT);
        fillFundDetails(acquiringOrder, payOrder, request);
        return payOrder;
    }

    /**
     * 填充资金明细
     *
     * @param payOrder
     * @param request
     */
    private void fillFundDetails(AcquiringOrder acquiringOrder, PayOrder payOrder, AcquiringPayRequest request) {
        request.getPayerFundDetail().forEach(fundDetailInfo -> {
            payOrder.addPayerFundDetail(buildFundDetail(payOrder.getTradeId(), payOrder.getOrderId(), fundDetailInfo, BelongTo.PAYER));
        });
        payOrder.addPayeeFundDetail(buildPayeeFundDetail(acquiringOrder, payOrder.getOrderId()));
    }

    private FundDetail buildPayeeFundDetail(AcquiringOrder acquiringOrder, String payOrderId) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setTradeId(acquiringOrder.getTradeId());
        fundDetail.setOrderId(payOrderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(acquiringOrder.getTradeId(), IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(acquiringOrder.getAmount());
        fundDetail.setMemberId(acquiringOrder.getPayeeId());
        fundDetail.setAssetInfo(new BalanceAsset(acquiringOrder.getPayeeId(), acquiringOrder.getPayeeAccountNo()));
        fundDetail.setBelongTo(BelongTo.PAYEE);
        fundDetail.setFundAction(FundAction.INCREASE);
        fundDetail.setPayModel(PayModel.BALANCE);
        return fundDetail;
    }

    public AcquiringPayResponse buildResponse(AcquiringOrder acquiringOrder, PayResult payResult) {
        AcquiringPayResponse response = new AcquiringPayResponse();
        response.setSuccess(true);
        if (acquiringOrder != null) {
            response.setTradeId(acquiringOrder.getTradeId());
            response.setPartnerId(acquiringOrder.getPartnerId());
            response.setOutTradeNo(acquiringOrder.getOutTradeNo());
            response.setOrderStatus(acquiringOrder.getStatus().getCode());
        }
        if (payResult != null) {
            if (payResult.getPayStatus() == PayStatus.PROCESS && acquiringOrder != null) {
                Extension payResponse = new Extension(payChannelParamService.get(acquiringOrder.getOrderId()));
                response.setIrd(payResponse.get(ChannelExtKey.INST_REDIRECTION_DATA.getCode()));
            }
            response.setResultCode(payResult.getResultCode());
            response.setResultMsg(payResult.getResultMessage());
        } else {
            response.setResultCode(GlobalResultCode.SUCCESS.getCode());
        }
        return response;
    }

    public AcquiringPayResponse buildExceptionResponse(AcquiringOrder acquiringOrder, Exception e) {
        AcquiringPayResponse response = buildResponse(acquiringOrder, null);
        BaseResult.fillExceptionResult(response, e);
        return response;
    }
}
