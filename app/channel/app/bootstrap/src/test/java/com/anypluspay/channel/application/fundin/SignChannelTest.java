package com.anypluspay.channel.application.fundin;

import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/7/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SignChannelTest extends FundServiceBaseTest {

    @Test
    public void applySignSuccess() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.S);
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        log.info(ToStringBuilder.reflectionToString(fundResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, fundResult.getStatus());
        Assert.assertNotNull(fundResult.getResponseExtra());
        Assert.assertNotNull(fundResult.getInstResponseNo());
    }

    @Test
    public void applySignFail() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.F);
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        log.info(ToStringBuilder.reflectionToString(fundResult));
        Assert.assertEquals(BizOrderStatus.FAILED, fundResult.getStatus());
    }

    @Test
    public void testQuerySuccess() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.S, TestConstants.S);
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        log.info(ToStringBuilder.reflectionToString(queryResult));
        Assert.assertEquals(BizOrderStatus.SUCCESS, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }

    @Test
    public void testQueryFail() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.S, TestConstants.F);
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        log.info(ToStringBuilder.reflectionToString(queryResult));
        Assert.assertEquals(BizOrderStatus.FAILED, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }

    @Test
    public void testQueryAmountNotEqual() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.S, TestConstants.QUERY_MONEY_NOT_EQUAL);
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        log.info(ToStringBuilder.reflectionToString(queryResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }

}
