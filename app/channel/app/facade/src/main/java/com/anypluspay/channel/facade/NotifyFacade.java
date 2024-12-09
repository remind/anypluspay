package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * 通知
 * @author wxj
 * 2024/9/24
 */
public interface NotifyFacade {

    /**
     * 通知
     * @param fundChannelCode   渠道编码
     * @param channelApiType     通知类型
     * @param request           通知内容
     * @return
     */
    FundResult notify(String fundChannelCode, ChannelApiType channelApiType, String request);
}
