package com.anypluspay.payment.facade.withdraw;

import cn.hutool.core.lang.UUID;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.biz.withdraw.WithdrawOrder;
import com.anypluspay.payment.types.biz.WithdrawOrderStatus;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/5/15
 */
@Component
public class WithdrawBuilder extends PaymentBuilder {

    public WithdrawOrder buildWithdrawOrder(WithdrawRequest request) {
        WithdrawOrder withdrawOrder = new WithdrawOrder();
        withdrawOrder.setPaymentId(idGeneratorService.genPaymentId(request.getMemberId(), IdType.WITHDRAW_ORDER_ID));
        withdrawOrder.setPayOrderId(idGeneratorService.genIdByRelateId(withdrawOrder.getPaymentId(), PayOrderType.PAY.getIdType()));
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

    public GeneralPayOrder buildPayOrder(WithdrawOrder withdrawOrder) {
        GeneralPayOrder generalPayOrder = new GeneralPayOrder();
        generalPayOrder.setPaymentId(withdrawOrder.getPaymentId());
        generalPayOrder.setOrderId(withdrawOrder.getPayOrderId());
        generalPayOrder.setRequestId(UUID.randomUUID().toString(true));
        generalPayOrder.setAmount(withdrawOrder.getAmount());
        generalPayOrder.setMemberId(withdrawOrder.getMemberId());
        generalPayOrder.setOrderStatus(GeneralPayOrderStatus.INIT);
        generalPayOrder.addPayerFundDetail(buildPayerFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getOrderId(), withdrawOrder));
        generalPayOrder.addPayeeFundDetail(buildPayeeFundDetail(generalPayOrder.getPaymentId(), generalPayOrder.getOrderId(), withdrawOrder));
        return generalPayOrder;
    }

    private FundDetail buildPayeeFundDetail(String paymentId, String orderId, WithdrawOrder withdrawOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setPaymentId(paymentId);
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
        fundDetail.setPaymentId(paymentId);
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
