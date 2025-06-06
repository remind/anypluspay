package com.anypluspay.channel.application.route.scorer;

import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.application.route.RouteScorer;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;

/**
 * 渠道打分器
 *
 * @author wxj
 * 2024/6/28
 */
public interface ChannelScorer extends RouteScorer {

    /**
     * 计算渠道分数
     *
     * @param channelFullInfo
     * @param routeParam
     * @return
     */
    int score(ChannelFullInfo channelFullInfo, RouteParam routeParam);

}
