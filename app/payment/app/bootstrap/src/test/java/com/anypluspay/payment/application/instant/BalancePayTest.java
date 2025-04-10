package com.anypluspay.payment.application.instant;

import cn.hutool.json.JSONUtil;
import com.anypluspay.payment.facade.instant.InstantPaymentFacadeImpl;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
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
public class BalancePayTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacadeImpl instantPaymentFacade;

    @Test
    public void testPaySuccess() {
        mockAccountingSuccess();
        double amount = 10;
        InstantPaymentRequest request = buildInstantPaymentRequest(amount);
        request.setPayerFundDetail(List.of(buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, amount)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, amount)));
        System.out.println(JSONUtil.toJsonStr(request));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        assetPayOrder(request, response);
    }
}
