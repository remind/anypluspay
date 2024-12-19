package com.anypluspay.channel.application.fundin.onlinebank;

import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author wxj
 * 2024/12/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SignTest extends FundInBaseTest {

    @Test
    public void applySignSuccess() {
        FundInRequest request = buildRequest(TestConstants.S);
        FundResult fundResult = fundInFacade.apply(request);
        verify(apiResultRepository, never()).store(any());
        validateBySignSuccess(fundResult);
    }

    @Test
    public void applySignFail() {
        FundInRequest request = buildRequest(TestConstants.F);
        FundResult fundResult = fundInFacade.apply(request);
        verify(apiResultRepository, never()).store(any());
        Assert.assertEquals(BizOrderStatus.FAILED, fundResult.getStatus());
    }

    @Test
    public void testQuerySuccess() {
        FundInRequest request = buildRequest(TestConstants.S, TestConstants.S);
        FundResult fundResult = fundInFacade.apply(request);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        verify(apiResultRepository, never()).store(any());
        validateByQuerySuccess(queryResult);
    }

    @Test
    public void testQueryFail() {
        FundInRequest request = buildRequest(TestConstants.S, TestConstants.F);
        FundResult fundResult = fundInFacade.apply(request);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        verify(apiResultRepository, never()).store(any());
        Assert.assertEquals(BizOrderStatus.FAILED, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }

    @Test
    public void testQueryAmountNotEqual() {
        FundInRequest request = buildRequest(TestConstants.S, TestConstants.QUERY_MONEY_NOT_EQUAL);
        FundResult fundResult = fundInFacade.apply(request);
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        verify(apiResultRepository, never()).store(any());
        Assert.assertEquals(BizOrderStatus.PROCESSING, queryResult.getStatus());
        Assert.assertNotNull(queryResult.getUnityCode());
        Assert.assertNotNull(queryResult.getUnityMessage());
    }
}
