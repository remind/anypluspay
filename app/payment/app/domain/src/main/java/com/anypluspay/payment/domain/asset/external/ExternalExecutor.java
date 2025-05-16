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
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructionDirection;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.domain.repository.FundDetailRepository;
import com.anypluspay.payment.domain.repository.WithdrawOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.domain.withdraw.WithdrawOrder;
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
    private FluxInstructionRepository fluxInstructionRepository;

    @Autowired
    private FundDetailRepository fundDetailRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private WithdrawOrderRepository withdrawOrderRepository;

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        IdType paymentIdType = idGeneratorService.getIdType(fluxOrder.getPaymentId());
        if (paymentIdType == IdType.WITHDRAW_ORDER_ID) {
            FundOutRequest fundOutRequest = buildFundOutRequest(fluxOrder, fluxInstruction);
            FundResult fundResult = fundOutFacade.apply(fundOutRequest);
            return externalResultService.process(fluxInstruction, fundResult);
        } else {
            if (fluxOrder.getPayType() == PayOrderType.REFUND ||
                    (fluxOrder.getPayType() == PayOrderType.PAY && fluxInstruction.getDirection() == InstructionDirection.REVOKE)) {
                FluxInstruction origFluxInstruction = fluxInstructionRepository.load(fluxInstruction.getRelationId());
                RefundRequest request = buildRefundOrder(fluxInstruction, origFluxInstruction);
                FundResult fundResult = refundFacade.apply(request);
                return externalResultService.process(fluxInstruction, fundResult);
            }
        }
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult decrease(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FundInRequest request = new FundInRequest();
        request.setRequestId(fluxInstruction.getInstructionId());
        FundDetail fundDetail = fundDetailRepository.load(fluxInstruction.getFundDetailId());
        request.setPayModel(fundDetail.getPayModel().getCode());
        request.setMemberId(fundDetail.getMemberId());
        request.setAmount(fluxInstruction.getAmount());

        Extension payParam = fundDetail.getPayParam();
        request.setPayInst(payParam.get(PaymentExtKey.PAY_INST.getCode()));
        request.setInstExt(new Extension(payParam.get(PaymentExtKey.INST_EXT.getCode())));

        Extension requestExt = new Extension();
        requestExt.add(ChannelExtKey.GOODS_DESC.getCode(), payParam.get(ChannelExtKey.GOODS_DESC.getCode()));
        requestExt.add(ChannelExtKey.GOODS_SUBJECT.getCode(), payParam.get(ChannelExtKey.GOODS_SUBJECT.getCode()));
        request.setExtension(requestExt);

        FundResult fundResult = fundInFacade.apply(request);
        return externalResultService.process(fluxInstruction, fundResult);
    }

    @Override
    public FluxResult freeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult unfreeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    /**
     * 构造退款请求
     *
     * @param fluxInstruction     退款指令
     * @param origFluxInstruction 原指令
     * @return 退款request
     */
    private RefundRequest buildRefundOrder(FluxInstruction fluxInstruction, FluxInstruction origFluxInstruction) {
        RefundRequest request = new RefundRequest();
        request.setRequestId(fluxInstruction.getInstructionId());
        request.setRefundType(RefundType.PAYER_REFUND);
        request.setOrigRequestId(origFluxInstruction.getInstructionId());
        request.setAmount(fluxInstruction.getAmount());
        return request;
    }

    private FundOutRequest buildFundOutRequest(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FundOutRequest request = new FundOutRequest();
        WithdrawOrder withdrawOrder = withdrawOrderRepository.load(fluxOrder.getPaymentId());
        request.setRequestId(fluxInstruction.getInstructionId());
        request.setMemberId(withdrawOrder.getMemberId());
        request.setAccountNo(withdrawOrder.getAccountNo());
        request.setBankCode(withdrawOrder.getBankCode());
        request.setAccountName(withdrawOrder.getCardName());
        request.setAmount(fluxInstruction.getAmount());
        request.setAccountType(CompanyOrPersonal.PERSONAL);
        request.setPayMethod("balance");
        return request;
    }
}
