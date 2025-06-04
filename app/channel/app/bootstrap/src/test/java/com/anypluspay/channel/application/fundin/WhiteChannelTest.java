package com.anypluspay.channel.application.fundin;

import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 白名单渠道测试
 *
 * @author wxj
 * 2024/12/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class WhiteChannelTest extends FundInBaseTest {

    @Test
    public void applySignSuccess() {
        FundInRequest request = buildRequest(TestConstants.S);
        request.getExtension().add(ChannelExtKey.WHITE_CHANNELS.getCode(), TEST_CHANNEL_CODE);
        FundResult fundResult = fundInFacade.apply(request);
        Assert.assertEquals(BizOrderStatus.PROCESSING, fundResult.getStatus());
        Assert.assertNotNull(fundResult.getResponseExt());
        Assert.assertNotNull(fundResult.getResponseExt().get(ChannelExtKey.INST_REDIRECTION_DATA.getCode()));
    }
}
