package com.anypluspay.payment.domain.asset.external;

import cn.hutool.core.map.MapUtil;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.asset.FluxInstructionExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.PaymentKey;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.funds.FundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 外部渠道指令执行器
 *
 * @author wxj
 * 2024/1/27
 */
@Component("EXTERNAL_FluxInstructExecutor")
public class ExternalFluxInstructExecutor implements FluxInstructionExecutor {

    @Autowired
    private FundInFacade fundInFacade;

    @Override
    public FluxResult execute(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        FluxResult result = null;
        if (fluxInstruction.getType() == InstructionType.PAY) {
            if (fluxInstruction.getFundAction() == FundAction.DECREASE) {
                result = fundInRequest(fluxInstruction);
            }
        }
        Assert.notNull(result, "支付结果异常");
        result.setNewFluxInstructions(List.of(buildClearingFluxInstruct(fluxInstruction)));
        return result;
    }

    private FluxResult fundInRequest(FluxInstruction fluxInstruction) {
        FluxResult result = new FluxResult();
        BankCardAsset bankCardAsset = (BankCardAsset) fluxInstruction.getAssetInfo();
        FundInRequest request = new FundInRequest();
        request.setRequestId(fluxInstruction.getInstructionId());
        request.setPayInst(bankCardAsset.getInstCode());
        request.setMemberId(fluxInstruction.getExtValue("payerId"));
        request.setPayMethod(fluxInstruction.getExtValue("payMethod"));
        request.setGoodsDesc(fluxInstruction.getExtValue("goodsDesc"));
        request.setAmount(new Money(100));
        FundResult fundResult = fundInFacade.apply(request);

        result.setResultCode(fundResult.getUnityCode());
        result.setResultMessage(fundResult.getUnityMessage());
        result.setPayParam(fundResult.getResponseExtra());

        if (fundResult.getStatus() == BizOrderStatus.FAILED) {
            result.setStatus(PayStatus.FAIL);
        } else if (fundResult.getStatus() == BizOrderStatus.SUCCESS) {
            result.setStatus(PayStatus.SUCCESS);
            fluxInstruction.putExtValue(PaymentKey.CLEARING_ACCOUNT_NO, MapUtil.getStr(fundResult.getExtInfo(), PaymentKey.CLEARING_ACCOUNT_NO));
        } else {
            result.setStatus(PayStatus.PROCESS);
        }
        return result;
    }

    private FluxInstruction buildClearingFluxInstruct(FluxInstruction fluxInstruction) {
        FluxInstruction newFluxInstruct = new FluxInstruction();
        newFluxInstruct.setFluxOrderId(fluxInstruction.getFluxOrderId());
        newFluxInstruct.setType(InstructionType.CLEARING);
        newFluxInstruct.setRelationId(fluxInstruction.getInstructionId());
        newFluxInstruct.setFundDetailId(fluxInstruction.getFundDetailId());
        newFluxInstruct.setAmount(fluxInstruction.getAmount());
        newFluxInstruct.setAssetInfo(new BalanceAsset(PaymentConstants.INNER_MEMBER_ID, fluxInstruction.getExtValue(PaymentKey.CLEARING_ACCOUNT_NO)));
        newFluxInstruct.setFundAction(fluxInstruction.getFundAction());
        return newFluxInstruct;
    }
}
