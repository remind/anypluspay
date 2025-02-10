package com.anypluspay.payment.facade.instant;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.TradeInfo;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
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
 * 2024/1/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChannelPayTest extends InstPaymentBaseTest {
    @Autowired
    private InstantPaymentFacade instantPaymentFacade;

    @Test
    public void testPaySuccess() {
        mockFundInSuccess();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        request.setTradeInfos(List.of(buildTradeInfos(amount, List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)))));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayOrderStatus.SUCCESS, response.getOrderStatus());
        assetPayment(request, response);
    }

    @Test
    public void testPayProcessing() {
        mockFundInProcessing();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        request.setTradeInfos(List.of(buildTradeInfos(amount, List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)))));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        assetPayment(request, response);
        Assert.assertEquals(PayOrderStatus.PAYING, response.getOrderStatus());
        PayResult payResult = response.getResult();
        Assert.assertEquals(PayStatus.PROCESS, payResult.getPayStatus());
        Assert.assertNotNull(payResult.getPayParam());
    }


    @Test
    public void testPay() {
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(2000, Currency.getInstance("CNY")));
        request.setPayerId("payer123");

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
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        assetPayment(request, response);
    }


}
