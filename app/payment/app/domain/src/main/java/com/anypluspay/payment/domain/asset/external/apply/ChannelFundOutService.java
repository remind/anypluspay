package com.anypluspay.payment.domain.asset.external.apply;

import com.anypluspay.channel.facade.FundOutFacade;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
import com.anypluspay.payment.types.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流出提交
 * @author wxj
 * 2025/6/19
 */
@Service
public class ChannelFundOutService extends AbstractExternalApplyService {

    @Autowired
    private FundOutFacade fundOutFacade;

    public FluxResult apply(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FundOutRequest fundOutRequest = buildFundOutRequest(fluxOrder, fluxProcess);
        FundResult fundResult = fundOutFacade.apply(fundOutRequest);
        fillTradeInfo(fluxOrder, fundOutRequest);
        return externalResultService.process(fluxProcess, fundResult);
    }

    private FundOutRequest buildFundOutRequest(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FundOutRequest request = new FundOutRequest();
        WithdrawOrder withdrawOrder = withdrawOrderRepository.load(fluxOrder.getTradeId());
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

    private void fillTradeInfo(FluxOrder fluxOrder, FundOutRequest request) {
        IdType tradIdType = idGeneratorService.getIdType(fluxOrder.getTradeId());
        if (tradIdType == IdType.WITHDRAW_ORDER_ID) {
            WithdrawOrder withdrawOrder = withdrawOrderRepository.load(fluxOrder.getTradeId());
            request.setPartnerId(withdrawOrder.getPartnerId());
        }
    }
}
