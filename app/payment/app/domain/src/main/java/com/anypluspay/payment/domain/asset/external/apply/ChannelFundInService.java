package com.anypluspay.payment.domain.asset.external.apply;

import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.trade.deposit.DepositOrder;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流入提交
 *
 * @author wxj
 * 2025/6/19
 */
@Service
public class ChannelFundInService extends AbstractExternalApplyService {

    @Autowired
    private FundInFacade fundInFacade;

    @Autowired
    private RefundFacade refundFacade;

    public FluxResult apply(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FundInRequest request = new FundInRequest();
        request.setRequestId(fluxProcess.getFluxProcessId());
        FundDetail fundDetail = fundDetailRepository.load(fluxProcess.getFundDetailId());
        request.setPayModel(fundDetail.getPayModel().getCode());
        request.setMemberId(fundDetail.getMemberId());
        request.setAmount(fluxProcess.getAmount());

        Extension payParam = fundDetail.getPayParam();
        request.setPayInst(payParam.get(PaymentExtKey.PAY_INST.getCode()));
        request.setInstExt(new Extension(payParam.get(PaymentExtKey.INST_EXT.getCode())));
        fillTradeInfo(fluxOrder, request);

        FundResult fundResult = fundInFacade.apply(request);
        return externalResultService.process(fluxProcess, fundResult);
    }

    public FluxResult refund(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FluxProcess origFluxProcess = fluxProcessRepository.load(fluxProcess.getRelationId());
        RefundRequest request = buildRefundOrder(fluxProcess, origFluxProcess);
        FundResult fundResult = refundFacade.apply(request);
        return externalResultService.process(fluxProcess, fundResult);
    }

    /**
     * 填充交易信息
     *
     * @param fluxOrder
     * @param request
     */
    protected void fillTradeInfo(FluxOrder fluxOrder, FundInRequest request) {
        IdType tradIdType = idGeneratorService.getIdType(fluxOrder.getTradeId());
        if (tradIdType == IdType.ACQUIRING_ORDER_ID) {
            AcquiringOrder acquiringOrder = acquiringOrderRepository.load(fluxOrder.getTradeId());
            request.setPartnerId(acquiringOrder.getPartnerId());
            request.getExtension().add(ChannelExtKey.GOODS_DESC.getCode(), acquiringOrder.getSubject());
            request.getExtension().add(ChannelExtKey.GOODS_SUBJECT.getCode(), acquiringOrder.getSubject());
        } else if (tradIdType == IdType.DEPOSIT_ORDER_ID) {
            DepositOrder depositOrder = depositOrderRepository.load(fluxOrder.getTradeId());
            request.setPartnerId(depositOrder.getPartnerId());
        }
    }

    /**
     * 构造退款请求
     *
     * @param fluxProcess     退款指令
     * @param origFluxProcess 原指令
     * @return 退款request
     */
    private RefundRequest buildRefundOrder(FluxProcess fluxProcess, FluxProcess origFluxProcess) {
        RefundRequest request = new RefundRequest();
        request.setRequestId(fluxProcess.getFluxProcessId());
        request.setRefundType(RefundType.PAYER_REFUND);
        request.setOrigRequestId(origFluxProcess.getFluxProcessId());
        request.setAmount(fluxProcess.getAmount());
        return request;
    }
}
