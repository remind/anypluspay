package com.anypluspay.payment.domain.asset.external;

import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.asset.AssetFluxExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.domain.repository.FluxInstructionRepository;
import com.anypluspay.payment.types.asset.BankCardAsset;
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
    private RefundFacade refundFacade;

    @Autowired
    private ExternalResultService externalResultService;

    @Autowired
    private FluxInstructionRepository fluxInstructionRepository;

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        if (fluxInstruction.getType() == InstructionType.REFUND) {
            FluxInstruction origFluxInstruction = fluxInstructionRepository.load(fluxInstruction.getRelationId());
            RefundRequest request = buildRefundOrder(fluxInstruction, origFluxInstruction);
            FundResult fundResult = refundFacade.apply(request);
            return externalResultService.process(fluxInstruction, fundResult);
        }
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult decrease(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        BankCardAsset bankCardAsset = (BankCardAsset) fluxInstruction.getAssetInfo();
        FundInRequest request = new FundInRequest();
        request.setRequestId(fluxInstruction.getInstructionId());
        request.setPayInst(bankCardAsset.getInstCode());
        request.setMemberId(fluxInstruction.getExtValue("payerId"));
        request.setPayMethod(fluxInstruction.getExtValue("payMethod"));
        request.setGoodsDesc(fluxInstruction.getExtValue("goodsDesc"));
        request.setAmount(new Money(100));
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
        request.setMemberId(origFluxInstruction.getExtValue("payerId"));
        request.setRefundType(RefundType.PAYER_REFUND);
        request.setOrigRequestId(origFluxInstruction.getInstructionId());
        request.setAmount(fluxInstruction.getAmount());
        return request;
    }
}
