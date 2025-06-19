package com.anypluspay.payment.facade.deposit;

import com.anypluspay.payment.application.TradeBuilder;
import com.anypluspay.payment.domain.trade.deposit.DepositOrder;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.types.trade.DepositOrderStatus;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 充值构造器
 * @author wxj
 * 2025/5/14
 */
@Component
public class DepositBuilder extends TradeBuilder {

    public DepositOrder buildDepositOrder(DepositRequest request) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setPartnerId(request.getPartnerId());
        depositOrder.setTradeId(idGeneratorService.genTradeId(request.getMemberId(), IdType.DEPOSIT_ORDER_ID));
        depositOrder.setOrderId(idGeneratorService.genIdByRelateId(depositOrder.getTradeId(), PayOrderType.PAY.getIdType()));
        depositOrder.setAmount(request.getAmount());
        depositOrder.setMemberId(request.getMemberId());
        depositOrder.setAccountNo(request.getAccountNo());
        depositOrder.setStatus(DepositOrderStatus.PAYING);
        depositOrder.setMemo(request.getMemo());
        return depositOrder;
    }

    public PayOrder buildPayProcess(DepositOrder depositOrder, List<FundDetailInfo> payerFundDetails) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(depositOrder.getTradeId());
        payOrder.setOrderId(depositOrder.getOrderId());
        payOrder.setAmount(depositOrder.getAmount());
        payOrder.setMemberId(depositOrder.getMemberId());
        payOrder.setStatus(PayProcessStatus.INIT);
        payerFundDetails.forEach(fundDetailInfo -> payOrder.addPayerFundDetail(buildFundDetail(payOrder.getTradeId(), payOrder.getOrderId(), fundDetailInfo, BelongTo.PAYER)));
        payOrder.addPayeeFundDetail(buildPayeeFundDetail(payOrder.getTradeId(), payOrder.getOrderId(), depositOrder));
        return payOrder;
    }

    protected FundDetail buildPayeeFundDetail(String tradeId, String orderId, DepositOrder depositOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setTradeId(tradeId);
        fundDetail.setOrderId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(tradeId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(depositOrder.getAmount());
        fundDetail.setMemberId(depositOrder.getMemberId());
        fundDetail.setAssetInfo(new BalanceAsset(depositOrder.getMemberId(), depositOrder.getAccountNo()));
        fundDetail.setBelongTo(BelongTo.PAYEE);
        fundDetail.setFundAction(FundAction.INCREASE);
        return fundDetail;
    }
}
