package com.anypluspay.payment.application.flux;

import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import com.anypluspay.payment.domain.payorder.BasePayOrder;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2025/2/14
 */
@Service
public class FluxOrderBuilder {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public FluxOrder buildFluxOrder(BasePayOrder payOrder) {
        FluxOrder fluxOrder = new FluxOrder();
        fluxOrder.setPaymentId(payOrder.getPaymentId());
        fluxOrder.setPayOrderId(payOrder.getOrderId());
        fluxOrder.setFluxOrderId(idGeneratorService.genIdByRelateId(payOrder.getPaymentId(), IdType.FLUX_ORDER_ID));
        fluxOrder.setStatus(FluxOrderStatus.INIT);
        fluxOrder.setInstructChain(new InstructChain());
        InstructionType type = payOrder instanceof GeneralPayOrder ? InstructionType.PAY : InstructionType.REFUND;
        fillFluxInstruct(fluxOrder, payOrder.getPayerDetails(), payOrder.getPayeeDetails(), type);
        return fluxOrder;
    }

    private void fillFluxInstruct(FluxOrder fluxOrder, List<FundDetail> payerFundDetails, List<FundDetail> payeeFundDetails, InstructionType type) {
        payerFundDetails.forEach(fundDetail -> {
            fillFluxInstruction(fluxOrder, fundDetail, type);
        });

        payeeFundDetails.forEach(fundDetail -> {
            fillFluxInstruction(fluxOrder, fundDetail, type);
        });
    }

    private void fillFluxInstruction(FluxOrder fluxOrder, FundDetail fundDetail, InstructionType type) {
        FluxInstruction fluxInstruction = new FluxInstruction();
        fluxInstruction.setInstructionId(idGeneratorService.genIdByRelateId(fluxOrder.getFluxOrderId(), IdType.FLUX_INSTRUCT_ID));
        fluxInstruction.setPaymentId(fundDetail.getPaymentId());
        fluxInstruction.setPayOrderId(fundDetail.getOrderId());
        fluxInstruction.setStatus(InstructStatus.INIT);
        fluxInstruction.setType(type);
        fluxInstruction.setFluxOrderId(fluxOrder.getFluxOrderId());
        fluxInstruction.setAmount(fundDetail.getAmount());
        fluxInstruction.setFundDetailId(fundDetail.getDetailId());

        fluxInstruction.setAssetInfo(fundDetail.getAssetInfo());
        fluxInstruction.setFundAction(fundDetail.getFundAction());

        fluxOrder.getInstructChain().append(fluxInstruction);
    }
}
