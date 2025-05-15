package com.anypluspay.payment.facade.deposit;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.application.instant.InstPaymentBaseTest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;

/**
 * @author wxj
 * 2025/5/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DepositFacadeTest extends InstPaymentBaseTest {

    @Autowired
    private DepositFacade depositFacade;

    @Test
    public void testSuccess() {
        mockFundInSuccess();
        mockAccountingSuccess();
        double amount = 10;
        DepositRequest request = new DepositRequest();
        request.setMemberId(PAYER_MEMBER_ID);
        request.setAccountNo(PAYER_ACCOUNT_NO);
        request.setAmount(new Money(amount, Currency.getInstance("CNY")));
        request.setMemo("测试充值");
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, amount)));
        DepositResponse response = depositFacade.apply(request);
        System.out.println(ToStringBuilder.reflectionToString(response));
    }

}
