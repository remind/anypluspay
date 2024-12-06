package com.anypluspay.channel.application.query;

import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/8/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderQueryFacadeTest extends FundServiceBaseTest {

    @Test
    public void testQueryByOrderId() {
        FundResult fundResult = buildNormalFundOrder();
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        Assert.assertEquals(BizOrderStatus.SUCCESS, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }
}
