package com.anypluspay.anypay.domain.channel.spi;

import com.anypluspay.anypay.domain.channel.spi.response.ChannelNotifyResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 渠道回调处理
 * @author wxj
 * 2026/1/30
 */
public interface ChannelCallbackService {

    ChannelNotifyResponse notify(String channelCode, HttpServletRequest request);
}
