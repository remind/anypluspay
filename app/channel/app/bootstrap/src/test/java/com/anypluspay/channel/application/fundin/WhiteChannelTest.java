package com.anypluspay.channel.application.fundin;

import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.commons.lang.utils.ExtUtil;
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
        addRouteExtra(request, ExtKey.WHITE_CHANNELS, TEST_CHANNEL_CODE);
        FundResult fundResult = fundInFacade.apply(request);
        Assert.assertEquals(BizOrderStatus.PROCESSING, fundResult.getStatus());
        Assert.assertNotNull(fundResult.getResponseExtra());
        Assert.assertNotNull(ExtUtil.getStringValue(ExtKey.INST_URL, fundResult.getResponseExtra()));
    }
}
