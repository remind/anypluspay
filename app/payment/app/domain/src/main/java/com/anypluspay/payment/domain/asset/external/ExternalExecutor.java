package com.anypluspay.payment.domain.asset.external;

import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.FundOutFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.payment.domain.asset.AssetFluxExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxProcessDirection;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.domain.repository.FundDetailRepository;
import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.domain.biz.withdraw.WithdrawOrder;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.anypluspay.payment.domain.asset.AssetFluxExecutor.EXECUTOR_SUFFIX;

/**
 * 外部渠道指令执行器
 *
 * @author wxj
 * 2025/2/12
 */
@Component("EXTERNAL" + EXECUTOR_SUFFIX)
public class ExternalExecutor implements AssetFluxExecutor {

    @Autowired
    private FundInFacade fundInFacade;

    @Autowired
    private FundOutFacade fundOutFacade;

    @Autowired
    private RefundFacade refundFacade;

    @Autowired
    private ExternalResultService externalResultService;

    @Autowired
    private FluxProcessRepository fluxProcessRepository;

    @Autowired
    private FundDetailRepository fundDetailRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private WithdrawOrderRepository withdrawOrderRepository;

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        IdType paymentIdType = idGeneratorService.getIdType(fluxOrder.getPaymentId());
        if (paymentIdType == IdType.WITHDRAW_ORDER_ID) {
            FundOutRequest fundOutRequest = buildFundOutRequest(fluxOrder, fluxProcess);
            FundResult fundResult = fundOutFacade.apply(fundOutRequest);
            return externalResultService.process(fluxProcess, fundResult);
        } else {
            if (fluxOrder.getPayType() == PayOrderType.REFUND ||
                    (fluxOrder.getPayType() == PayOrderType.PAY && fluxProcess.getDirection() == FluxProcessDirection.REVOKE)) {
                FluxProcess origFluxProcess = fluxProcessRepository.load(fluxProcess.getRelationId());
                RefundRequest request = buildRefundOrder(fluxProcess, origFluxProcess);
                FundResult fundResult = refundFacade.apply(request);
                return externalResultService.process(fluxProcess, fundResult);
            }
        }
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult decrease(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FundInRequest request = new FundInRequest();
        request.setRequestId(fluxProcess.getFluxProcessId());
        FundDetail fundDetail = fundDetailRepository.load(fluxProcess.getFundDetailId());
        request.setPayModel(fundDetail.getPayModel().getCode());
        request.setMemberId(fundDetail.getMemberId());
        request.setAmount(fluxProcess.getAmount());

        Extension payParam = fundDetail.getPayParam();
        request.setPayInst(payParam.get(PaymentExtKey.PAY_INST.getCode()));
        request.setInstExt(new Extension(payParam.get(PaymentExtKey.INST_EXT.getCode())));

        Extension requestExt = new Extension();
        requestExt.add(ChannelExtKey.GOODS_DESC.getCode(), payParam.get(ChannelExtKey.GOODS_DESC.getCode()));
        requestExt.add(ChannelExtKey.GOODS_SUBJECT.getCode(), payParam.get(ChannelExtKey.GOODS_SUBJECT.getCode()));
        request.setExtension(requestExt);

        FundResult fundResult = fundInFacade.apply(request);
        return externalResultService.process(fluxProcess, fundResult);
    }

    @Override
    public FluxResult freeze(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult unfreeze(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        throw new UnsupportedOperationException("不支持的指令");
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

    private FundOutRequest buildFundOutRequest(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FundOutRequest request = new FundOutRequest();
        WithdrawOrder withdrawOrder = withdrawOrderRepository.load(fluxOrder.getPaymentId());
        request.setRequestId(fluxProcess.getFluxProcessId());
        request.setMemberId(withdrawOrder.getMemberId());
        request.setAccountNo(withdrawOrder.getAccountNo());
        request.setBankCode(withdrawOrder.getBankCode());
        request.setAccountName(withdrawOrder.getCardName());
        request.setAmount(fluxProcess.getAmount());
        request.setAccountType(CompanyOrPersonal.PERSONAL);
        request.setPayMethod("balance");
        return request;
    }
}
