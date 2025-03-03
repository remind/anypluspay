package com.anypluspay.payment.facade.instant;

import com.anypluspay.payment.application.instant.InstantPaymentService;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrderStatus;
import com.anypluspay.payment.application.instant.request.InstantPaymentRequest;
import com.anypluspay.payment.application.instant.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wxj
 * 2024/1/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChannelPayTest extends InstPaymentBaseTest {
    @Autowired
    private InstantPaymentService instantPaymentService;

    @Test
    public void testPaySuccess() {
        mockFundInSuccess();
        mockAccountingSuccess();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        request.setTradeInfos(List.of(buildTradeInfos(amount, List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)))));
        InstantPaymentResponse response = instantPaymentService.pay(request);
        Assert.assertEquals(GeneralPayOrderStatus.SUCCESS, response.getOrderStatus());
        assetPayOrder(request, response);
    }

    @Test
    public void testPayProcessing() {
        mockFundInProcessing();
        mockAccountingSuccess();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        request.setTradeInfos(List.of(buildTradeInfos(amount, List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)))));
        InstantPaymentResponse response = instantPaymentService.pay(request);
        assetPayOrder(request, response);
        Assert.assertEquals(GeneralPayOrderStatus.PAYING, response.getOrderStatus());
        PayResult payResult = response.getResult();
        Assert.assertEquals(PayStatus.PROCESS, payResult.getPayStatus());
        Assert.assertNotNull(payResult.getPayParam());
    }

}
