package com.anypluspay.payment.domain.asset.external;

import cn.hutool.core.map.MapUtil;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.PaymentKey;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外部渠道结果处理器
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class ExternalResultService {

    /**
     * 处理外部渠道结果
     *
     * @param fluxInstruction 交换指令
     * @param fundResult      外部渠道调用结果
     * @return 交换结果
     */
    public FluxResult process(FluxInstruction fluxInstruction, FundResult fundResult) {
        FluxResult result = convert(fundResult);
        if (fundResult.getStatus() == BizOrderStatus.SUCCESS && fundResult.isNeedClearing()) {
            result.setNewFluxInstructions(List.of(buildClearingFluxInstruct(fluxInstruction, fundResult.getClearingAccountNo())));
        }
        return result;
    }


    private FluxResult convert(FundResult fundResult) {
        FluxResult result = new FluxResult();
        result.setResultCode(fundResult.getUnityCode());
        result.setResultMessage(fundResult.getUnityMessage());
        result.setFluxResponse(fundResult.getResponseExt());

        if (fundResult.getStatus() == BizOrderStatus.FAILED) {
            result.setStatus(PayStatus.FAIL);
        } else if (fundResult.getStatus() == BizOrderStatus.SUCCESS) {
            result.setStatus(PayStatus.SUCCESS);
        } else {
            result.setStatus(PayStatus.PROCESS);
        }
        return result;
    }

    private FluxInstruction buildClearingFluxInstruct(FluxInstruction fluxInstruction, String clearingAccountNo) {
        FluxInstruction newFluxInstruct = new FluxInstruction();
        newFluxInstruct.setFluxOrderId(fluxInstruction.getFluxOrderId());
        newFluxInstruct.setType(InstructionType.CLEARING);
        newFluxInstruct.setRelationId(fluxInstruction.getInstructionId());
        newFluxInstruct.setFundDetailId(fluxInstruction.getFundDetailId());
        newFluxInstruct.setAmount(fluxInstruction.getAmount());
        newFluxInstruct.setAssetInfo(new BalanceAsset(PaymentConstants.INNER_MEMBER_ID, clearingAccountNo));
        newFluxInstruct.setFundAction(fluxInstruction.getFundAction().reverse());
        return newFluxInstruct;
    }
}
