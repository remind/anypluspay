package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wxj
 * 2025/5/20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AcquiringPayTest extends AcquiringBaseTest {

    @Test
    public void testBalancePaySuccess() {
        AcquiringOrder acquiringOrder = create();
        AcquiringPayRequest request = new AcquiringPayRequest();
        request.setPaymentId(acquiringOrder.getPaymentId());
        request.setPayerFundDetail(List.of(buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, acquiringOrder.getAmount().getAmount().doubleValue())));
        AcquiringPayResponse response = tradeFacade.pay(request);
        System.out.println(ToStringBuilder.reflectionToString(response));
        acquiringIntegrityCheck.checkAcquiringOrder(acquiringOrder.getPaymentId());
    }
}
