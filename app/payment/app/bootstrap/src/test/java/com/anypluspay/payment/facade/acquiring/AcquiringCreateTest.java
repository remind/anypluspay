package com.anypluspay.payment.facade.acquiring;

import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.types.biz.TradeType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wxj
 * 2025/5/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AcquiringCreateTest extends AcquiringBaseTest {

    @Test
    public void testSuccess() {
        create();
    }

    @Test
    public void testExists() {
        AcquiringOrder acquiringOrder = create();
        AcquiringCreateRequest request = new AcquiringCreateRequest();
        request.setTradeType(TradeType.INSTANT_ACQUIRING.getCode());
        request.setOutTradeNo(acquiringOrder.getOutTradeNo());
        request.setPartnerId(MERCHANT_ID);
        request.setPayeeId(PAYEE_MEMBER_ID);
        request.setPayeeAccountNo(PAYEE_ACCOUNT_NO);
        request.setPayerId(PAYER_MEMBER_ID);
        request.setSubject("测试商品");
        request.setAmount(new Money(10));
        Map<String, String> extension = Map.of("key1", "value1", "key2", "value2");
        request.setExtension(JSONUtil.toJsonStr(extension));

        AcquiringCreateResponse response1 = tradeFacade.create(request);
        Assert.assertFalse(response1.isSuccess());
    }
}
