package com.anypluspay.channel.application.route.scorer.api;

import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.application.route.RouteScorer;

/**
 * @author wxj
 * 2024/8/24
 */
public interface ChannelApiScorer extends RouteScorer {

    /**
     * 计算渠道API分数
     *
     * @param channelApi
     * @param channelFullInfo
     * @param routeParam
     * @return
     */
    int score(ChannelApi channelApi, ChannelFullInfo channelFullInfo, RouteParam routeParam);
}
