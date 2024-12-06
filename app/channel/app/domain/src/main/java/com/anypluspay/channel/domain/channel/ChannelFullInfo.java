package com.anypluspay.channel.domain.channel;

import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.channel.fund.FundChannel;
import lombok.Data;

import java.util.List;

/**
 * 渠道完整信息
 *
 * @author wxj
 * 2024/8/23
 */
@Data
public class ChannelFullInfo {

    /**
     * 渠道信息
     */
    private FundChannel fundChannel;

    /**
     * 渠道API信息
     */
    private List<ChannelApi> channelApis;

    /**
     * 维护信息
     */
    private List<ChannelMaintain> channelMaintains;

    /**
     * 支持的机构，如果没有就不支持任何机构
     */
    private List<ChannelSupportInst> channelSupportInst;

    /**
     * 参数值
     */
    private String paramValue;

    public String getChannelCode() {
        return fundChannel.getCode();
    }
}
