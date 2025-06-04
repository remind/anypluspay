package com.anypluspay.payment.application.instant;

import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.payment.application.notify.ExternalFluxNotifyService;
import com.anypluspay.payment.facade.instant.InstantPaymentFacadeImpl;
import com.anypluspay.payment.types.status.PayProcessStatus;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * @author wxj
 * 2025/2/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExternalFluxNotifyServiceTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacadeImpl instantPaymentFacade;

    @Autowired
    private ExternalFluxNotifyService externalFluxNotifyService;

    private final AtomicReference<String> requestId = new AtomicReference<>();

    @Test
    public void notifySuccess() {
        InstantPaymentResponse response = buildProcessingOrder();
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setRequestId(requestId.get());
        externalFluxNotifyService.process(fundResult);
    }


    private void mockFundIn() {
        doAnswer(invocation -> {
            FundResult fundResult = new FundResult();
            fundResult.setStatus(BizOrderStatus.PROCESSING);
            fundResult.setUnityCode("P001");
            Extension responseExt = new Extension();
            responseExt.add(ChannelExtKey.INST_REDIRECTION_DATA.getCode(), "channel pay url");
            fundResult.setResponseExt(responseExt);
            FundInRequest request = invocation.getArgument(0);
            requestId.set(request.getRequestId());
            return fundResult;
        }).when(fundInFacade).apply(any());
    }


    public InstantPaymentResponse buildProcessingOrder() {
        mockFundIn();
        mockAccountingSuccess();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        assetPayOrder(request, response);
        Assert.assertEquals(PayProcessStatus.PAYING, response.getOrderStatus());
        PayResult payResult = response.getResult();
        Assert.assertEquals(PayStatus.PROCESS, payResult.getPayStatus());
        Assert.assertNotNull(payResult.getPayResponse());
        return response;
    }
}
