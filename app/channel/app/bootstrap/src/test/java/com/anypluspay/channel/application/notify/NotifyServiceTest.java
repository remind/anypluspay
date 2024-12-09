package com.anypluspay.channel.application.notify;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.test.request.LocalBankNotifyResult;
import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.utils.ExtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/8/20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class NotifyServiceTest extends FundServiceBaseTest {

    @Autowired
    private NotifyFacade notifyFacade;

    @Test
    public void testNotifySuccess() {
        FundResult fundResult = buildNormalFundOrder();

        LocalBankNotifyResult localBankNotifyResult = new LocalBankNotifyResult();
        localBankNotifyResult.setInstRequestNo(fundResult.getInstRequestNo());
        localBankNotifyResult.setResultCode("SUCCESS");
        FundResult notifyResult = notifyFacade.notify(channelFullInfo.getFundChannel().getCode(), ChannelApiType.VERIFY_SIGN,JSONUtil.toJsonStr(localBankNotifyResult));
        Assert.assertEquals(BizOrderStatus.SUCCESS, notifyResult.getStatus());
        Assert.assertEquals("SUCCESS", ExtUtil.getStringValue(ExtKey.NOTIFY_RESPONSE_DATA, notifyResult.getResponseExtra()));
    }

    @Test
    public void testNotifyProcessing() {
        FundResult fundResult = buildNormalFundOrder();

        LocalBankNotifyResult localBankNotifyResult = new LocalBankNotifyResult();
        localBankNotifyResult.setInstRequestNo(fundResult.getInstRequestNo());
        localBankNotifyResult.setResultCode("processing");
        FundResult notifyResult = notifyFacade.notify(channelFullInfo.getFundChannel().getCode(), ChannelApiType.VERIFY_SIGN, JSONUtil.toJsonStr(localBankNotifyResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, notifyResult.getStatus());
        Assert.assertEquals("SUCCESS", ExtUtil.getStringValue(ExtKey.NOTIFY_RESPONSE_DATA, notifyResult.getResponseExtra()));
    }
}
