package com.anypluspay.channel.application.notify;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.facade.request.NotifyRequest;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.test.request.LocalBankNotifyResult;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channelgateway.types.CallbackType;
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
public class NotifyServiceTest extends FundInBaseTest {

    @Autowired
    private NotifyFacade notifyFacade;

    @Test
    public void testNotifySuccess() {
        FundResult fundResult = createProcessFundOrder();

        LocalBankNotifyResult localBankNotifyResult = new LocalBankNotifyResult();
        localBankNotifyResult.setInstRequestNo(fundResult.getInstRequestNo());
        localBankNotifyResult.setResultCode("SUCCESS");
        FundResult notifyResult = notifyFacade.notify(buildNotifyRequest(localBankNotifyResult));
        Assert.assertEquals(BizOrderStatus.SUCCESS, notifyResult.getStatus());
        Assert.assertEquals("SUCCESS", notifyResult.getResponseExt().get(ChannelExtKey.NOTIFY_RESPONSE_DATA.getCode()));
    }

    @Test
    public void testNotifyProcessing() {
        FundResult fundResult = createProcessFundOrder();

        LocalBankNotifyResult localBankNotifyResult = new LocalBankNotifyResult();
        localBankNotifyResult.setInstRequestNo(fundResult.getInstRequestNo());
        localBankNotifyResult.setResultCode("processing");
        FundResult notifyResult = notifyFacade.notify(buildNotifyRequest(localBankNotifyResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, notifyResult.getStatus());
        Assert.assertEquals("SUCCESS", notifyResult.getResponseExt().get(ChannelExtKey.NOTIFY_RESPONSE_DATA.getCode()));
    }

    private NotifyRequest buildNotifyRequest(LocalBankNotifyResult localBankNotifyResult) {
        NotifyRequest request = new NotifyRequest();
        request.setChannelCode(TEST_CHANNEL_CODE);
        request.setChannelApiType(ChannelApiType.VERIFY_SIGN.getCode());
        request.setCallbackType(CallbackType.SERVER.getCode());
        request.setRequestBody(JSONUtil.toJsonStr(localBankNotifyResult));
        return request;
    }
}
