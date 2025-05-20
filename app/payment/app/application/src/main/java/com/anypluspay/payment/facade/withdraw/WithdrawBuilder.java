package com.anypluspay.payment.facade.withdraw;

import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.biz.withdraw.WithdrawOrder;
import com.anypluspay.payment.domain.process.PayProcess;
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
        withdrawOrder.setPaymentId(idGeneratorService.genPaymentId(request.getMemberId(), IdType.WITHDRAW_ORDER_ID));
        withdrawOrder.setPayProcessId(idGeneratorService.genIdByRelateId(withdrawOrder.getPaymentId(), PayOrderType.PAY.getIdType()));
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

    public PayProcess buildPayProcess(WithdrawOrder withdrawOrder) {
        PayProcess payProcess = new PayProcess();
        payProcess.setPaymentId(withdrawOrder.getPaymentId());
        payProcess.setProcessId(withdrawOrder.getPayProcessId());
        payProcess.setAmount(withdrawOrder.getAmount());
        payProcess.setMemberId(withdrawOrder.getMemberId());
        payProcess.setStatus(PayProcessStatus.INIT);
        payProcess.addPayerFundDetail(buildPayerFundDetail(payProcess.getPaymentId(), payProcess.getProcessId(), withdrawOrder));
        payProcess.addPayeeFundDetail(buildPayeeFundDetail(payProcess.getPaymentId(), payProcess.getProcessId(), withdrawOrder));
        return payProcess;
    }

    private FundDetail buildPayeeFundDetail(String paymentId, String orderId, WithdrawOrder withdrawOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setPaymentId(paymentId);
        fundDetail.setPayProcessId(orderId);
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
        fundDetail.setPayProcessId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(paymentId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(withdrawOrder.getAmount());
        fundDetail.setMemberId(withdrawOrder.getMemberId());
        fundDetail.setAssetInfo(new BalanceAsset(withdrawOrder.getMemberId(), withdrawOrder.getAccountNo()));
        fundDetail.setBelongTo(BelongTo.PAYER);
        fundDetail.setFundAction(FundAction.DECREASE);
        return fundDetail;
    }
}
