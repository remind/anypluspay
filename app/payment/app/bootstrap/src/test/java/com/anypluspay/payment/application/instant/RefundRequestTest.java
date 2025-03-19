package com.anypluspay.payment.application.instant;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.domain.payorder.refund.RefundOrderStatus;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.RefundRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.facade.response.RefundResponse;
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
    private InstantPaymentFacadeImpl instantPaymentFacade;

    @Test
    public void testSuccess() {
        mockRefundSuccess();
        InstantPaymentResponse response = buildInstantPayment();
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setRequestId(randomId());
        refundRequest.setAmount(new Money(2000));
        refundRequest.setOrigOrderId(response.getPayOrderId());
        RefundResponse refundResponse = instantPaymentFacade.refund(refundRequest);
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
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, 2000)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(GeneralPayOrderStatus.SUCCESS, response.getOrderStatus());
        assetPayOrder(request, response);
        return response;
    }
}
