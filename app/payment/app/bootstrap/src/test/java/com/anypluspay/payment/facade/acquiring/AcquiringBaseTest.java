package com.anypluspay.payment.facade.acquiring;

import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.trade.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.facade.BaseMockPaymentTest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.types.biz.TradeType;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author wxj
 * 2025/5/20
 */
public class AcquiringBaseTest extends BaseMockPaymentTest {

    @Autowired
    protected AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    protected AcquiringIntegrityCheck acquiringIntegrityCheck;

    @Autowired
    protected AcquiringFacadeImpl tradeFacade;

    protected AcquiringOrder create() {
        AcquiringCreateRequest request = new AcquiringCreateRequest();
        request.setTradeType(TradeType.INSTANT_ACQUIRING.getCode());
        request.setOutTradeNo(randomId());
        request.setPartnerId(MERCHANT_ID);
        request.setPayeeId(PAYEE_MEMBER_ID);
        request.setPayeeAccountNo(PAYEE_ACCOUNT_NO);
        request.setPayerId(PAYER_MEMBER_ID);
        request.setSubject("测试商品");
        request.setAmount(new Money(10));
        Map<String, String> extension = Map.of("key1", "value1", "key2", "value2");
        request.setExtension(JSONUtil.toJsonStr(extension));
        AcquiringCreateResponse response = tradeFacade.create(request);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPartnerId());
        Assert.assertEquals(request.getPartnerId(), response.getPartnerId());
        Assert.assertEquals(request.getOutTradeNo(), response.getOutTradeNo());
        return acquiringOrderRepository.load(response.getTradeId());
    }
}
