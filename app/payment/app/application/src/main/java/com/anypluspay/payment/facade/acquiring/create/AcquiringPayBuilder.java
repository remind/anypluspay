package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.PayChannelParamService;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.process.PayProcess;
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

    public PayProcess buildPayProcess(AcquiringOrder acquiringOrder, AcquiringPayRequest request) {
        PayProcess payProcess = new PayProcess();
        payProcess.setPaymentId(acquiringOrder.getPaymentId());
        payProcess.setProcessId(idGeneratorService.genIdByRelateId(acquiringOrder.getPaymentId(), PayOrderType.PAY.getIdType()));
        payProcess.setAmount(acquiringOrder.getAmount());
        payProcess.setMemberId(acquiringOrder.getPayerId());
        payProcess.setStatus(PayProcessStatus.INIT);
        fillFundDetails(acquiringOrder, payProcess, request);
        return payProcess;
    }

    /**
     * 填充资金明细
     *
     * @param payProcess
     * @param request
     */
    private void fillFundDetails(AcquiringOrder acquiringOrder, PayProcess payProcess, AcquiringPayRequest request) {
        request.getPayerFundDetail().forEach(fundDetailInfo -> {
            payProcess.addPayerFundDetail(buildFundDetail(payProcess.getPaymentId(), payProcess.getProcessId(), fundDetailInfo, BelongTo.PAYER));
        });
        payProcess.addPayeeFundDetail(buildPayeeFundDetail(acquiringOrder, payProcess.getProcessId()));
    }

    private FundDetail buildPayeeFundDetail(AcquiringOrder acquiringOrder, String payOrderId) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setPaymentId(acquiringOrder.getPaymentId());
        fundDetail.setPayProcessId(payOrderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(acquiringOrder.getPaymentId(), IdType.FUND_DETAIL_ID));
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
            response.setPaymentId(acquiringOrder.getPaymentId());
            response.setPartnerId(acquiringOrder.getPartnerId());
            response.setOutTradeNo(acquiringOrder.getOutTradeNo());
            response.setOrderStatus(acquiringOrder.getStatus().getCode());
        }
        if (payResult != null) {
            if (payResult.getPayStatus() == PayStatus.PROCESS) {
                Extension payResponse = new Extension(payChannelParamService.get(acquiringOrder.getPayOrderId()));
                response.setInstUrl(payResponse.get(ChannelExtKey.INST_URL.getCode()));
            }
            response.setResultCode(payResult.getResultCode());
            response.setResultMsg(payResult.getResultMessage());
        }
        return response;
    }

    public AcquiringPayResponse buildExceptionResponse(AcquiringOrder acquiringOrder, Exception e) {
        AcquiringPayResponse response = buildResponse(acquiringOrder, null);
        BaseResult.fillExceptionResult(response, e);
        return response;
    }
}
