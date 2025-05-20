package com.anypluspay.payment.facade.deposit;

import cn.hutool.core.lang.UUID;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.biz.deposit.DepositOrder;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.types.biz.DepositOrderStatus;
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
public class DepositBuilder extends PaymentBuilder {

    public DepositOrder buildDepositOrder(DepositRequest request) {
        DepositOrder depositOrder = new DepositOrder();
        depositOrder.setPaymentId(idGeneratorService.genPaymentId(request.getMemberId(), IdType.DEPOSIT_ORDER_ID));
        depositOrder.setPayProcessId(idGeneratorService.genIdByRelateId(depositOrder.getPaymentId(), PayOrderType.PAY.getIdType()));
        depositOrder.setAmount(request.getAmount());
        depositOrder.setMemberId(request.getMemberId());
        depositOrder.setAccountNo(request.getAccountNo());
        depositOrder.setStatus(DepositOrderStatus.PAYING);
        depositOrder.setMemo(request.getMemo());
        return depositOrder;
    }

    public PayProcess buildPayProcess(DepositOrder depositOrder, List<FundDetailInfo> payerFundDetails) {
        PayProcess payProcess = new PayProcess();
        payProcess.setPaymentId(depositOrder.getPaymentId());
        payProcess.setProcessId(depositOrder.getPayProcessId());
        payProcess.setRequestId(UUID.randomUUID().toString(true));
        payProcess.setAmount(depositOrder.getAmount());
        payProcess.setMemberId(depositOrder.getMemberId());
        payProcess.setStatus(PayProcessStatus.INIT);
        payerFundDetails.forEach(fundDetailInfo -> payProcess.addPayerFundDetail(buildFundDetail(payProcess.getPaymentId(), payProcess.getProcessId(), fundDetailInfo, BelongTo.PAYER)));
        payProcess.addPayeeFundDetail(buildPayeeFundDetail(payProcess.getPaymentId(), payProcess.getProcessId(), depositOrder));
        return payProcess;
    }

    protected FundDetail buildPayeeFundDetail(String paymentId, String orderId, DepositOrder depositOrder) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setPaymentId(paymentId);
        fundDetail.setPayProcessId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(paymentId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(depositOrder.getAmount());
        fundDetail.setMemberId(depositOrder.getMemberId());
        fundDetail.setAssetInfo(new BalanceAsset(depositOrder.getMemberId(), depositOrder.getAccountNo()));
        fundDetail.setBelongTo(BelongTo.PAYEE);
        fundDetail.setFundAction(FundAction.INCREASE);
        return fundDetail;
    }
}
