package com.anypluspay.channel.application.route.scorer.api;

import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.stereotype.Component;

/**
 * 支持的API判断
 *
 * @author wxj
 * 2024/8/24
 */
@Component
public class SupportApiScorer implements ChannelApiScorer {
    @Override
    public int score(ChannelApi channelApi, ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        return channelApi.getPayMethods().contains(routeParam.getPayMethod())
                && ChannelApiType.isFirstCommand(channelApi.getType()) ? STANDARD_SCORE : ZERO_SCORE;
    }
}
