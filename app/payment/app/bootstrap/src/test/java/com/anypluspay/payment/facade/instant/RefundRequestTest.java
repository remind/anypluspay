package com.anypluspay.payment.facade.instant;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.application.instant.InstantPaymentService;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrderStatus;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrderStatus;
import com.anypluspay.payment.application.instant.request.FundDetailInfo;
import com.anypluspay.payment.application.instant.request.InstantPaymentRequest;
import com.anypluspay.payment.application.instant.request.RefundRequest;
import com.anypluspay.payment.application.instant.request.TradeInfo;
import com.anypluspay.payment.application.instant.response.InstantPaymentResponse;
import com.anypluspay.payment.application.instant.response.RefundResponse;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;

/**
 * @author wxj
 * 2025/2/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RefundRequestTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentService instantPaymentService;

    @Test
    public void testSuccess() {
        mockRefundSuccess();
        InstantPaymentResponse response = buildInstantPayment();
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setRequestId(randomId());
        refundRequest.setAmount(new Money(2000));
        refundRequest.setOrigOrderId(response.getPayOrderId());
        RefundResponse refundResponse = instantPaymentService.refund(refundRequest);
        Assert.assertNotNull(refundResponse);
        modelIntegrityCheck.checkInstantPayment(response.getPaymentId());
        RefundOrder refundOrder = refundOrderRepository.load(refundResponse.getRefundOrderId());
        refundOrder.getPayeeDetails().forEach(fundDetail -> {
            Assert.assertEquals(RefundOrderStatus.SUCCESS, refundOrder.getOrderStatus());
        });
    }



    private InstantPaymentResponse buildInstantPayment() {
        mockFundInSuccess();

        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(2000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 1000)));

        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeAmount(new Money(2000, Currency.getInstance("CNY")));
        tradeInfo.setTradeId("tradeId123");
        tradeInfo.setPayeeId("payee123");

        FundDetailInfo payeeFundDetail = new FundDetailInfo();
        payeeFundDetail.setMemberId("payee123");
        payeeFundDetail.setAmount(new Money(2000, Currency.getInstance("CNY")));
        payeeFundDetail.setAssetInfo(new BalanceAsset("payee123", "payee-account"));
        tradeInfo.setPayeeFundDetail(List.of(payeeFundDetail));
        request.setTradeInfos(List.of(tradeInfo));
        InstantPaymentResponse response = instantPaymentService.pay(request);
        Assert.assertEquals(GeneralPayOrderStatus.SUCCESS, response.getOrderStatus());
        assetPayOrder(request, response);
        return response;
    }
}
