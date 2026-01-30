package com.anypluspay.anypay.infra.channel.test;

import com.anypluspay.anypay.domain.channel.spi.ChannelCallbackService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelNotifyResponse;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelOrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2026/1/30
 */
@Service("testChannelCallbackService")
public class TestChannelCallbackService implements ChannelCallbackService {
    @Override
    public ChannelNotifyResponse notify(String channelCode, HttpServletRequest request) {
        ChannelNotifyResponse channelResponse = new ChannelNotifyResponse();
        channelResponse.setStatus(ChannelOrderStatus.SUCCESS);
        channelResponse.setResultCode("SUCCESS");
        channelResponse.setResultMsg("SUCCESS");
        channelResponse.setChannelRequestNo("channelRequestNo");
        channelResponse.setChannelResponseNo("channelResponseNo");
        channelResponse.setBody("SUCCESS");
        return channelResponse;
    }
}
