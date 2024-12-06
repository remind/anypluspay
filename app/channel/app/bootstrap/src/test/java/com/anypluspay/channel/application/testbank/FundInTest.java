package com.anypluspay.channel.application.testbank;

import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channelgateway.ChannelGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/11/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FundInTest extends FundServiceBaseTest {

    @DubboReference(group = "test-bank-sg")
    private ChannelGateway annoChannelGateway;

    @Test
    public void applySignSuccess() {
        FundInRequest fundInDTO = buildFundOrder(TestConstants.S);
        fundInDTO.setPayInst("TB1");
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        log.info(ToStringBuilder.reflectionToString(fundResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, fundResult.getStatus());
        Assert.assertNotNull(fundResult.getInstResponseNo());
    }

}
