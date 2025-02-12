package com.anypluspay.payment.domain.asset;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.PaymentKey;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        if (fluxInstruction.getType() == InstructionType.REFUND) {
            FluxInstruction origFluxInstruction = fluxOrder.getInstructChain().find(fluxInstruction.getRelationId()).getFluxInstruction();
            RefundRequest request = buildRefundOrder(fluxInstruction, origFluxInstruction);
            FundResult fundResult = refundFacade.apply(request);
            if (fundResult.getStatus() == BizOrderStatus.SUCCESS) {
                fluxInstruction.putExtValue(PaymentKey.CLEARING_ACCOUNT_NO, MapUtil.getStr(fundResult.getExtInfo(), PaymentKey.CLEARING_ACCOUNT_NO));
            }
            FluxResult result = convert(fundResult);
            Assert.notNull(result, "支付结果异常");
            result.setNewFluxInstructions(List.of(buildClearingFluxInstruct(fluxInstruction)));
            return result;
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
        if (fundResult.getStatus() == BizOrderStatus.SUCCESS) {
            fluxInstruction.putExtValue(PaymentKey.CLEARING_ACCOUNT_NO, MapUtil.getStr(fundResult.getExtInfo(), PaymentKey.CLEARING_ACCOUNT_NO));
        }
        FluxResult result = convert(fundResult);
        Assert.notNull(result, "支付结果异常");
        result.setNewFluxInstructions(List.of(buildClearingFluxInstruct(fluxInstruction)));
        return result;
    }

    @Override
    public FluxResult freeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult unfreeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    private FluxInstruction buildClearingFluxInstruct(FluxInstruction fluxInstruction) {
        FluxInstruction newFluxInstruct = new FluxInstruction();
        newFluxInstruct.setFluxOrderId(fluxInstruction.getFluxOrderId());
        newFluxInstruct.setType(InstructionType.CLEARING);
        newFluxInstruct.setRelationId(fluxInstruction.getInstructionId());
        newFluxInstruct.setFundDetailId(fluxInstruction.getFundDetailId());
        newFluxInstruct.setAmount(fluxInstruction.getAmount());
        newFluxInstruct.setAssetInfo(new BalanceAsset(PaymentConstants.INNER_MEMBER_ID, fluxInstruction.getExtValue(PaymentKey.CLEARING_ACCOUNT_NO)));
        newFluxInstruct.setFundAction(fluxInstruction.getFundAction().reverse());
        return newFluxInstruct;
    }

    /**
     * 构造退款请求
     *
     * @param fluxInstruction   退款指令
     * @param origFluxInstruction   原指令
     * @return  退款request
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

    private FluxResult convert(FundResult fundResult) {
        FluxResult result = new FluxResult();
        result.setResultCode(fundResult.getUnityCode());
        result.setResultMessage(fundResult.getUnityMessage());
        result.setPayParam(fundResult.getResponseExtra());

        if (fundResult.getStatus() == BizOrderStatus.FAILED) {
            result.setStatus(PayStatus.FAIL);
        } else if (fundResult.getStatus() == BizOrderStatus.SUCCESS) {
            result.setStatus(PayStatus.SUCCESS);
        } else {
            result.setStatus(PayStatus.PROCESS);
        }
        return result;
    }
}
