package com.anypluspay.payment.facade.instant;

import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.payment.application.notify.PayNotifyService;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrderStatus;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.InstantPaymentFacadeTest;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2025/2/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PayNotifyServiceTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacade instantPaymentFacade;

    @Autowired
    private PayNotifyService payNotifyService;

    private AtomicReference<String> requestId = new AtomicReference<>();

    @Test
    public void notifySuccess() {
        InstantPaymentResponse response = buildProcessingOrder();
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setRequestId(requestId.get());
        payNotifyService.process(fundResult);
    }


    private void mockFundIn() {
        doAnswer(invocation -> {
            FundResult fundResult = new FundResult();
            fundResult.setStatus(BizOrderStatus.PROCESSING);
            fundResult.setUnityCode("P001");
            Map<String, String> responseExtra = new HashMap<>();
            responseExtra.put("instUrl", "channel pay url");
            fundResult.setResponseExtra(responseExtra);
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
        request.setTradeInfos(List.of(buildTradeInfos(amount, List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)))));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        assetPayOrder(request, response);
        Assert.assertEquals(GeneralPayOrderStatus.PAYING, response.getOrderStatus());
        PayResult payResult = response.getResult();
        Assert.assertEquals(PayStatus.PROCESS, payResult.getPayStatus());
        Assert.assertNotNull(payResult.getPayParam());
        return response;
    }
}
