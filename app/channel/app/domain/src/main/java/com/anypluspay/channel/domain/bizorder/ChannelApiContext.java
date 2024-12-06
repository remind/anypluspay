package com.anypluspay.channel.domain.bizorder;

import com.anypluspay.channel.domain.channel.BaseChannel;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.types.channel.ChannelApiType;
import lombok.Data;

/**
 *
 * 渠道上下文，在订单流程和调用渠道API时使用
 * @author wxj
 * 2024/8/23
 */
@Data
public class ChannelApiContext {

    /**
     * 渠道
     */
    private BaseChannel channel;

    /**
     * 渠道API
     */
    private ChannelApi channelApi;

    /**
     * 是否维护中
     */
    private boolean isMaintain;

    /**
     * 获取渠道编码
     * @return
     */
    public String getChannelCode() {
        return channel.getCode();
    }

    /**
     * 获取渠道API类型
     * @return
     */
    public ChannelApiType getChannelApiType() {
        return channelApi.getType();
    }
}
