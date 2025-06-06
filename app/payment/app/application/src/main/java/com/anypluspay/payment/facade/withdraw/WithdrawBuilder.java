package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.trade.withdraw.WithdrawOrder;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.biz.WithdrawOrderStatus;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/5/15
 */
@Component
public class WithdrawBuilder extends PaymentBuilder {

    public WithdrawOrder buildWithdrawOrder(WithdrawRequest request) {
        WithdrawOrder withdrawOrder = new WithdrawOrder();
        withdrawOrder.setTradeId(idGeneratorService.genPaymentId(request.getMemberId(), IdType.WITHDRAW_ORDER_ID));
        withdrawOrder.setOrderId(idGeneratorService.genIdByRelateId(withdrawOrder.getTradeId(), PayOrderType.PAY.getIdType()));
        withdrawOrder.setAmount(request.getAmount());
        withdrawOrder.setMemberId(request.getMemberId());
        withdrawOrder.setAccountNo(request.getAccountNo());
        withdrawOrder.setBankCode(request.getBankCode());
        withdrawOrder.setCardName(request.getCardName());
        withdrawOrder.setCardNo(request.getCardNo());
        withdrawOrder.setCardIdNo(request.getCardIdNo());
        withdrawOrder.setStatus(WithdrawOrderStatus.PAYING);
        withdrawOrder.setMemo(request.getMemo());
        return withdrawOrder;
    }

    public PayOrder buildPayProcess(WithdrawOrder withdrawOrder) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTradeId(withdrawOrder.getTradeId());
        payOrder.setOrderId(withdrawOrder.getOrderId());
        payOrder.setAmount(withdrawOrder.getAmount());
        payOrder.setMemberId(withdrawOrder.getMemberId());
        payOrder.setStatus(PayProcessStatus.INIT);
        payOrder.addPayerFundDetail(buildPayerFundDetail(payOrder.getTradeId(), payOrder.getOrderId(), withdrawOrder));
        payOrder.addPayeeFundDetail(buildPayeeFundDetail(payOrder.getTradeId(), payOrder.getOrderId(), withdrawOrder));
        return payOrder;
    }

    private FundDetail buildPayeeFundDetail(String paymentId, String orderId, WithdrawOrder withdrawOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setTradeId(paymentId);
        fundDetail.setOrderId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(paymentId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(withdrawOrder.getAmount());
        fundDetail.setMemberId(withdrawOrder.getMemberId());
        BankCardAsset bankCardAsset = new BankCardAsset(withdrawOrder.getAccountNo());
        fundDetail.setAssetInfo(bankCardAsset);
        fundDetail.setBelongTo(BelongTo.PAYEE);
        fundDetail.setFundAction(FundAction.INCREASE);
        return fundDetail;
    }

    private FundDetail buildPayerFundDetail(String paymentId, String orderId, WithdrawOrder withdrawOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setTradeId(paymentId);
        fundDetail.setOrderId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(paymentId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(withdrawOrder.getAmount());
        fundDetail.setMemberId(withdrawOrder.getMemberId());
        fundDetail.setAssetInfo(new BalanceAsset(withdrawOrder.getMemberId(), withdrawOrder.getAccountNo()));
        fundDetail.setBelongTo(BelongTo.PAYER);
        fundDetail.setFundAction(FundAction.DECREASE);
        return fundDetail;
    }
}
