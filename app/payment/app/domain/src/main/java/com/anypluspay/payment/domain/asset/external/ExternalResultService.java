package com.anypluspay.payment.domain.asset.external;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxProcessType;
import com.anypluspay.payment.types.PayStatus;
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
     * @param fluxProcess 交换指令
     * @param fundResult      外部渠道调用结果
     * @return 交换结果
     */
    public FluxResult process(FluxProcess fluxProcess, FundResult fundResult) {
        FluxResult result = convert(fundResult);
        if (fundResult.isSuccess() && fundResult.getStatus() == BizOrderStatus.SUCCESS && fundResult.isNeedClearing()) {
            result.setNewFluxProcesses(List.of(buildClearingFluxInstruct(fluxProcess, fundResult.getClearingAccountNo())));
        }
        return result;
    }


    private FluxResult convert(FundResult fundResult) {
        FluxResult result = new FluxResult();
        if (fundResult.isSuccess()) {
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
        } else {
            result.setResultCode(fundResult.getResultCode());
            result.setResultMessage(fundResult.getResultMsg());
            result.setStatus(PayStatus.FAIL);
        }
        return result;
    }

    private FluxProcess buildClearingFluxInstruct(FluxProcess fluxProcess, String clearingAccountNo) {
        FluxProcess newFluxInstruct = new FluxProcess();
        newFluxInstruct.setFluxOrderId(fluxProcess.getFluxOrderId());
        newFluxInstruct.setType(FluxProcessType.CLEARING);
        newFluxInstruct.setDirection(fluxProcess.getDirection());
        newFluxInstruct.setRelationId(fluxProcess.getFluxProcessId());
        newFluxInstruct.setFundDetailId(fluxProcess.getFundDetailId());
        newFluxInstruct.setAmount(fluxProcess.getAmount());
        newFluxInstruct.setAssetInfo(new BalanceAsset(PaymentConstants.INNER_MEMBER_ID, clearingAccountNo));
        newFluxInstruct.setFundAction(fluxProcess.getFundAction().reverse());
        return newFluxInstruct;
    }
}
