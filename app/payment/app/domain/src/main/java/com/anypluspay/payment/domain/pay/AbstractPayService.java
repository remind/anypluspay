package com.anypluspay.payment.domain.pay;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.payment.domain.asset.FundDetailSortService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.trade.TradeOrderService;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.FluxChain;
import com.anypluspay.payment.domain.flux.service.FluxEngineService;
import com.anypluspay.payment.domain.pay.refund.RefundOrder;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.domain.repository.PayOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * 基础支付服务
 * @author wxj
 * 2024/1/27
 */
public abstract class AbstractPayService {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    @Autowired
    protected FluxEngineService fluxEngineService;

    @Autowired
    protected FluxOrderRepository fluxOrderRepository;

    @Autowired
    protected FluxProcessRepository fluxProcessRepository;

    @Autowired
    protected PayOrderRepository payOrderRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected FundDetailSortService fundDetailSortService;

    @Autowired
    protected TradeOrderService tradeOrderService;


    protected FluxOrder createAndStoreFluxOrder(BasePayOrder payOrder) {
        FluxOrder fluxOrder = buildFluxOrder(payOrder);
        transactionTemplate.executeWithoutResult(status -> {
            fluxOrderRepository.store(fluxOrder);
        });
        return fluxOrder;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected FluxOrder buildFluxOrder(BasePayOrder payOrder) {
        FluxOrder fluxOrder = new FluxOrder();
        fluxOrder.setTradeId(payOrder.getTradeId());
        fluxOrder.setOrderId(payOrder.getOrderId());
        fluxOrder.setFluxOrderId(idGeneratorService.genIdByRelateId(payOrder.getTradeId(), IdType.FLUX_ORDER_ID));
        fluxOrder.setStatus(FluxOrderStatus.PROCESS);
        fluxOrder.setPayType(getPayOrderType(payOrder));
        fluxOrder.setFluxChain(new FluxChain());
        fillFluxInstruct(fluxOrder, payOrder.getPayerDetails(), payOrder.getPayeeDetails());
        return fluxOrder;
    }

    protected PayOrderType getPayOrderType(BasePayOrder payOrder) {
        if (payOrder instanceof PayOrder) {
            return PayOrderType.PAY;
        } else if (payOrder instanceof RefundOrder) {
            return PayOrderType.REFUND;
        }
        return null;
    }

    protected void fillFluxInstruct(FluxOrder fluxOrder, List<FundDetail> payerFundDetails, List<FundDetail> payeeFundDetails) {
        fundDetailSortService.payerSort(payerFundDetails);
        payerFundDetails.forEach(fundDetail -> {
            fillFluxInstruction(fluxOrder, fundDetail);
        });

        fundDetailSortService.payeeSort(payeeFundDetails);
        payeeFundDetails.forEach(fundDetail -> {
            fillFluxInstruction(fluxOrder, fundDetail);
        });
    }

    private void fillFluxInstruction(FluxOrder fluxOrder, FundDetail fundDetail) {
        FluxProcess fluxProcess = new FluxProcess();
        fluxProcess.setFluxProcessId(idGeneratorService.genIdByRelateId(fluxOrder.getFluxOrderId(), IdType.FLUX_INSTRUCT_ID));
        fluxProcess.setTradeId(fundDetail.getTradeId());
        fluxProcess.setOrderId(fundDetail.getOrderId());
        fluxProcess.setStatus(FluxProcessStatus.INIT);
        fluxProcess.setType(FluxProcessType.NORMAL);
        fluxProcess.setDirection(FluxProcessDirection.APPLY);
        fluxProcess.setFluxOrderId(fluxOrder.getFluxOrderId());
        fluxProcess.setAmount(fundDetail.getAmount());
        fluxProcess.setFundDetailId(fundDetail.getDetailId());

        fluxProcess.setAssetInfo(fundDetail.getAssetInfo());
        fluxProcess.setFundAction(fundDetail.getFundAction());

        if (StrUtil.isNotBlank(fundDetail.getRelationId())) {
            FluxProcess relationFluxProcess = fluxProcessRepository.loadByPayFundDetailId(fundDetail.getRelationId());
            fluxProcess.setRelationId(relationFluxProcess.getFluxProcessId());
        }

        fluxOrder.getFluxChain().append(fluxProcess);
    }
}